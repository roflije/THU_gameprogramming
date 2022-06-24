import java.awt.Color;

class TD_Shot extends A_GameObject {
	private static final B_Shape SHAPE = new B_Shape(8, Color.yellow);

	private double lifeTime = 1000;

	public TD_Shot(double x, double y, double xDest, double yDest) {
		super(x, y, Math.atan2(yDest - y, xDest - x), 500, SHAPE);
		this.isMoving = true;
	}

	public TD_Shot(double x, double y, double a, double s, double time) {
		super(x, y, a, s, SHAPE);
		lifeTime = time;
		this.isMoving = true;
	}

	public void move(double diffSeconds) {
		lifeTime -= diffSeconds;
		if (lifeTime <= 0) {
			this.isLiving = false;
			return;
		}

		// handle collisions of the zombie
		A_GameObjectList collisions = physicsSystem.getCollisions(this);
		for (int i = 0; i < collisions.size(); i++) {
			A_GameObject obj = collisions.get(i);

			A_Type type = obj.type();

			// tree: shot is deleted
			if (type == A_Type.TURRET || type == A_Type.SLOWER) {
				this.isLiving = false;
			}
			// Zombie: inform Zombie it is hit
			else if ( (type == A_Type.ALIEN_SMALL || type== A_Type.ALIEN_MEDIUM || type== A_Type.ALIEN_BIG) && obj.isLiving) {
				TD_AlienAI alien = (TD_AlienAI) obj;
				alien.hasBeenShot();
				this.isLiving = false;
			}
		}

		super.move(diffSeconds);
	}

	public final A_Type type() {
		return A_Type.BULLET;
	}
}
