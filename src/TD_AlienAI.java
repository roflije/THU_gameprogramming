import java.awt.Color;
import java.util.LinkedList;

class TD_AlienAI extends A_GameObject {

	private A_Square dest; // destination
	double[] destCoords = { 0, 0 }; // coordinates of destination
	private LinkedList<A_Square> route; // route of the zombie
	private A_Square current; // current square position
	private boolean routeChanged = false; // if route changed
	private int life; // life of a zombie
	private int credits;
	private static final B_Shape SHAPE = new B_Shape(10, new Color(255, 0, 0));
	private A_Type type;

	/**
	 * Alien constructor
	 * 
	 * @param spawn Spawn square
	 * @param route Initial route
	 * @param x     Position X
	 * @param y     Position Y
	 * @param life  Life of the alien
	 */
	public TD_AlienAI(A_Type type, A_Square spawn, LinkedList<A_Square> route, double x, double y) {
		super(x, y, 0, 40, SHAPE);
		this.type = type;
		switch (this.type) {
			case ALIEN_SMALL:
				this.life = 15;
				this.credits = 50;
				this.speed = 150;
				break;
			case ALIEN_MEDIUM:
				this.life = 40;
				this.credits = 100;
				this.speed = 75;
				break;
			case ALIEN_BIG:
				this.life = 80;
				this.credits = 200;
				this.speed = 25;
				break;
			case ALIEN_IMMUNE:
				this.life = 40;
				this.credits = 100;
				this.speed = 75;
			default:
				break;
		}

		this.current = spawn;
		this.isMoving = true;
		this.route = new LinkedList<A_Square>();
		for (A_Square sqr : route) { // copy elements from initial route to its own route
			this.route.add(new A_Square(sqr));
		}
		this.dest = this.route.pollFirst(); // first destination is the first element of the route
	}

	/**
	 * Updates path of the Alien
	 * 
	 * @return boolean Path updated?
	 */
	public boolean updatePath() {
		// check own position
		int[] currentSqr = new int[2];
		if (dest.isWithin(x, y)) { // if reached current destination, update current position
			currentSqr[0] = dest.getI();
			currentSqr[1] = dest.getJ();
		} else { // if not reached current destiantion, take current position
			currentSqr[0] = current.getI();
			currentSqr[1] = current.getJ();
		}
		// calculate new route for the alien from current position
		LinkedList<A_Square> newRoute = A_Square.getPathFromCellList(BFS.shortestPath(currentSqr, A_Const.END));
		if (newRoute == null) // if no route, return false
			return false;
		this.route = newRoute; // update route
		this.routeChanged = true; // update flag that route changed
		return true;
	}

	public void move(double diffSeconds) {
		if (!routeChanged && !dest.isCloseCenter(this.x, this.y)) { // if route hasnt changed and not close to current
																	// destination -> move forward towards it
			super.move(diffSeconds);
			return;
		} else if (!route.isEmpty()) { // update destination with next one from the route
			this.routeChanged = false;
			current = dest;
			dest = route.pollFirst();
			destCoords = dest.getMiddle();
			this.setDestination(destCoords[0], destCoords[1]);
			super.move(diffSeconds);
		}

	}

	public void hasBeenShot() {
		life -= 5;
		if (life <= 0) {
			this.isLiving = false;
			return;
		}
	}
	
	public void hasBeenBlasted() {
		life -= 99999;
		if (life <= 0) {
			this.isLiving = false;
			return;
		}
	}

	public int getCredits() {
		return credits;
	}

	public A_Type type() {
		return this.type;
	}

	public void fast(){
		this.speed *= 2;
	}

	public void slow() {
		this.speed /= 2;
	}
}
