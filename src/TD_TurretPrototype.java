import java.awt.Color;

class TD_TurretPrototype extends A_GameObject {
	protected static final Color COLOR = new Color(100, 100, 100);

	public TD_TurretPrototype(int x, int y, int m) {
		super(x, y, 0, 0, new B_Shape(m, COLOR));
		this.isMoving = false;
	}

	public A_Type type() {
		return A_Type.TURRET;
	}
}
