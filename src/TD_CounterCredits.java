import java.awt.Color;

class TD_CounterCredits extends A_TextObject {
  private int number = 0;

  public TD_CounterCredits(int x, int y) {
    super(x, y, new B_Shape(0, new Color(255, 0, 0)));
  }

  public String toString() {
    String display = "Credits: ";
    display += number;
    return display;
  }

  public void add(int n) {
    number += n;
  }

  public void subtract(int n) {
    number -= n;
  }

  public int get() {
    return number;
  }
}
