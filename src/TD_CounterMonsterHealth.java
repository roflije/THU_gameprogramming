import java.awt.Color;

 public class TD_CounterMonsterHealth  extends A_TextObject {
	private int hp = 10;
	

	public TD_CounterMonsterHealth(int x, int y) {
		super(x-13, y-20, new B_Shape(0, new Color(255,0,0)));
 
	}

	public String toString() {
		String display = "";
		display += hp;
		return display;
	}

	public void setHP(int n) {
		hp = n;
	}
	
 
	
	public int getX() {
		return x;
	}
	public int getY() {
		return x;
	}
}
