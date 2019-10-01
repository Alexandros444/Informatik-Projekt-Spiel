package graphics.gui;

/**
 * Die abstrakte Klasse f�r all unsere Gui-Komponenten.<br>
 * Die Klasse <code>GuiComponent</code> kann <b>nicht</b> selber instanziiert werden, da es sich um eine abstrakte Klasse handelt - stattdessen k�nnen andere Klassen diese Klasse via Vererbung erweitern und dann instanziiert werden.<br>
 * Zum Beispiel kann mit <code>GuiComponent c = new TextComponent("Abc")</code> ein neues Text-Element erstellt werden.
 * <br><br>
 * Der Vorteil der Vererbung ist eben, dass durch die hier festgelegten Methoden auf alle Gui-Komponenten zugegriffen werden kann, egal ob es sich um eine Zeile Text oder ein Bild handelt - so wird eben ein System geschaffen, durch das mit verschiedenen Komponenten einheitlich umgegangen werden kann.
 * <br><br>
 * Das hei�t allerdings auch nicht, dass die hier festgelegten Methoden f�r alle Klassen gelten m�ssen - Klassen, die diese Klassen erweitern, k�nnen die Methoden immer noch �berschreiben, um das Verhalten beim Aufruf der Methode zu ver�ndern.
 * Durch die Deklaration der Methoden in dieser Klasse hier ist lediglich festgelegt, dass diese Methoden f�r alle diese Klasse erweiternden Klassen existieren m�ssen, nicht welchen Effekt sie jeweils haben.
 * 
 * @author Ben
 */
public abstract class GuiComponent {
	
	private int width;
	private int height;
	
	/**
	 * Erstellt eine neuen Gui-Komponente.
	 * 
	 * @param width Anf�ngliche Breite in Pixeln
	 * @param height Anf�ngliche H�he in Pixeln
	 */
	public GuiComponent(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Rendert die Komponente. Muss von allen abgeleiteten Klassen implementiert werden.
	 */
	public abstract void render();
	
	/**
	 * L�scht alle von der Komponente abh�ngigen Objekte, um Speicher wieder freizugeben. Muss von allen abgeleiteten Klassen implementiert werden.
	 */
	public abstract void destroy();
	
	/**
	 * �ndert die Gr��e der Komponente.
	 * 
	 * @param width neue Breite in Pixeln
	 * @param height neue H�he in Pixeln
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gibt die Breite des Elements zur�ck
	 * 
	 * @return Breite in Pixeln
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gibt die H�he des Elements zur�ck
	 * 
	 * @return H�he in Pixeln
	 */
	public int getHeight() {
		return height;
	}
	
}
