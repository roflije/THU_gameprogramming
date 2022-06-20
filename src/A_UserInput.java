import java.util.ArrayList;

final class A_UserInput {
	int mousePressedX, mousePressedY, mouseMovedX, mouseMovedY, mouseButton;
	 // char keyPressed,keyReleased;
      
	// For using non-character buttons
	 int keyPressed,keyReleased;
	 
	// Will be needed later for n-key rollover  
     ArrayList<Integer> keys = new ArrayList<>();
   
     
	boolean isMouseEvent, isKeyPressEvent,isKeyReleaseEvent, isMousePressed;
	A_UserInput() {
		this.clear();
	}
	final void clear() {
		isMouseEvent = false;
		isKeyPressEvent = false;
		isKeyReleaseEvent = false;
	} 
	
	public ArrayList<Integer> getKeys() {
		return keys;
	}
}
