import java.awt.Color;
import java.util.LinkedList;

class TD_AlienAI extends A_GameObject {

	private A_Square dest;
	double[] destCoords = {0,0};
	private LinkedList<A_Square> route;
	private A_Square current;
	private boolean routeChanged = false;
	// life of a zombie
	private int life;

	private static final B_Shape SHAPE = new B_Shape(15, new Color(160, 80, 40));

	public TD_AlienAI(A_Square spawn, LinkedList<A_Square> route, double x, double y, int life) {
		super(x, y, 0, 40, SHAPE);
		this.current = spawn;
		this.isMoving = true;
		this.route = route;
		this.dest = route.pollFirst();
		// turn left or right to clear
	}

	public void updatePath(A_Square[][] squares, int[][] matrix) {
		int[] currentSqr = {current.getI(), current.getJ()};
		this.route = A_Square.getPathFromCellList(squares, BFS.shortestPath(matrix, currentSqr, A_Const.END));
		System.out.println("NEW MONSTER PATH: " + this.route);
		this.routeChanged = true;
	}

	public void move(double diffSeconds) {
		if(!routeChanged && !dest.isWithin(this.x, this.y)){
			super.move(diffSeconds);
			return;
		} else if (!route.isEmpty()) {
			this.routeChanged = false;
			current = dest;
			dest = route.pollFirst();
			destCoords = dest.getMiddle();
			this.setDestination(destCoords[0], destCoords[1]);
			System.out.println("NEW TARGET: ["+destCoords[0]+","+destCoords[1]+"]");
			super.move(diffSeconds);
		}

	}

	// inform zombie it is hit
	public void hasBeenShot() {
		// every shot decreases life
		life -= 1;

		// if Zombie is dead (haha), delete it
		if (life <= 0) {
			this.isLiving = false;
			return;
		}
	}

	public A_Type type() {
		return A_Type.ALIEN;
	}

}
