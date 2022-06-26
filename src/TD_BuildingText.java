import java.awt.Color;

class TD_BuildingText extends A_TextObject {
    public TD_BuildingText(int x, int y) {
        super(x, y, new B_Shape(0, new Color(0, 120, 255, 60)));
    }

    public String toString() {
        String display = "BUILDING";
        return display;
    }

}
