package graphics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;

import main.StaticUtils;

/**
 * Klasse f�r OpengGL-Texturen. K�nnen zum Speichern und sp�teren Rendern von Bildern genutzt werden.
 * 
 * @author Ben, Texture von Alex
 */
public class Texture {
	
	public final int id;
	
	
	private int width, height;
	
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
	 * Erstellt neue Textur aus Datei
	 * 
	 * @param path Dateipfad relativ zu <code>res/res</code>
	 */
	public Texture(String path) {
		// ruft normalen Konstruktor auf
		this();
		// Placeholder Textur, f�r nicht geladene Texturen
		bufferData(2,2,new int[] {0xff000000,0xffff0000,0xff00ff00,0xff0000ff});
		 
		// Buffer f�r Daten der Textur(Breite, H�he, PixelDaten)
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		try {
			// Buffer f�r Textur werden erstllt
			ByteBuffer fileContents = StaticUtils.ioResourceToByteBuffer(path);
			ByteBuffer pixels = STBImage.stbi_load_from_memory(fileContents,widthBuffer,heightBuffer,comp,4);
			// Textur wird aus Buffern erstellt
			bufferData(pixels, widthBuffer.get(0), heightBuffer.get(0));
				
		} catch (IOException e) {
			System.err.println("Bild konnte nicht geladen werden, Path: "+path);
			e.printStackTrace();
		}	
		
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
	 * Setzt das Texture auf die gegebene Gr��e und den gegebenen Inhalt.
	 * 
	 * @param Pixel-Daten ByteBuffer-Magic
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void bufferData(ByteBuffer pixels, int width, int height) {
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
	 * Gibt die Breite des Textures zur�ck.
	 * 
	 * @return Breite in Pixeln
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gibt die H�he des Textures zur�ck.
	 * 
	 * @return H�he in Pixeln
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * L�scht das Texture, um Speicher freizugeben.
	 */
	public void destroy() {
		GL11.glDeleteTextures(id);
	}
	
}
