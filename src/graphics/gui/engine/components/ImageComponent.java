package graphics.gui.engine.components;

import graphics.core.Texture;
import graphics.core.Vao;
import graphics.gui.engine.GuiComponent;
import graphics.gui.renderer.GuiShader;

/**
 * Die Klasse f�r Bild-Komponenten unseres Gui-Systems.
 * 
 * @author Ben
 */
public class ImageComponent extends GuiComponent {
	
	private Vao vao;
	private Texture texture;
	
	/**
	 * Erstellt eine neue Bild-Komponente aus dem gegebenen Dateipfad.
	 * <br><br>
	 * <b>Achtung</b>: Bilder mit ungerader Breite oder H�he funktionieren gerade noch nicht richtig, es kann vorkommen, dass am Rand eine Reihe Pixel abgeschnitten oder doppelt dargestellt wird.<br>
	 * Das liegt vermutlich an Rundungsfehlern in der Grafikkarte, wie wir das am besten beheben k�nnen ist derzeit noch nicht klar.
	 * 
	 * @param path Dateipfad relativ zu <code>res/res</code>
	 */
	public ImageComponent(String path) {
		// ruft den GuiComponent-Konstruktor auf
		this(path,2);
	}
	public ImageComponent(String path,float scale) {
		// ruft den GuiComponent-Konstruktor auf
		super(0,0);
		
		// l�dt das Bild und passt eigene Gr��e an
		texture = new Texture(path);
		int width = (int)(scale*texture.getWidth());
		int height = (int)(scale*texture.getHeight());
		super.setSize(width,height);
		System.out.println("Width: "+width+", height: "+height);
		
		// erstellt Rechteck-Vao
		vao = new Vao(new float[]{0,0,0,height,width,0,0,height,width,height,width,0},new float[]{0,0,0,1,1,0,0,1,1,1,1,0});
	}
	
	/**
	 * Ersetzt das Bild durch ein anderes, beh�lt aber die Gr��e des alten Bildes bei
	 * 
	 * @param path Pfad relativ
	 */
	public void loadImage(String path) {
		texture.destroy();
		texture = new Texture(path);
	}
	
	/**
	 * �ndert die Gr��e der Komponente. Das Bild wird auf die neue Gr��e gestreckt.
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void setSize(int width, int height) {
		// l�scht das alte Vao und erstellt ein neues mit der neuen Gr��e
		vao.destroy();
		vao = new Vao(new float[]{0,0,0,height,width,0,0,height,width,height,width,0},new float[]{0,0,0,1,1,0,0,1,1,1,1,0});
		super.setSize(width,height);
	}
	
	/**
	 * Rendert das Bild
	 * @param shader zum Rendern genutzter Shader
	 */
	public void render(GuiShader shader) {
		if (super.getVisibility()==VISIBILITY_VISIBLE) {
			shader.loadTransformationMatrix(super.getTotalTransform());
			shader.loadTransparency(super.getTotalTransparency());
			texture.bind();
			vao.bind();
			vao.render();
		}
	}
	
	/**
	 * L�scht das Bild, um Speicher freizugeben
	 */
	public void destroy() {
		vao.destroy();
		texture.destroy();
	}
	
}
