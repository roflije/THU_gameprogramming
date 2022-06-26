import java.awt.Color;

class TD_PauseText extends A_TextObject {
	public TD_PauseText(int x, int y) {
		super(x, y, new B_Shape(0, new Color(0, 120, 255, 60)));
	}

	public String toString() {
		String display = "PAUSED";
		return display;
	}

}
