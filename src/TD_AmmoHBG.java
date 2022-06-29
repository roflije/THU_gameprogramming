import java.awt.Color;

class TD_AmmoHBG extends A_GameObject
{
  double life = A_Const.HBG_LIFE;
  
  public TD_AmmoHBG(double x, double y)
  {
    super(x,y,0,0,new B_Shape(15,Color.GREEN ));
  }
  
  public void move(double diffSeconds)
  {
    life -= diffSeconds;
    if(life<0)
    { this.isLiving=false;
      return;
    }
    
  }
  
  public A_Type type() { return A_Type.HBG_AMMO; }
}