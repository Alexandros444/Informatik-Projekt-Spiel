package graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Klasse f�r OpengGL-Texturen. K�nnen zum Speichern und sp�teren Rendern von Bildern genutzt werden.
 * 
 * @author Ben
 */
public class Texture {
	
	public final int id;
	
	private int width;
	private int height;
	
	/**
	 * Erstellt ein neues Texture
	 */
	public Texture() {
		// generiert ein neues Texture
		id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
		// setzt die Interpolationsmethode bei Gr��en�nderungen auf Nearest-Neighbor, d.h. verpixelt
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_NEAREST);
		// initialisiert Speicher des Textures
		bufferData(0,0,null);
	}
	
	/**
	 * �ndert die Gr��e des Textures, falls es noch nicht die richtige Gr��e hat.<br>
	 * Der Inhalt des Textures wird in dem Fall zur�ckgesetzt.
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void resize(int width, int height) {
		if (this.width!=width||this.height!=height) {
			bufferData(width,height,null);
		}
	}
	
	/**
	 * Setzt das Texture auf die gegebene Gr��e und den gegebenen Inhalt.
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 * @param data Pixel-Daten im RGBA8-Format - jeder Pixel wird durch einen Integer angegeben, wobei das insignifikanteste Byte den Farbwert f�r Rot angibt, das zweite f�r Gr�n usw.
	 */
	public void bufferData(int width, int height, int[] pixels) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D,0,GL11.GL_RGBA,width,height,0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE,pixels);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Bindet das Texture an den OpenGL-Kontext, damit es zum Rendern benutzt werden kann.
	 */
	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
	}
	
	/**
	 * L�scht das Texture, um Speicher freizugeben.
	 */
	public void destroy() {
		GL11.glDeleteTextures(id);
	}
	
}
