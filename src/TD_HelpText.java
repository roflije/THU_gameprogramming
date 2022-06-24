import java.awt.Color;

class TD_HelpText extends A_TextObject {
	public TD_HelpText(int x, int y) {
		super(x, y, new B_Shape(0, new Color(0, 120, 255, 60)));
	}

	public String toString() {
		String display = "MOVE:WASD        SHOOT:Mouse left        Building mode: TAB";
		return display;
	}

}
