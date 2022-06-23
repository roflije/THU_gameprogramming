import java.awt.Color;

public class TD_Avatar extends A_GameObject {
	private static final B_Shape SHAPE = new B_Shape(15, new Color(96, 96, 255),A_Type.PLAYER);
//	private static final B_Shape SHAPE = new B_Shape(15, new Color(96, 96, 255));
	private TD_CounterMonsterHealth counter;
	
	public TD_Avatar(double x, double y) {
		super(x, y, 0, 0, SHAPE);
		//this.isMoving = false;

        counter = new TD_CounterMonsterHealth((int)x,(int)y);
		
	}

	public void move(double diffSeconds) {
		// move Avatar one step forward
		super.move(diffSeconds);

		// calculate all collisions with other Objects
		A_GameObjectList collisions = physicsSystem.getCollisions(this);
		for (int i = 0; i < collisions.size(); i++) {
			A_GameObject obj = collisions.get(i);

			// if Object is a tree, move back one step
			if (obj.type() == A_Type.TURRET) {
				this.moveBack();
			}
		}
	}

	public A_Type type() {
		return A_Type.PLAYER;
	}
	
	public  TD_CounterMonsterHealth counter() {
		return counter;
	}
}
