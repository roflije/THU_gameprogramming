import java.awt.Color;

//
// actual Interface containing the Appearance of an Object:
// it has radius and color
//

class B_ShapePrototype implements A_Shape {
    private int radius;
    Color color;
    long interval;

    public B_ShapePrototype(int r, Color c) {
        radius = r;
        color = c;
    }

    public B_ShapePrototype(int r, Color c, long millis) {
        radius = r;
        color = c;
        interval = millis;

    }

    public final double radius() {
        return radius;
    }
}
