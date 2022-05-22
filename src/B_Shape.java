import java.awt.Color;

//
// actual Interface containing the Appearance of an Object:
// it has radius and color
//

class B_Shape implements A_Shape {
	private int radius;
	Color color;

	public B_Shape(int r, Color c) {
		radius = r;
		color = c;
	}

	public final double radius() {
		return radius;
	}
}
