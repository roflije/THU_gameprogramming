import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

class TD_World extends A_World {
	public static int[][] matrix = new int[25][21];

	private double timeSinceLastShot = 0;

	private TD_CounterHealth counterH;
	private TD_Counter counterC;
	private TD_HelpText helpText;

	private ArrayList<TD_AlienAI> monsterObject = new ArrayList<TD_AlienAI>();
	private LinkedList<A_Square> initRoute;
	private  double[] startPoint;
	private  A_Square startSquare;

	private double lifeHelpText = 10.0;
	int[][] startend = { { 0, 10 }, { 24, 10 } };

	protected void init() {
		for (int x = 0, i = 0; x <= 960; x = x + 40, ++i) {
			for (int y = 0, j = 0; y <= 800; y = y + 40, ++j) {
				squareObjects[i][j] = new A_Square(x, y, x + 40, y + 40, i, j);
			}
		}
		startSquare = squareObjects[0][10];
		startSquare.setStart();
		A_Square endSquare = squareObjects[24][10];
		endSquare.setEnd();
		// add the Avatar
		avatar = new TD_Avatar(400, 500);
		gameObjects.add(avatar);

		// calculate initial route
		updateMatrix();
		initRoute = A_Square.getPathFromCellList(BFS.shortestPath(startend[0], startend[1]));
		// add one zombie
		startPoint = startSquare.getMiddle();
		TD_AlienAI monster = new TD_AlienAI(startSquare, initRoute, startPoint[0], startPoint[1], 10);
		gameObjects.add(monster);
		monsterObject.add(monster);

		counterC = new TD_Counter(20, 80);
		counterH = new TD_CounterHealth(20, 40);
		helpText = new TD_HelpText(100, 400);

		textObjects.add(counterC);
		textObjects.add(counterH);
		textObjects.add(helpText);


	}

