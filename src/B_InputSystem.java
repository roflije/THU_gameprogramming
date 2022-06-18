import java.awt.event.*;
import java.util.ArrayList;

class B_InputSystem implements A_InputSystem, KeyListener, MouseListener, MouseMotionListener {

	// UserInput variables
	private A_UserInput userInput = new A_UserInput();
	
     ArrayList<Integer>  keys = userInput.keys;

	public void mousePressed(MouseEvent evt) {
		// an input Event occurs
		userInput.isMouseEvent = true;
		userInput.mousePressedX = evt.getX();
		userInput.mousePressedY = evt.getY();
		userInput.mouseButton = evt.getButton();
		userInput.isMousePressed = true;
	}

	public void mouseReleased(MouseEvent evt) {
		userInput.isMousePressed = false;
	}

	public void mouseMoved(MouseEvent evt) {
		userInput.mouseMovedX = evt.getX();
		userInput.mouseMovedY = evt.getY();
	}

	public void mouseDragged(MouseEvent evt) {
		userInput.mouseMovedX = evt.getX();
		userInput.mouseMovedY = evt.getY();
	}

	public void keyPressed(KeyEvent evt) {
		userInput.isKeyEvent = true;
		userInput.keyPressed =   evt.getKeyCode();
		
		if(!keys.contains(evt.getKeyCode()))
		keys.add((Integer)evt.getKeyCode());

		
		
		System.out.println(evt.getKeyCode());

		System.out.println(keys.size());
		 
		 
	 
		
	}
	

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

 
	
	public void keyReleased(KeyEvent evt) {
	 	userInput.isKeyEvent = true;
	//	 userInput.keyReleased = evt.getKeyCode();
        
		System.out.println(keys.size());
		if(keys.contains(evt.getKeyCode()))
		keys.remove((Integer)evt.getKeyCode());
		System.out.println(keys.size());
		System.out.println("Released");
 
		 
		
		
	}

	public void keyTyped(KeyEvent evt) {
	 
		 
	}

	public A_UserInput getUserInput() {
		return userInput;
	}
	
	public ArrayList<Integer> getKeys() {
		return keys;
	}
}
