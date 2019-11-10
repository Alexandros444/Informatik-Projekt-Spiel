package graphics.gui.engine.components;

import graphics.gui.engine.MouseEvent;
import graphics.gui.engine.fonts.Font;

/**
 * Klasse f�r einfache Buttons
 * 
 * @author Ben
 */
public class ButtonComponent extends BoxComponent {
	
	private TextComponent text;
	
	private int backgroundColor, hoverColor;
	private boolean wasClicked;
	
	/**
	 * Erstellt einen neuen Button
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 * @param text Beschriftung
	 * @param font Schriftart
	 */
	public ButtonComponent(int width, int height, String text, Font font) {
		super(width,height,0x80000000,0xa0808080,4);
		this.text = new TextComponent(text,font);
		this.text.setPosition(POSITION_CENTER);
		this.text.setScale(2);
		super.addComponent(this.text);
		
		backgroundColor =0x80000000;
		hoverColor = 0xa0505050;
	}
	
	/**
	 * �ndert die Hintergrundfarbe, wenn der Mauszeiger den Button ber�hrt
	 * 
	 * @param event Event mit Informationen zur Maus
	 */
	protected void onMouseOn(MouseEvent event) {
		super.onMouseOn(event);
		super.setBackgroundColor(hoverColor);
	}
	
	/**
	 * �ndert die Hintergrundfarbe wieder zur�ck, wenn der Mauszeiger den Button nicht mehr ber�hrt
	 * 
	 * @param event Event mit Informationen zur Maus
	 */
	protected void onMouseOff(MouseEvent event) {
		super.onMouseOff(event);
		super.setBackgroundColor(backgroundColor);
	}
	
	/**
	 * Gibt zur�ck, ob der Button in diesem Frame angeklickt worden ist
	 * 
	 * @return ob der Button angeklickt wurde
	 */
	public boolean wasClicked() {
		return wasClicked;
	}
	
	/**
	 * Setzt die Variable <code>wasClicked</code> auf <code>false</code>, wann immer ein neues <code>MouseEvent</code> verarbeitet wird<br>
	 * So ist diese Variable nur dann <code>true</code>, wenn das Element im selben Frame auch geklickt wurde.
	 * <br><br>
	 * Anschlie�end wird ganz normal das <code>MouseEvent</code> verarbeitet.
	 */
	public void receiveMouseEvent(boolean canTouchMouse, MouseEvent event) {
		wasClicked = false;
		super.receiveMouseEvent(canTouchMouse,event);
	}
	
	/**
	 * Setzt die Variable <code>wasClicked</code> auf <code>true</code>, wenn auf das Element geklickt wurde.<br>
	 * {@link #wasClicked()} gibt dann solange <code>true</code> zur�ck, bis das n�chste <code>MouseEvent</code> verarbeitet wurde.
	 */
	protected void onLeftClick(MouseEvent event) {
		super.onLeftClick(event);
		wasClicked = true;
	}
	
	
	/**
	 * Setzt die Hintergrundfarbe
	 * 
	 * @param color Hintergrundfarbe im Format 0xaabbggrr (Hexadezimal)
	 */
	public void setBackgroundColor(int color) {
		backgroundColor = color;
		super.setBackgroundColor(color);
	}
	
	/**
	 * Setzt die Maus-�berflug-Farbe
	 * 
	 * @param color Hoverfarbe im Format 0xaabbggrr (Hexadezimal)
	 */
	public void setHoverColor(int color) {
		hoverColor = color;
	}
	
}
