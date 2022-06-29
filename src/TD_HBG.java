import java.awt.Color;
import java.util.ArrayList;

class TD_HBG extends A_GameObject {
	private static final B_Shape SHAPE = new B_Shape(50, Color.green);
	private double ttl = 0;

	private double lifeTime = 1000;
	private TD_AlienAI target;
	private A_Type owner;
	public TD_HBG(A_Type owner, double x, double y, double xDest, double yDest) {
		super(x, y, Math.atan2(yDest - y, xDest - x), 150, SHAPE);
		this.owner = owner;
		this.isMoving = true;
	}

	public TD_HBG(A_Type owner, double x, double y, double a, double s, double time) {
		super(x, y, a, s, SHAPE);
		lifeTime = time;
		this.isMoving = true;
		this.owner = owner;
	}
	
	

	public TD_HBG(A_Type owner, double x, double y, TD_AlienAI target) {
		super(x, y, Math.atan2(target.y - y, target.x - x), 500, SHAPE);
		this.isMoving = true;
		this.target = target;
		this.owner = owner;
	}

	public void move(double diffSeconds) {
		if (target != null) {
			if (target.isLiving == false) {
				this.isLiving = false;
			}
			this.setDestination(target);
		}
		lifeTime -= diffSeconds;
		if (lifeTime <= 0) {
			this.isLiving = false;
			return;
		}

		A_GameObjectList collisions = physicsSystem.getCollisions(this);
		for (int i = 0; i < collisions.size(); i++) {
			A_GameObject obj = collisions.get(i);

			A_Type type = obj.type();

			// tree: shot is deleted
			if (owner == A_Type.PLAYER && (type == A_Type.TURRET || type == A_Type.SLOWER)) {
				this.isLiving = false;
			}
			// Zombie: inform Zombie it is hit
//			else if ((type == A_Type.ALIEN_SMALL || type == A_Type.ALIEN_MEDIUM || type == A_Type.ALIEN_BIG) && obj.isLiving) {
//				TD_AlienAI alien = (TD_AlienAI) obj;
//				alien.hasBeenShot();
//				this.isLiving = false;
//			}
			
			 findNearbyMonsters();
		}

		super.move(diffSeconds);
	}
	
	  private void findNearbyMonsters() {
	        for (TD_AlienAI alien : TD_World.alienObjects) {
	            if (isNearby(alien)) {
	            	disintegrate(alien);
	                alien.hasBeenBlasted();
	               
	            }
	        }
	    }
	  
	  private void disintegrate(TD_AlienAI alien)
	  {
		  
		  for(int i=0; i<50; i++)
		    {
		      double alfa = Math.random()*Math.PI*2;
		      double speed = 50+Math.random()*600;
		      double time  = 0.1+Math.random()*0.2;
		      TD_Shot alienGuts = new TD_Shot(alien.type(),alien.x,alien.y,alfa,speed,time);
		      TD_World.gameObjects.add(alienGuts);
		    }
	  }
	
	private boolean isNearby(TD_AlienAI alien) {
        return this.x - A_Const.HBG_RADIUS < alien.x && alien.x < this.x + A_Const.TURRET_RANGE && this.y - A_Const.TURRET_RANGE < alien.y && alien.y < this.y + A_Const.TURRET_RANGE;
    }


	public final A_Type type() {
		return A_Type.HBD_BULLET;
	}
	

	
}


