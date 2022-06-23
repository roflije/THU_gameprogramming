 abstract class A_GameObject   {
	protected double x, y;
	protected double alfa = 0;
	protected double speed = 0;
	protected A_Shape shape;

	// if the object is existing, moving etc
	protected boolean isLiving = true;
	protected boolean isMoving = true;

	// destination the object shall move to,
	// old position etc
	private double destX, destY;
	private boolean hasDestination = false;
	private double xOld, yOld;

	// GameObjects sometimes call physics methods
	protected static A_PhysicsSystem physicsSystem;
	protected static A_World world;

	// construct GameObject
	public A_GameObject(double _x, double _y, double _a, double _s, A_Shape _shape) {
		x = _x;
		y = _y;
		xOld = x;
		yOld = y;
		alfa = _a;
		speed = _s;
		shape = _shape;
	}

	// move one step to direction <alfa>
	public void move(double diffSeconds) {
		if (!isMoving)
			return;

		// move if object has a destination
		if (hasDestination) {
			// stop if destination is reached
			double diffX = Math.abs(x - destX);
			double diffY = Math.abs(y - destY);
			if (diffX < 2 && diffY < 2) {
				isMoving = false;
				return;
			}
		}

		// remember old position
		xOld = x;
		yOld = y;

		// move one step
		x += Math.cos(alfa) * speed * diffSeconds;
		y += Math.sin(alfa) * speed * diffSeconds;
	}

	// test and reflect on Window Borders
	protected void reflectOnBorders() {
		double rad = this.shape.radius();
		double PI = Math.PI;

		if (x < rad && (alfa > PI / 2 && alfa < PI * 3 / 2)) {
			alfa = Math.PI - alfa;
		}
		if (y < rad && alfa > PI) {
			alfa = PI * 2 - alfa;
		}
		if (x > A_Const.WIDTH - rad) {
			alfa = Math.PI - alfa;
		}
		if (y > A_Const.HEIGHT - rad) {
			alfa = PI * 2 - alfa;
		}

		if (alfa < 0)
			alfa += PI * 2;
		if (alfa > PI * 2)
			alfa -= PI * 2;
	}

	// set a point in the world as destination
	public final void setDestination(double dx, double dy) {
		isMoving = true;
		hasDestination = true;
		destX = dx;
		destY = dy;

		alfa = Math.atan2(dy - y, dx - x);
	}

	// set the LOCATION of an object as destination
	public void setDestination(A_GameObject obj) {
		setDestination(obj.x, obj.y);
	}

	// move back to the position BEFORE the move Method was called
	protected void moveBack() {
		x = xOld;
		y = yOld;
	}

	abstract A_Type type();

	static void setPhysicsSystem(A_PhysicsSystem ps) {
		physicsSystem = ps;
	}

	static void setWorld(A_World w) {
		world = w;
	}

}
