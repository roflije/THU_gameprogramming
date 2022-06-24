import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

class TD_World extends A_World {
	public static int[][] matrix = new int[25][21]; // 0-1 matrix for bfs
	public static ArrayList<TD_AlienAI> alienObjects = new ArrayList<TD_AlienAI>(); // stores monsters

	private double timeSinceLastShot = 0;

	private TD_CounterHealth counterH; // health
	private TD_CounterAlien counterA; // monsters
	private TD_HelpText helpText; // helptext
	private TD_CounterCredits counterC;

	private LinkedList<A_Square> initRoute;
	private double[] startPoint;
	private A_Square startSquare;
	private A_Square endSquare;
	private double lifeHelpText = 10.0;
	private A_Type chosenBuilding = A_Type.TURRET;
	int[][] startend = { { 0, 10 }, { 24, 10 } };

	protected void init() {
		// create square objects for map grid
		for (int x = 0, i = 0; x <= 960; x = x + 40, ++i) {
			for (int y = 0, j = 0; y <= 800; y = y + 40, ++j) {
				squareObjects[i][j] = new A_Square(x, y, x + 40, y + 40, i, j);
			}
		}
		// set starting square
		startSquare = squareObjects[0][10];
		startSquare.setStart();
		// set ending square
		endSquare = squareObjects[24][10];
		endSquare.setEnd();
		// add the Avatar
		avatar = new TD_Avatar(400, 500);
		gameObjects.add(avatar);

		// calculate initial route
		updateMatrix();
		initRoute = A_Square.getPathFromCellList(BFS.shortestPath(startend[0], startend[1]));
		// add one alien
		startPoint = startSquare.getMiddle();
		spawn(0);

		counterA = new TD_CounterAlien(20, 120);
		counterH = new TD_CounterHealth(20, 40);
		counterC = new TD_CounterCredits(20, 80);
		helpText = new TD_HelpText(100, 400);

		textObjects.add(counterA);
		textObjects.add(counterC);
		textObjects.add(counterH);
		textObjects.add(helpText);

	}

	private A_Square findSquareAtPos(int x, int y) {
		int i = 0;
		int j = 0;
		for (int ii = 0; ii < 25; ++ii) // && loop
			for (int jj = 0; jj < 21; ++jj) // && loop
				if (squareObjects[ii][jj].isWithin((double) x, (double) y)) {
					i = ii;
					j = jj;
				}
		if (i != 25 && j != 21)
			return squareObjects[i][j];
		return null;
	}

