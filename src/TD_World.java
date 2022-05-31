import java.util.LinkedList;

class TD_World extends A_World {
	private double timePassed = 0;
	private double timeSinceLastShot = 0;

	private TD_CounterHealth counterH;
	private TD_Counter counterC;
	private TD_HelpText helpText;
	private double spawnGrenade = 0;

	private double lifeHelpText = 10.0;

	protected void init() {
		for(int x = 0, i = 0 ; x <= 960 ; x = x+40, ++i) {
			for(int y = 0, j = 0 ; y <= 800 ; y = y+40, ++j) {
				squareObjects[i][j] = new A_Square(x, y, x+40, y+40);
			}
		}
		A_Square startSquare = squareObjects[0][10];
		startSquare.setStart();
		A_Square endSquare = squareObjects[24][10];
		endSquare.setEnd();
		// add the Avatar
		avatar = new TD_Avatar(400, 500);
		gameObjects.add(avatar);

		// add one zombie
		double[] startPoint = startSquare.getMiddle();
		gameObjects.add(new TD_AlienAI(startSquare, startPoint[0],startPoint[1], 10));

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
				for(int ii = 0 ; ii < 25 && loop; ++ii)
					for(int jj = 0 ; jj < 21 && loop ; ++jj)
						if(squareObjects[ii][jj].isWithin((double)userInput.mousePressedX, (double)userInput.mousePressedY)) {
							i = ii;
							j = jj;
						}
				if(i != 25 && j != 21) {
					sqr = squareObjects[i][j];
				};
				if(sqr != null && !sqr.getTaken()) {
					sqr.take();
					int[][] startend = {{0,0},{24,20}};
					LinkedList<Cell> cells = BFS.shortestPath(compose2DMatrix(), startend[0],startend[1]);
					if(cells == null) {
						System.out.println("Cannot create path!");
						sqr.notTake();
					} else {
						double[] middle = sqr.getMiddle();
						gameObjects.add(new TD_Turret(middle[0],middle[1],20));	
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
				compose2DMatrix();
			}
		}
	}

	private int[][] compose2DMatrix(){
		int[][] result = new int[25][21];
		for(int i = 0 ; i < 25 ; ++i) {
			for(int j = 0 ; j < 21 ; ++j) {
				if(!squareObjects[i][j].getTaken()) {
					result[i][j] = 1;
				} else {
					result[i][j] = 0;
				}
			}
		}
		return result;
	}
	protected void createNewObjects(double diffSeconds) {
		//createZombie(diffSeconds);

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
