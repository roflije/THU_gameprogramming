
// (c) Thorsten Hasbargen


import java.awt.Color;

class TD_HelpTextPrototype extends A_TextObject{
    public TD_HelpTextPrototype(int x, int y){
        super(x,y, new B_Shape(0,new Color(0,120,255,60)));
    }

    public String toString(){
        String display = "MOVE:WASD        SHOOT:Mouse left   Flood with enemies: 1      Building mode: 2";
        return display;
    }

}
