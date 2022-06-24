import java.util.LinkedList;

public class A_Square {
	private double sX; // start X
	private double sY; // start Y
	private double eX; // end X
	private double eY; // end Y
	private int i; // i-index in the array
	private int j; // j-index in the array
	private boolean taken; // square taken by a building
	private boolean isStart = false; // is start 
	private boolean isEnd = false; // is end

	/**
	 * Default constructor
	 * @param sx Start X
	 * @param sy Start Y
	 * @param ex End X
	 * @param ey End Y
	 * @param i i-index in the array
	 * @param j j-index in the array
	 */
	public A_Square(int sx, int sy, int ex, int ey, int i, int j) {
		this.sX = sx;
		this.sY = sy;
		this.eX = ex;
		this.eY = ey;
		this.i = i;
		this.j = j;
		this.taken = false;
	}

	/**
	 * Copy deep constructor
	 * @param cpy Square to be copied
	 */
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

	/**
	 * Returns middle of the square
	 * @return
	 */
	public double[] getMiddle() {
		double[] arr = new double[2];
		arr[0] = sX + ((eX - sX) / 2);
		arr[1] = sY + ((eY - sY) / 2);
		return arr;
	}

	/**
	 * Checks if point is within the square
	 * @param x x of the point
	 * @param y y of the point
	 * @return boolean Is within the square?
	 */
	public boolean isWithin(double x, double y) {
		return (x >= sX && x <= eX) && (y >= sY && y <= eY);
	}

	/**
	 * Checks if point is close to the center of the square
	 * @param x x of the point
	 * @param y y of the point
	 * @return boolean Is close to the center of the square?
	 */
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

	/**
	 * Static method that returns LinkedList of A_Squares from LinkedList of Cells
	 * @param path
	 * @return
	 */
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
