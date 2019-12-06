package graphics.gui.engine.components;

import graphics.gui.engine.GuiComponent;
import graphics.gui.engine.MouseEvent;

public class ButtonComponent extends BoxComponent {
	
	private boolean wasClicked, wasRightClicked;
	
	private BoxComponent overlay;
	
	/**
	 * Erstellt einen neuen Button
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 * @param text Beschriftung
	 * @param font Schriftart
	 */
	public ButtonComponent(int width, int height, int backgroundColor, int hoverColor, int borderColor, int borderWidth) {
		super(width,height,backgroundColor,borderColor,borderWidth);
		overlay = new BoxComponent(0,0,hoverColor,0,0);
		overlay.setTransparency(0);
		overlay.setPosition(POSITION_FULL);
		super.addComponent(overlay);
	}
	
	/**
	 * F�gt eine Komponente als Kindelemente ein
	 * 
	 * @param component die Komponente
	 */
	public void addComponent(GuiComponent component) {
		super.insertBefore(component,overlay);
	}
	
	/**
	 * Macht das Overlay sichtbar, wenn der Mauszeiger den Button ber�hrt
	 * 
	 * @param event Event mit Informationen zur Maus
	 */
	protected void onMouseOn(MouseEvent event) {
		super.onMouseOn(event);
		overlay.setTransparency(1);
	}
	
	/**
	 * Versteckt das Overlay wieder, wenn der Mauszeiger den Button nicht mehr ber�hrt
	 * 
	 * @param event Event mit Informationen zur Maus
	 */
	protected void onMouseOff(MouseEvent event) {
		super.onMouseOff(event);
		overlay.setTransparency(0);
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
	 * Gibt zur�ck, ob der Button in diesem Frame angeklickt worden ist
	 * 
	 * @return ob der Button angeklickt wurde
	 */
	public boolean wasRightClicked() {
		return wasRightClicked;
	}
	
	/**
	 * Setzt die Variable <code>wasClicked</code> auf <code>false</code>, wann immer ein neues <code>MouseEvent</code> verarbeitet wird<br>
	 * So ist diese Variable nur dann <code>true</code>, wenn das Element im selben Frame auch geklickt wurde.
	 * <br><br>
	 * Anschlie�end wird ganz normal das <code>MouseEvent</code> verarbeitet.
	 */
	public void receiveMouseEvent(boolean canTouchMouse, MouseEvent event) {
		wasClicked = false;
		wasRightClicked = false;
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
	 * Setzt die Variable <code>wasClicked</code> auf <code>true</code>, wenn auf das Element geklickt wurde.<br>
	 * {@link #wasClicked()} gibt dann solange <code>true</code> zur�ck, bis das n�chste <code>MouseEvent</code> verarbeitet wurde.
	 */
	protected void onRightClick(MouseEvent event) {
		super.onRightClick(event);
		wasRightClicked = true;
	}
}
