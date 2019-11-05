package graphics.gui.engine;

import java.util.ArrayList;
import java.util.List;

import graphics.core.Display;

/**
 * Klasse zur Verarbeitung der Inputs auf ein Fenster.
 * 
 * @author Ben
 */
public class InputHandler {
	
	private Display display;
	
	private float mouseX;
	private float mouseY;
	private boolean isLeftMouseDown;
	private boolean isRightMouseDown;
	private boolean wasLeftMouseClicked;
	private boolean wasRightMouseClicked;
	
	private List<KeyInput> keyInputs;
	
	/**
	 * Erstellt einen neuen InputHandler.
	 * 
	 * @param display Fenster, dessen Inputs verarbeitet werden sollen
	 */
	public InputHandler(Display display) {
		this.display = display;
		keyInputs = new ArrayList<KeyInput>();
	}
	
	/**
	 * Updated alle Inputs
	 */
	public void update() {
		boolean wasLeftMouseDown = isLeftMouseDown;
		boolean wasRightMouseDown = isRightMouseDown;
		isLeftMouseDown = display.isLeftMouseDown();
		isRightMouseDown = display.isRightMouseDown();
		wasLeftMouseClicked = isLeftMouseDown&&!wasLeftMouseDown;
		wasRightMouseClicked = isRightMouseDown&&!wasRightMouseDown;
		mouseX = display.getMouseX();
		mouseY = display.getMouseY();
		for(KeyInput keyInput:keyInputs) {
			keyInput.update(display.isKeyPressed(keyInput.getKey()));
		}
	}
	
	public KeyInput getKeyInput(int key) {
		KeyInput keyInput = new KeyInput(key);
		keyInputs.add(keyInput);
		return keyInput;
	}
	
	/**
	 * Gibt Informationen �ber den derzeitigen Zustand der Maus in Form eines MouseEvents zur�ck
	 * 
	 * @return MouseEvent mit Informationen zur Maus
	 */
	public MouseEvent getCurrentMouseEvent() {
		return new MouseEvent(mouseX,mouseY,isLeftMouseDown,isRightMouseDown,wasLeftMouseClicked,wasRightMouseClicked);
	}
	
}
