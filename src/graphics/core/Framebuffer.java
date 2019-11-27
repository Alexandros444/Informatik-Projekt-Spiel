package graphics.core;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

/**
 * Klasse f�r OpenGL-Framebuffer-Objekte.<br>
 * Framebuffer werden vor allem dazu genutzt, gerenderte Bilder zwischenzuspeichern, um sie dann in weiteren Rendering-Prozessen weiterzuverwenden.<br>
 * Framebuffer speichern selbst keine Bilder, dienen daf�r aber als eine Art speicher f�r Texturen; diese k�nnen an sogenannte "Attachment Points" des Framebuffers gebunden werden.
 * Solange der Framebuffer gebunden ist, wird beim Rendern statt aufs Display in die Texturen gerendert, sodass die gerenderten Sachen dann wieder als Textur in einen Shader geladen werden k�nnen.
 * @author Ben
 */
public class Framebuffer {
	
	private int id;
	
	/**
	 * Erstellt einen neuen Framebuffer und bindet ihn an den OpenGL-Kontext.
	 */
	public Framebuffer() {
		id = GL30.glGenFramebuffers();
		bind();
	}
	
	/**
	 * Bindet den Framebuffer an den OpenGL-Kontext, sodass sich weitere Funktionsaufrufe auf ihn beziehen, bis ein anderer Buffer gebunden wird.
	 */
	public void bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER,id);
	}
	
	/**
	 * Bindet den Framebuffer an den OpenGL-Kontext, sodass sich weitere Funktionsaufrufe auf ihn beziehen, bis ein anderer Buffer gebunden wird.
	 * @param target <code>GL30.GL_FRAMEBUFFER</code>, <code>GL30.GL_READ_FRAMEBUFFER</code> oder <code>GL30.GL_DRAW_FRAMEBUFFER</code>
	 */
	public void bind(int target) {
		GL30.glBindFramebuffer(target,id);
	}
	
	/**
	 * F�gt eine Textur zum Framebuffer hinzu, sodass in diese gerendert werden kann.
	 * @param texture die Textur
	 * @param attachment Anhangspunkt des Framebuffers, an den die Textur angeh�ngt werden soll, wie z.B. <code>GL30.GL_COLOR_ATTACHMENT0</code>
	 */
	public void attachTexture(Texture texture, int attachment) {
		bind();
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER,attachment,texture.id,0);
	}
	
	/**
	 * L�scht den Framebuffer, um genutzte Ressourcen freizugeben.<br>
	 * Die zugeh�rigen Texturen werden nicht gel�scht und k�nnen weiter verwendet werden.
	 */
	public void destroy() {
		GL30.glDeleteFramebuffers(id);
	}
	
}
