import java.awt.Color;

class TD_Turret extends A_GameObject {
	protected static final Color COLOR = new Color(64, 160, 64);

	public TD_Turret(double x, double y, int r) {
		super(x, y, 0, 0, new B_Shape(r, COLOR));
		this.isMoving = false;
	}

	public A_Type type() {
		return A_Type.TURRET;
	}
}
