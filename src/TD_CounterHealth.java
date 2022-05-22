import java.awt.Color;

class TD_CounterHealth extends A_TextObject {
	private int number = 1;

	public TD_CounterHealth(int x, int y) {
		super(x, y, new B_Shape(0, new Color(255, 255, 0, 210)));
	}

	public String toString() {
		String display = "Health: ";
		display += number;
		return display;
	}

	public void setNumber(int n) {
		number = n;
	}
}
