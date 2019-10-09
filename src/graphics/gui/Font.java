package graphics.gui;

import graphics.Texture;

/**
 * Interface f�r Fonts
 * 
 * @author Alex
 */
public interface Font {

	/**
	 * Gibt Textur aus
	 * @return Textur Textur des Fonts
	 */
	public abstract Texture getTexture();

	/**
	 * X Choordinaten in der Textur<br>
	 * (0-1) Angaben f�r textures[]
	 * 
	 * @param asciiCode Ascii-Code des Zeichens
	 * @return Position (0-1)
	 */
	public abstract float getCharX(char asciiCode);
	/**
	 * Y Choordinaten In der Textur<br>
	 * (0-1) Angaben f�r textures[]
	 * 
	 * @param asciiCode Ascii-Code des Zeichens
	 * @return Position (0-1)
	 */
	public abstract float getCharY(char asciiCode);
	/**
	 * Breite der Zeichens in der Textur <br>
	 * (0-1) Angaben f�r textures[]
	 * 
	 * @param asciiCode Ascii-Code des Zeichens
	 * @return Position (0-1)
	 */
	public abstract float getCharOffX(char asciiCode);
	/**
	 * H�he in der Textur <br>
	 * (0-1) Angaben f�r textures[]
	 * 
	 * @param asciiCode Ascii-Code des Zeichens
	 * @return Position (0-1)
	 */
	public abstract float getCharOffY(char asciiCode);

	/**
	 * @param asciiCode Ascii-Code des Zeiches
	 * @return Breite Breite in px
	 */
	public abstract float getCharWidth(char asciiCode);

	/**
	 * @param asciiCode Ascii-Code des Zeiches
	 * @return H�he H�he in px
	 */
	public abstract float getCharHeight(char asciiCode);
	
	public abstract void destroy();

}
