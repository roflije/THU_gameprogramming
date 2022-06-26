import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

class TD_Slower extends A_GameObject {
    protected static final Color COLOR = new Color(0, 0, 255);
    private double timeSinceLastTick = 0.0;
    private ArrayList<TD_AlienAI> slowedAliens;
    private double ttl = 0;

    public TD_Slower(double x, double y, int r) {
        super(x, y, 0, 0, new B_Shape(r, COLOR, A_Type.SLOWER));
        this.isMoving = false;
        this.slowedAliens = new ArrayList<TD_AlienAI>();
    }

    @Override
    public void move(double diffSeconds) {
        timeSinceLastTick += diffSeconds;
        if (timeSinceLastTick > 1) {
            findNearbyMonsters();
            for (Iterator<TD_AlienAI> iter = slowedAliens.iterator(); iter.hasNext();) {
                TD_AlienAI alien = iter.next();
                if (!isNearby(alien)) {
                    alien.fast();
                    iter.remove();
                }
            }
        }
    }

    private void findNearbyMonsters() {
        for (TD_AlienAI alien : TD_World.alienObjects) {
            if (isNearby(alien) && !slowedAliens.contains(alien)) {
                slowedAliens.add(alien);
                alien.slow();
            }
        }
    }

    private boolean isNearby(TD_AlienAI alien) {
        return this.x - A_Const.TURRET_RANGE < alien.x && alien.x < this.x + A_Const.TURRET_RANGE && this.y - A_Const.TURRET_RANGE < alien.y && alien.y < this.y + A_Const.TURRET_RANGE;
    }

    public A_Type type() {
        return A_Type.SLOWER;
    }

    public void addTTL(double t){
        ttl += t;
    }

    public double getTTL(){
        return ttl;
    }
}
