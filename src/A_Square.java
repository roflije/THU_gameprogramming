import java.util.LinkedList;

public class A_Square {
	private double sX;
	private double sY;
	private double eX;
	private double eY;
	private int i;
	private int j;
	private boolean taken;
	private boolean isStart = false;
	private boolean isEnd = false;

	public A_Square(int sx, int sy, int ex, int ey, int i, int j) {
		this.sX = sx;
		this.sY = sy;
		this.eX = ex;
		this.eY = ey;
		this.i = i;
		this.j = j;
		this.taken = false;
	}

	public A_Square(A_Square cpy){
		this.sX = cpy.sX;
		this.sY = cpy.sY;
		this.eX = cpy.eX;
		this.eY = cpy.eY;
		this.i = cpy.i;
		this.j = cpy.j;
		this.taken = cpy.taken;
	}

	public double getsX() {
		return sX;
	}

	public double getsY() {
		return sY;
	}

	public double geteX() {
		return eX;
	}

	public double geteY() {
		return eY;
	}

	public String toString() {
		return "X1: " + sX + " X2: " + eX + " | Y1: " + sY + " Y2: " + eY;
	}

	public double[] getMiddle() {
		double[] arr = new double[2];
		arr[0] = sX + ((eX - sX) / 2);
		arr[1] = sY + ((eY - sY) / 2);
		return arr;
	}

	public boolean isWithin(double x, double y) {
		return (x >= sX && x <= eX) && (y >= sY && y <= eY);
	}
	public boolean isCloseCenter(double x, double y){
		double[] arr = getMiddle();
		return (
			x < arr[0] + A_Const.CLOSE_DISTANCE &&
			x > arr[0] - A_Const.CLOSE_DISTANCE &&
			y < arr[1] + A_Const.CLOSE_DISTANCE &&
			y > arr[1] - A_Const.CLOSE_DISTANCE
		);
	}

	public boolean getTaken() {
		return taken;
	}

	public void take() {
		this.taken = true;
	}

	public void notTake() {
		this.taken = false;
	}

	public void setStart() {
		this.isStart = true;
	}

	public boolean isStart() {
		return this.isStart;
	}

	public void setEnd() {
		this.isEnd = true;
	}

	public boolean isEnd() {
		return this.isEnd;
	}

	public int getI() {
		return this.i;
	}

	public int getJ() {
		return this.j;
	}

	public static LinkedList<A_Square> getPathFromCellList(LinkedList<Cell> path) {
		if (path == null)
			return null;
		LinkedList<A_Square> q = new LinkedList<A_Square>();
		while (!path.isEmpty()) {
			Cell c = path.remove();
			q.add(A_World.squareObjects[c.x][c.y]);
		}
		if (q.isEmpty()) {
			return null;
		}
		return q;
	}
}
