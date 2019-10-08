package graphics.gui;

import graphics.Texture;

/**
 * Interface f�r Fonts
 * 
 * @author Alexander
 */
public interface Font {

	// L�dt Textur
	public abstract void loadTexture(String path);

	// gibt Textur aus
	public abstract Texture getTexture();

	/**
	 * X und Y choordinaten In der Textur, und Breiten in der Textur <br>
	 * (0-1) Angaben f�r textures[]
	 * 
	 * @param asciiCode des Zeichens
	 * @return Position (0-1)
	 */
	public abstract float getCharX(int asciiCode);

	public abstract float getCharY(int asciiCode);

	public abstract float getCharOffX(int asciiCode);

	public abstract float getCharOffY(int asciiCode);

	/**
	 * @param asciiCode des Zeiches
	 * @return Ma�e in px
	 */
	public abstract float getCharWidth(int asciiCode);

	public abstract float getCharHeight(int asciiCode);

	/**
	 * Gibt die Distanz in px zwischen den Zeichen aus
	 * 
	 * @param asciiCode
	 * @return Distanz in px
	 */
	public abstract float getPadding(int asciiCode);
}
