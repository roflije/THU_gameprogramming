import java.awt.Color;

class TD_CounterHBG extends A_TextObject {
	private int number = 0;

	public TD_CounterHBG(int x, int y) {
		super(x, y, new B_Shape(0, new Color(255,0,0)));
	}

	public String toString() {
		String display = "HBG Ammo: ";
		display += number;
		return display;
	}

	public void setNumber(int n) {
		number = n;
	}

	public int getNumber(){
		return number;
	}
}