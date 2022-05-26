
// (c) Thorsten Hasbargen


import java.awt.Color;

class TD_Counter extends A_TextObject
{
  private int number = 0;
	
  public TD_Counter(int x, int y)
  { super(x,y, new B_Shape(0,new Color(255,0,0)));
  }
  
  public String toString()
  { String display = "Enemies: ";
    display += number;
    return display;
  }
  
  public void increment(){ number++; }
}
