import java.awt.Color;

class TD_Turret extends A_GameObject {
	protected static final Color COLOR = new Color(100, 100, 100);
	private TD_AlienAI target = null;
	private double timeSinceLastShot = 0.0;

	public TD_Turret(double x, double y, int r) {
		super(x, y, 0, 0, new B_Shape(r, COLOR, A_Type.TURRET));
		this.isMoving = false;
	}

	@Override
	public void move(double diffSeconds) {
		timeSinceLastShot += diffSeconds;
		if (timeSinceLastShot > 1) {
			target = findNearestMonster();
			if (target != null) {
				TD_Shot shot = new TD_Shot(super.x, super.y, target);
				TD_World.gameObjects.add(shot);
				timeSinceLastShot = 0;
			}
		}

	}

	private TD_AlienAI findNearestMonster() {
		TD_AlienAI result = null;
		double distance = Double.MAX_VALUE;
		for (TD_AlienAI alien : TD_World.alienObjects) {
			if (this.x - A_Const.TURRET_RANGE < alien.x && alien.x < this.x + A_Const.TURRET_RANGE && this.y - A_Const.TURRET_RANGE < alien.y && alien.y < this.y + A_Const.TURRET_RANGE && Math.abs(calculateDistance(this.x, this.y, alien.x, alien.y)) < distance) {
				result = alien;
				distance = Math.abs(calculateDistance(this.x, this.y, alien.x, alien.y));
			}
		}
		return result;
	}

	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public A_Type type() {
		return A_Type.TURRET;
	}
}
