import java.awt.Color;

class TD_AlienAI extends A_GameObject {
	private static final int HUNTING = 1;
	private static final int STUCK = 2;
	private static final int CLEARING = 3;

	private int state;
	private double alfaClear;
	private double secondsClear;

	// life of a zombie
	private double life = 1.0;

	private static final B_Shape SHAPE = new B_Shape(15, new Color(160, 80, 40));

	public TD_AlienAI(double x, double y) {
		super(x, y, 0, 40, SHAPE);
		this.isMoving = false;

		state = HUNTING;

		// turn left or right to clear
		alfaClear = Math.PI;
		if (Math.random() < 0.5)
			alfaClear = -alfaClear;

	}

	public void move(double diffSeconds) {
		// state HUNTING
		//

		if (state == HUNTING) {
			this.setDestination(world.avatar);

			super.move(diffSeconds);

			// handle collisions of the zombie
			A_GameObjectList collisions = physicsSystem.getCollisions(this);
			for (int i = 0; i < collisions.size(); i++) {
				A_GameObject obj = collisions.get(i);

				A_Type type = obj.type();

				// if object is avatar, game over
				if (type == A_Type.PLAYER) {
					world.gameOver();
				}

				// if object is zombie, step back
				if (type == A_Type.ALIEN) {
					moveBack();
					state = STUCK;
					return;
				}

				// if Object is a tree, move back one step
				if (obj.type() == A_Type.TURRET) {
					moveBack();
					state = STUCK;
					return;
				}
			}
		}

		// state STUCK
		//

		else if (state == STUCK) {
			// seconds left for clearing
			secondsClear = 1.0 + Math.random() * 0.5;
			// turn and hope to get clear
			alfa += alfaClear * diffSeconds;

			// try to clear
			state = CLEARING;
		}

		// state CLEARING
		//
		else if (state == CLEARING) {
			// check, if the clearing time has ended
			secondsClear -= diffSeconds;
			if (secondsClear < 0) {
				state = HUNTING;
				return;
			}

			// try step in this direction
			super.move(diffSeconds);

			// check if path was unblocked
			A_GameObjectList collisions = physicsSystem.getCollisions(this);
			if (collisions.size() > 0) {
				moveBack();

				// stuck again
				this.state = STUCK;
				return;
			}

		}
	}

	// inform zombie it is hit
	public void hasBeenShot() {
		// every shot decreases life
		life -= 0.21;

		// if Zombie is dead (haha), delete it
		if (life <= 0) {
			this.isLiving = false;
			return;
		}
	}

	public A_Type type() {
		return A_Type.ALIEN;
	}
}
