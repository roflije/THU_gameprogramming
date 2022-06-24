
// (c) Thorsten Hasbargen

class TD_PhysicsSystem extends A_PhysicsSystem {

	TD_PhysicsSystem(A_World w) {
		super(w);
	}

	//
	// collisions for circle Objects only...
	//
	public A_GameObjectList getCollisions(A_GameObject object) {
		A_GameObjectList result = new A_GameObjectList();

		int len = TD_World.gameObjects.size();
		for (int i = 0; i < len; i++) {
			A_GameObject obj2 = TD_World.gameObjects.get(i);

			// an object doesn't collide with itself
			if (obj2 == object)
				continue;

			// check if they touch each other
			double dist = object.shape.radius() + obj2.shape.radius();
			double dx = object.x - obj2.x;
			double dy = object.y - obj2.y;

			if (dx * dx + dy * dy < dist * dist) {
				result.add(obj2);
			}
		}

		return result;
	}

}