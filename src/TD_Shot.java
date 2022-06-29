import java.awt.Color;

class TD_Shot extends A_GameObject {
	private static final B_Shape SHAPE = new B_Shape(8, Color.yellow);

	private double lifeTime = 1000;
	private TD_AlienAI target;
	private A_Type owner;
	public TD_Shot(A_Type owner, double x, double y, double xDest, double yDest) {
		super(x, y, Math.atan2(yDest - y, xDest - x), 500, SHAPE);
		this.owner = owner;
		this.isMoving = true;
	}

	public TD_Shot(A_Type owner, double x, double y, double a, double s, double time) {
		super(x, y, a, s, SHAPE);
		lifeTime = time;
		this.isMoving = true;
		this.owner = owner;
		
		if(owner == A_Type.ALIEN_BIG ||
				owner == A_Type.ALIEN_SMALL ||
				owner == A_Type.ALIEN_MEDIUM)
		{
			SHAPE.color = Color.red;
		}
	}

	public TD_Shot(A_Type owner, double x, double y, TD_AlienAI target) {
		super(x, y, Math.atan2(target.y - y, target.x - x), 500, SHAPE);
		this.isMoving = true;
		this.target = target;
		this.owner = owner;
	}

	public void move(double diffSeconds) {
		if (target != null) {
			if (target.isLiving == false) {
				this.isLiving = false;
			}
			this.setDestination(target);
		}
		lifeTime -= diffSeconds;
		if (lifeTime <= 0) {
			this.isLiving = false;
			return;
		}

		A_GameObjectList collisions = physicsSystem.getCollisions(this);
		for (int i = 0; i < collisions.size(); i++) {
			A_GameObject obj = collisions.get(i);

			A_Type type = obj.type();

			// tree: shot is deleted
			if (owner == A_Type.PLAYER && (type == A_Type.TURRET || type == A_Type.SLOWER)) {
				this.isLiving = false;
			}
			// Zombie: inform Zombie it is hit
			else if ((type == A_Type.ALIEN_SMALL || type == A_Type.ALIEN_MEDIUM || type == A_Type.ALIEN_BIG) && obj.isLiving) {
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
