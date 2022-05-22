
public class A_Square {
	private int sX;
	private int sY;
	private int eX;
	private int eY;
	public A_Square(int sx, int sy, int ex, int ey) {
		this.sX = sx;
		this.sY = sy;
		this.eX = ex;
		this.eY = ey;
	}
	public int getsX() {
		return sX;
	}
	public int getsY() {
		return sY;
	}
	public int geteX() {
		return eX;
	}
	public int geteY() {
		return eY;
	}
	public String toString() {
		return "X1: " + sX + " X2: " + eX + " | Y1: " + sY + " Y2: " + eY;
	}
}
