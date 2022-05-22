final class A_UserInput {
	int mousePressedX, mousePressedY, mouseMovedX, mouseMovedY, mouseButton;
	char keyPressed;
	boolean isMouseEvent, isKeyEvent, isMousePressed;
	A_UserInput() {
		this.clear();
	}
	final void clear() {
		isMouseEvent = false;
		isKeyEvent = false;
	}
}
