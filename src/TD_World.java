class TD_World extends A_World {
	private double timePassed = 0;
	private double timeSinceLastShot = 0;

	private TD_CounterHealth counterH;
	private TD_Counter counterC;
	private TD_HelpText helpText;
	private double spawnGrenade = 0;

	private double lifeHelpText = 10.0;

	protected void init() {
		for(int x = 0 ; x <= A_Const.WIDTH ; x = x+40) {
			for(int y = 0 ; y <= A_Const.HEIGHT ; y = y+40) {
				squareObjects.add(new A_Square(x, y, x+40, y+40));
			}
		}
		// add the Avatar
		avatar = new TD_Avatar(400, 500);
		gameObjects.add(avatar);

		// add one zombie
		//gameObjects.add(new TD_AlienAI(100, 100));

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
		// Mouse events
		//
		if (userInput.isMouseEvent) {
			if (button == 1 && super.isBuilding) {
				A_Square sqr = null;
				int i;
				int sizeOfSquares = squareObjects.size();
				for(i = 0 ; i < sizeOfSquares && !squareObjects.get(i).isWithin(userInput.mousePressedX, userInput.mousePressedY); ++i);
				if(i != sizeOfSquares) {
					sqr = squareObjects.get(i);
				};
				if(sqr != null && !sqr.getTaken()) {
					int[] middle = sqr.getMiddle();
					gameObjects.add(new TD_Turret(middle[0],middle[1],20));
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

	/*
	private void throwGrenade(double x, double y) {
		if (grenades <= 0)
			return;

		// throw grenade
		for (int i = 0; i < 2000; i++) {
			double alfa = Math.random() * Math.PI * 2;
			double speed = 50 + Math.random() * 200;
			double time = 0.2 + Math.random() * 0.4;
			TD_Shot shot = new TD_Shot(x, y, alfa, speed, time);
			this.gameObjects.add(shot);
		}

		// inform counter
		grenades--;
		counterG.setNumber(grenades);
	}
	*/
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

	/*
	 * private void createGrenade(double diffSeconds) { final double INTERVAL =
	 * A_Const.SPAWN_GRENADE;
	 * 
	 * spawnGrenade += diffSeconds; if(spawnGrenade>INTERVAL) { spawnGrenade -=
	 * INTERVAL;
	 * 
	 * // create new Grenade double x = 20+Math.random()*960; double y =
	 * 20+Math.random()*760;
	 * 
	 * // if too close to Avatar, cancel double dx = x-avatar.x; double dy =
	 * y-avatar.y; if(dx*dx+dy*dy < 200*200) { spawnGrenade = INTERVAL; return; }
	 * 
	 * 
	 * // if collisions occur, cancel TD_Grenade grenade = new TD_Grenade(x,y);
	 * A_GameObjectList list = A_GameObject.physicsSystem.getCollisions(grenade);
	 * if(list.size()!=0) { spawnGrenade = INTERVAL; return; }
	 * 
	 * // else add zombie to world this.gameObjects.add(grenade);
	 * counterG.setNumber(grenades); }
	 * 
	 * }
	 */

	private void createZombie(double diffSeconds) {
		final double INTERVAL = A_Const.SPAWN_INTERVAL;

		timePassed += diffSeconds;
		if (timePassed > INTERVAL) {
			timePassed -= INTERVAL;

			// create new Zombie
			double x = 20 + Math.random() * 960;
			double y = 20 + Math.random() * 760;

			// if too close to Avatar, cancel
			double dx = x - avatar.x;
			double dy = y - avatar.y;
			if (dx * dx + dy * dy < 200 * 200) {
				timePassed = INTERVAL;
				return;
			}

			// if collisions occur, cancel
			TD_AlienAI zombie = new TD_AlienAI(x, y);
			A_GameObjectList list = A_GameObject.physicsSystem.getCollisions(zombie);
			if (list.size() != 0) {
				timePassed = INTERVAL;
				return;
			}

			// else add zombie to world
			this.gameObjects.add(zombie);
			zombie.setDestination(avatar);
			TD_Counter counter = (TD_Counter) textObjects.get(0);
			counter.increment();
		}

	}
	/*
	public void addGrenade() {
		if (grenades < 3) {
			grenades++;
		}
		counterG.setNumber(grenades);
	}
	*/
	public void gameOver() {
		while (true)
			;
	}
}
