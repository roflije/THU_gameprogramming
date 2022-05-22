abstract class A_TextObject {
	protected static A_World world;

	// yes, public :(
	protected int x, y;
	public A_Shape shape;

	public A_TextObject(int x_, int y_, A_Shape s_) {
		x = x_;
		y = y_;
		shape = s_;
	}

	public abstract String toString();

	protected static void setWorld(A_World w) {
		world = w;
	}
}