	protected void processUserInput(A_UserInput userInput, double diffSeconds) {
		int button = userInput.mouseButton;
		// mouse button pressed
		if (userInput.isMouseEvent) {
			if (button == 1 && !super.isBuilding) {
				TD_Shot shot = new TD_Shot(avatar.x, avatar.y, userInput.mouseMovedX, userInput.mouseMovedY);
				gameObjects.add(shot);
			}
			if (button == 1 && super.isBuilding) { // left click in building mode
				A_Square sqr = findSquareAtPos(userInput.mousePressedX, userInput.mousePressedY);
				if (sqr != null && !sqr.getTaken()) { // if square is not null and square is not taken, prepare to build
					sqr.take(); // mark square as taken
					updateMatrix(); // update matrix with newly marked square
					LinkedList<Cell> cells = BFS.shortestPath(startend[0], startend[1]); // calculate if new path possible
					if (cells == null) { // if no path (cannot build), free the square
						sqr.notTake();
					} else if (this.chosenBuilding == A_Type.TURRET ? this.counterC.get() - A_Const.TURRET_COST < 0 : this.counterC.get() - A_Const.SLOWER_COST < 0) {
						sqr.notTake();
					} else {
						this.initRoute = A_Square.getPathFromCellList(cells); // update initial route for monsters with new route
						boolean cannotCreate = false;
						for (TD_AlienAI a : alienObjects) { // check if can create path for every alien that is on the map already
							if (!a.updatePath()) // sqr.isWithin(a.x, a.y)
								cannotCreate = true; // if not, set flag
						}
						if (cannotCreate) { // if some alien cannot reach destination, free the square
							sqr.notTake();
						} else { // else spawn tower
							double[] middle = sqr.getMiddle();
							switch (this.chosenBuilding) {
							case TURRET:
								if (this.counterC.get() - A_Const.TURRET_COST >= 0) {
									gameObjects.add(new TD_Turret(middle[0], middle[1], 20));
									this.counterC.subtract(A_Const.TURRET_COST);
								} else {
									sqr.notTake();
								}
								break;
							case SLOWER:
								break;
							default:
								break;
							}
						}

					}
				}
			}
		}

		//
		// Mouse still pressed?
		//
		if (userInput.isMousePressed && button == 1 && !super.isBuilding) {
			timeSinceLastShot += diffSeconds;
			if (timeSinceLastShot > 0.2) {
				timeSinceLastShot = 0;
				TD_Shot shot = new TD_Shot(avatar.x, avatar.y, userInput.mouseMovedX, userInput.mouseMovedY);
				gameObjects.add(shot);
			}
		}

		//
		// Keyboard events
		//
		if (userInput.isKeyPressEvent || userInput.isKeyReleaseEvent) {
			if (userInput.keys.contains(KeyEvent.VK_W) && userInput.keys.size() == 1) {
				userInput.keys.toString();
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = Math.PI * -0.5;
			}
			if (userInput.keys.contains(KeyEvent.VK_S) && userInput.keys.size() == 1) {
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = -Math.PI * 1.5;
			}
			if (userInput.keys.contains(KeyEvent.VK_A) && userInput.keys.size() == 1) {
				if (avatar.alfa == Math.PI * 2)
					avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = -Math.PI;
			}

			if (userInput.keys.contains(KeyEvent.VK_D) && userInput.keys.size() == 1) {
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = Math.PI * 2;
			}
		}

		if (userInput.isKeyPressEvent) {
			if (userInput.keyPressed == KeyEvent.VK_TAB) {
				toggleBuilding();
			}
			if (userInput.keys.contains(KeyEvent.VK_W) && userInput.keys.contains(KeyEvent.VK_A)) {
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = Math.PI * -0.75;
			}
			if (userInput.keys.contains(KeyEvent.VK_W) && userInput.keys.contains(KeyEvent.VK_D)) {
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = Math.PI * -0.25;
			}
			if (userInput.keys.contains(KeyEvent.VK_A) && userInput.keys.contains(KeyEvent.VK_S)) {
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = Math.PI * -1.25;
			}
			if (userInput.keys.contains(KeyEvent.VK_S) && userInput.keys.contains(KeyEvent.VK_D)) {
				avatar.speed = A_Const.AVATAR_SPEED;
				avatar.alfa = Math.PI * 0.25;
			}
		}
		if (userInput.isKeyReleaseEvent) {
			if (userInput.keys.size() == 0) {
				avatar.speed = 0;
			}
		}
	}

	/**
	 * Updates matrix for BFS
	 */
	private void updateMatrix() {
		for (int i = 0; i < 25; ++i) {
			for (int j = 0; j < 21; ++j) {
				if (!squareObjects[i][j].getTaken()) {
					matrix[i][j] = 1;
				} else {
					matrix[i][j] = 0;
				}
			}
		}
	}

	/**
	 * Spawns alien object
	 * 
	 * @param double diffSeconds - time elapsed
	 */
	protected void spawn(double diffSeconds) {
		final double INTERVAL = A_Const.SPAWN_INTERVAL;

		timeSinceLastShot += diffSeconds;
		if (timeSinceLastShot > INTERVAL) {
			timeSinceLastShot -= INTERVAL;
			Random rand = new Random();
			int n = rand.nextInt(3);
			TD_AlienAI alien = new TD_AlienAI(A_Type.values()[n + 3], startSquare, initRoute, startPoint[0], startPoint[1]);
			counterA.increment();
			gameObjects.add(alien);
			alienObjects.add(alien);
		}
	}

	/**
	 * Create new objects
	 * 
	 * @param double diffSeconds - time elapsed
	 */
	protected void createNewObjects(double diffSeconds) {
		if (!isBuilding) {
			spawn(diffSeconds);
			// delete HelpText after ... seconds
			if (helpText != null) {
				lifeHelpText -= diffSeconds;
				if (lifeHelpText < 0) {
					textObjects.remove(helpText);
					helpText = null;
				}
			}
		}
	}

	/**
	 * Removes old objects (aka unalived aliens or aliens that reached end destination)
	 */
	protected void deleteOldObjects() {
		ArrayList<TD_AlienAI> toBeRemoved = new ArrayList<TD_AlienAI>();
		for (TD_AlienAI alien : alienObjects) {
			if (this.endSquare.isCloseCenter(alien.x, alien.y)) {
				counterH.setNumber(counterH.getNumber() - 1);
				if (counterH.getNumber() == 0) {
					gameOver();
				}
				toBeRemoved.add(alien);
			}
			if (!alien.isLiving) {
				toBeRemoved.add(alien);
				counterC.add(alien.getCredits());
			}
		}

		for (TD_AlienAI alien : toBeRemoved) {
			gameObjects.remove(alien);
			alienObjects.remove(alien);
			counterA.decrement();
		}
	}

	/**
	 * Gameover
	 */
	public void gameOver() {
		while (true)
			;
	}
}
