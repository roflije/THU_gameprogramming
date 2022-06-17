import java.util.HashMap;

final class A_UserInput {
	int mousePressedX, mousePressedY, mouseMovedX, mouseMovedY, mouseButton;
	 // char keyPressed,keyReleased;
      
	// For using non-character buttons
	 int keyPressed,keyReleased;
	 
	// Will be needed later for n-key rollover  
   private HashMap<Integer,Integer> keys = new HashMap<>();
   
     
	boolean isMouseEvent, isKeyEvent, isMousePressed;
	A_UserInput() {
		this.clear();
	}
	final void clear() {
		isMouseEvent = false;
		isKeyEvent = false;
	}
	

	public HashMap<Integer,Integer> getKeys()
	{
		 return keys;
		
	}
	 
}
