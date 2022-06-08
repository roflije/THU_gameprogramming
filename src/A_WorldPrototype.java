import java.util.ArrayList;

abstract class A_WorldPrototype {
	private A_GraphicSystem graphicSystem;
	private A_PhysicsSystem physicsSystem;
	private A_InputSystem inputSystem;
	private A_UserInput userInput;
	protected boolean isBuilding = false;
	private static final int FRAME_MINIMUM_MILLIS = 10;

	// all objects in the game, including the Avatar
	A_GameObjectList gameObjects = new A_GameObjectList();
	A_GameObject avatar;
	ArrayList<A_TextObject> textObjects = new ArrayList<A_TextObject>();
	ArrayList<A_Square> squareObjects = new ArrayList<A_Square>();

	A_WorldPrototype() {
		physicsSystem = new TD_PhysicsSystemPrototype(this);
	}

	// the main GAME LOOP
	final void run() {
		long lastTick = System.currentTimeMillis();

		while (true) {
			// calculate elapsed time
			long currentTick = System.currentTimeMillis();
			long millisDiff = currentTick - lastTick;

			// don�t run faster then MINIMUM_DIFF_SECONDS per frame
			if (millisDiff < FRAME_MINIMUM_MILLIS) {
				try {
					Thread.sleep(FRAME_MINIMUM_MILLIS - millisDiff);
				} catch (Exception ex) {
				}
				currentTick = System.currentTimeMillis();
				millisDiff = currentTick - lastTick;
			}

			lastTick = currentTick;

			// process User Input
			userInput = inputSystem.getUserInput();
			processUserInput(userInput, millisDiff / 1000.0);
			userInput.clear();
			
			int gameSize = gameObjects.size();
			if (!isBuilding) {
				// move all Objects, maybe collide them etc...
				for (int i = 0; i < gameSize; i++) {
					A_GameObject obj = gameObjects.get(i);
					if (obj.isLiving)
						obj.move(millisDiff / 1000.0);
				}

				// delete all Objects which are not living anymore
				int num = 0;
				while (num < gameSize) {
					if (gameObjects.get(num).isLiving == false) {
						gameObjects.remove(num);
						gameSize--;
					} else {
						num++;
					}
				}
			}
			// draw all Objects
			graphicSystem.clear();
			for (int i = 0; i < gameSize; i++) {
				graphicSystem.draw(gameObjects.get(i));
			}
			if (isBuilding) {
				int howManySquares = squareObjects.size();
				for (int i = 0; i < howManySquares; ++i) {
					graphicSystem.draw(squareObjects.get(i));
				}
			}
			// draw all TextObjects
			for (int i = 0; i < textObjects.size(); i++) {
				graphicSystem.draw(textObjects.get(i));
			}

			// redraw everything
			graphicSystem.redraw();

			// create new objects if needed
			createNewObjects(millisDiff / 1000.0);
		}
	}

	protected void setGraphicSystem(A_GraphicSystem p) {
		graphicSystem = p;
	}

	protected void setInputSystem(A_InputSystem p) {
		inputSystem = p;
	}

	protected A_PhysicsSystem getPhysicsSystem() {
		return physicsSystem;
	}

	protected void toggleBuilding() {
		this.isBuilding = !this.isBuilding;
	}

	protected abstract void init();

	protected abstract void processUserInput(A_UserInput input, double diffSec);

	protected abstract void createNewObjects(double diffSeconds);
	

	protected abstract void gameOver();

}
