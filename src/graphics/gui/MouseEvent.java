package graphics.gui;

/**
 * Enth�lt Informationen �ber den Zustand der Maus.<br>
 * Wird von anderen Klassen genutzt, um Informationen �ber die Maus kompakt zu speichern und auszutauschen.
 * 
 * @author Ben
 */
public class MouseEvent {
	
	public final float mouseX;
	public final float mouseY;
	public final boolean isLeftMouseDown;
	public final boolean isRightMouseDown;
	public final boolean wasLeftClicked;
	public final boolean wasRightClicked;
	
	/**
	 * Erstellt eine neue Instanz mit den gegebenen Werten.
	 * 
	 * @param mouseX Maus-X-Position relativ zur oberen linken Ecke des Fensters
	 * @param mouseY Maus-Y-Position relativ zur oberen linken Ecke des Fensters
	 * @param isLeftMouseDown ob die linke Maustaste gedr�ckt ist
	 * @param isRightMouseDown ob die rechte Maustaste gedr�ckt ist
	 * @param wasLeftClicked ob mit der linken Maustaste k�rzlich geklickt wurde
	 * @param wasRightClicked ob mit der rechten Maustaste k�rzlich geklickt wurde
	 */
	public MouseEvent(float mouseX, float mouseY, boolean isLeftMouseDown, boolean isRightMouseDown, boolean wasLeftClicked, boolean wasRightClicked) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.isLeftMouseDown = isLeftMouseDown;
		this.isRightMouseDown = isRightMouseDown;
		this.wasLeftClicked = wasLeftClicked;
		this.wasRightClicked = wasRightClicked;
	}
	
}
