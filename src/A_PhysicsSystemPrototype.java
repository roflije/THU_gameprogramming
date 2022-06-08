abstract class A_PhysicsSystemPrototype {
	protected A_WorldPrototype world;

	public A_PhysicsSystemPrototype(A_WorldPrototype w) {
		world = w;
	}

	protected abstract A_GameObjectList getCollisions(A_GameObject object);

	protected double distance(double x1, double y1, double x2, double y2) {
		double xd = x1 - x2;
		double yd = y1 - y2;
		return Math.sqrt(xd * xd + yd * yd);
	}

	//
	// move object "back" reverse alfa until it just does not collide
	//
	public void moveBackToUncollide(A_GameObject object) {
		double dx = Math.cos(object.alfa);
		double dy = Math.sin(object.alfa);

		while (true) {
			object.x -= dx;
			object.y -= dy;

			A_GameObjectList collisions = getCollisions(object);
			if (collisions.size() == 0)
				break;
		}
	}

}