	protected void processUserInput(A_UserInput userInput, double diffSeconds) {
		// distinguish if Avatar shall move or shoots
		int button = userInput.mouseButton;

		//
		if (userInput.isMouseEvent) {
			if (button == 1 && super.isBuilding) {
				A_Square sqr = null;
				int i = 0;
				int j = 0;
				boolean loop = true;
				for (int ii = 0; ii < 25 && loop; ++ii)
					for (int jj = 0; jj < 21 && loop; ++jj)
						if (squareObjects[ii][jj].isWithin((double) userInput.mousePressedX,
								(double) userInput.mousePressedY)) {
							i = ii;
							j = jj;
						}
				if (i != 25 && j != 21) {
					sqr = squareObjects[i][j];
				}
				;
				if (sqr != null && !sqr.getTaken()) {
					sqr.take();
					updateMatrix();
					LinkedList<Cell> cells = BFS.shortestPath(startend[0], startend[1]);
					if (cells == null) {
						System.out.println("Cannot create path from start!");
						sqr.notTake();
					} else {
						boolean cannotCreate = false;
						for (TD_AlienAI a : monsterObject) {
							if (!a.updatePath()) // sqr.isWithin(a.x, a.y)
								cannotCreate = true;
						}
						if (cannotCreate) {
							System.out.println("Cannot create path from start!");
							sqr.notTake();
						} else {
							double[] middle = sqr.getMiddle();
							gameObjects.add(new TD_Turret(middle[0], middle[1], 20));
						}

					}
				}
			}
		}

		//
		// Mouse still pressed?
		//
		if (userInput.isMousePressed && button == 3) {
			// only 1 shot every ... seconds:
			timeSinceLastShot += diffSeconds;
			if (timeSinceLastShot > 0.2) {
				timeSinceLastShot = 0;

				TD_Shot shot = new TD_Shot(avatar.x, avatar.y, userInput.mouseMovedX, userInput.mouseMovedY);
				this.gameObjects.add(shot);
			}
		}

		//
		// Keyboard events
		//
		if (userInput.isKeyPressEvent) {
			if (userInput.keyPressed == '2') {
				toggleBuilding();
				
			}
		}


		if (userInput.isKeyPressEvent || userInput.isKeyReleaseEvent)  {
			if (userInput.keys.contains(KeyEvent.VK_W)&& userInput.keys.size()== 1) {
				userInput.keys.toString();
				avatar.speed = 50;
				avatar.alfa = Math.PI * -0.5;
				//	System.out.println(avatar.speed);
			}
		}

		if (userInput.isKeyReleaseEvent)  {
			if ( userInput.keys.size() == 0) {
				avatar.speed = 0;
				//	System.out.println(avatar.speed);
			}
		}


		if (userInput.isKeyPressEvent)  {
			if (userInput.keys.contains(KeyEvent.VK_W)&&
					userInput.keys.contains(KeyEvent.VK_A) ) {
				avatar.speed = 50;
				avatar.alfa = Math.PI * -0.75;
				//	System.out.println(avatar.speed);
			}
		}

		if (userInput.isKeyPressEvent)  {
			if (userInput.keys.contains(KeyEvent.VK_W)&&
					userInput.keys.contains(KeyEvent.VK_D)) {

				avatar.speed = 50;
				avatar.alfa = Math.PI * -0.25;
				//	System.out.println(avatar.speed);
			}
		}


		if (userInput.isKeyPressEvent || userInput.isKeyReleaseEvent) {
			if (userInput.keys.contains(KeyEvent.VK_A)&& userInput.keys.size()== 1) {
				if(avatar.alfa == Math.PI * 2)
					avatar.speed = 50;
				avatar.alfa = -Math.PI;
				//	System.out.println(avatar.speed);
			}
		}

		if (userInput.isKeyPressEvent)  {
			if (userInput.keys.contains(KeyEvent.VK_A)&&
					userInput.keys.contains(KeyEvent.VK_S)) {
				avatar.speed = 50;
				avatar.alfa = Math.PI * -1.25;
				//	System.out.println(avatar.speed);
			}
		}

		if (userInput.isKeyPressEvent || userInput.isKeyReleaseEvent) {
			if (userInput.keys.contains(KeyEvent.VK_S)&& userInput.keys.size()== 1) {
				avatar.speed = 50;
				avatar.alfa = -Math.PI * 1.5;
				//System.out.println(avatar.speed);
			}
		}

		if (userInput.isKeyPressEvent)  {
			if (userInput.keys.contains(KeyEvent.VK_S)&&
					userInput.keys.contains(KeyEvent.VK_D)) {
				avatar.speed = 50;
				avatar.alfa = Math.PI * 0.25;
				//	System.out.println(avatar.speed);
			}
		}


		if (userInput.isKeyPressEvent || userInput.isKeyReleaseEvent) {
			if (userInput.keys.contains(KeyEvent.VK_D) && userInput.keys.size()== 1) {
				avatar.speed = 50;
				avatar.alfa = Math.PI * 2;
				//System.out.println(avatar.speed);
			}
		}
	}







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

	/* This functions works randomly 
	 * After a fixed time it results to null pointer for bigger intervals.
	 * For shorter intervals it crashes after certain amount of aliens/zombies
	 * Collusion detection and BFS works at random. Sometimes monster change they course
	 * sometimes not.
	 */

	protected void spawn(double diffSeconds)
	{
		final double INTERVAL = A_Const.SPAWN_INTERVAL;

		timeSinceLastShot += diffSeconds;
		if(timeSinceLastShot>INTERVAL)
		{
			timeSinceLastShot -= INTERVAL;

			// create new Zombie
			//				double x = 20+Math.random()*960;
			//				double y = 20+Math.random()*760;

			// if too close to Avatar, cancel
			//				double dx = x-avatar.x;
			//				double dy = y-avatar.y;
			//				if(dx*dx+dy*dy < 200*200) 
			//				{ timeSinceLastShot = INTERVAL;
			//				return;
			//				}


			// if collisions occur, cancel
			TD_AlienAI monster = new TD_AlienAI(startSquare , initRoute, startPoint[0], startPoint[1], 10);
			//				A_GameObjectList list = A_GameObject.physicsSystem.getCollisions(monster);
			//				if(list.size()!=0)
			//				{ timeSinceLastShot = INTERVAL;
			//				return;
			//				}

			// else add monster to world
			this.gameObjects.add(monster);
			this.monsterObject.add(monster);
		}

	}

	protected void createNewObjects(double diffSeconds) {
		//spawn(diffSeconds);
		// createZombie(diffSeconds);

		// delete HelpText after ... seconds
		if (helpText != null) {
			lifeHelpText -= diffSeconds;
			if (lifeHelpText < 0) {
				textObjects.remove(helpText);
				helpText = null;
			}
		}
	}

	public void gameOver() {
		while (true)
			;
	}
}
