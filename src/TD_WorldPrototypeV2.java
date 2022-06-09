import java.util.ArrayList;
import java.util.LinkedList;

class TD_WorldPrototypeV2 extends A_World {
	public static int[][] matrix = new int[25][21];

	private double timeSinceLastShot = 0;
	
	private TD_CounterHealth counterH;
	private TD_Counter counterC;
	private TD_HelpText helpText;

	private ArrayList<TD_AlienAI> monsterObject = new ArrayList<TD_AlienAI>();
	private LinkedList<A_Square> initRoute;

	private double lifeHelpText = 10.0;
	int[][] startend = { { 0, 10 }, { 24, 10 } };

	protected void init() {
		for (int x = 0, i = 0; x <= 960; x = x + 40, ++i) {
			for (int y = 0, j = 0; y <= 800; y = y + 40, ++j) {
				squareObjects[i][j] = new A_Square(x, y, x + 40, y + 40, i, j);
			}
		}
		A_Square startSquare = squareObjects[0][10];
		startSquare.setStart();
	    A_Square   endSquare = squareObjects[24][10];
		endSquare.setEnd();
		// add the Avatar
	 TD_Avatar	avatar = new TD_Avatar(400, 500);
		 
		gameObjects.add(avatar);

		// calculate initial route
		updateMatrix();
		initRoute = A_Square.getPathFromCellList(BFS.shortestPath(startend[0], startend[1]));
		// add one zombie
		double[] startPoint = startSquare.getMiddle();
		TD_AlienAI monster = new TD_AlienAI(startSquare, initRoute, startPoint[0], startPoint[1], 10);
		gameObjects.add(monster);
		monsterObject.add(monster);
 
		
		
		counterC = new TD_Counter(20, 80);
		counterH = new TD_CounterHealth(20, 40);
		helpText = new TD_HelpText(100, 400);
		
	//	c = new TD_CounterMonsterHealth(390,480);
		textObjects.add(counterC);
		textObjects.add(counterH);
		textObjects.add(helpText);
		textObjects.add(avatar.counter());
	//	textObjects.add(c);
		 
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
		if (userInput.isKeyEvent) {
			if (userInput.keyPressed == '2') {
				toggleBuilding();
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

	protected void createNewObjects(double diffSeconds) {
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
