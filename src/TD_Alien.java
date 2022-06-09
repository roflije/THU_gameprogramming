import java.awt.Color;
import java.util.ArrayList;

class TD_Alien extends A_GameObject {
	private static final Color COLOR = new Color(160, 80, 40);

	
	
	
	public TD_Alien(double x, double y) {
		super(x, y, 0, 70, new B_Shape(15, COLOR));
		this.isMoving = false;
	
	}

	public void move(double diffSeconds) {
		this.setDestination(world.avatar);

		super.move(diffSeconds);

		// handle collisions of the zombie
		ArrayList<A_GameObject> collisions = physicsSystem.getCollisions(this);
		for (int i = 0; i < collisions.size(); i++) {
			A_GameObject obj = collisions.get(i);

			A_Type type = obj.type();

			// if object is avatar, game over
			if (type == A_Type.PLAYER) {
				
			}
		}
	}

	public A_Type type() {
		return A_Type.ALIEN;
	}
}
