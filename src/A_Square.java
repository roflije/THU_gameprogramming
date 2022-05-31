import java.util.LinkedList;
import java.util.Queue;

public class A_Square {
	private double sX;
	private double sY;
	private double eX;
	private double eY;
	private boolean taken;
	private boolean isStart = false;
	private boolean isEnd = false;
	public A_Square(int sx, int sy, int ex, int ey) {
		this.sX = sx;
		this.sY = sy;
		this.eX = ex;
		this.eY = ey;
		this.taken = false;
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
		arr[0] = sX + ((eX-sX)/2);
		arr[1] = sY + ((eY-sY)/2);
		return arr;
	}
	public boolean isWithin(double x, double y) {
		return (x >= sX && x <= eX) && (y >= sY && y <= eY);
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
	public static Queue<A_Square> getPathFromCellList(LinkedList<Cell> path){
		Queue<A_Square> q = new LinkedList<A_Square>();
		while(!path.isEmpty()) {
			Cell c = path.getFirst();
		}
		return q;
	}
}
