
public class A_Square {
	private int sX;
	private int sY;
	private int eX;
	private int eY;
	private boolean taken;
	public A_Square(int sx, int sy, int ex, int ey) {
		this.sX = sx;
		this.sY = sy;
		this.eX = ex;
		this.eY = ey;
		this.taken = false;
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
	public int[] getMiddle() {
		int[] arr = new int[2];
		arr[0] = sX + ((eX-sX)/2);
		arr[1] = sY + ((eY-sY)/2);
		return arr;
	}
	public boolean isWithin(int x, int y) {
		return (x >= sX && x <= eX) && (y >= sY && y <= eY);
	}
	public boolean getTaken() {
		return taken;
	}
	public void take() {
		this.taken = true;
	}
}
