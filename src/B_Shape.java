import java.awt.Color;

//
// actual Interface containing the Appearance of an Object:
// it has radius and color
//

class B_Shape implements A_Shape {
	private int radius;
	Color color;
	private A_Type type;

	public B_Shape(int r, Color c) {
		radius = r;
		color = c;
	}
	
	public B_Shape(int r, Color c, A_Type t) {
		radius = r;
		color = c;
		type = t;
	}
	

	public final double radius() {
		return radius;
	}
	
	public  A_Type  type() {
		return type;
	}
	
 
}
