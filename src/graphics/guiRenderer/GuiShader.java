package graphics.guiRenderer;

import graphics.Shader;
import graphics.Vector2f;

public class GuiShader extends Shader {
	
	private static final String VERTEX_FILE = "graphics/guiRenderer/vertexShader.txt";
	private static final String FRAGMENT_FILE = "graphics/guiRenderer/fragmentShader.txt";
	
	private int screenSizeUniformID;
	
	/**
	 * L�dt den Shader.
	 * <br><br>
	 * Er muss anschlie�end noch mit <code>.start()</code> an den OpenGL-Kontext gebunden werden, damit er benutzt werden kann.
	 */
	public GuiShader() {
		super(VERTEX_FILE,FRAGMENT_FILE);
	}
	
	/**
	 * Legt die Inputs f�r den Vertex-Shader fest.
	 */
	protected void bindAttributes() {
		super.bindAttribute(0,"position");
		super.bindAttribute(1,"textureCoords");
	}
	
	/**
	 * L�dt die IDs der verwendeten Uniforms.
	 */
	protected void getUniformLocations() {
		screenSizeUniformID = super.getUniformLocation("screenSize");
	}
	
	/**
	 * L�dt die Gr��e des Gui-Koordinatensystems in den Shader.
	 * <br><br>
	 * Die Gr��e wird dort genutzt, um die im Gui-System genutzten Pixel-Koordinaten in OpenGL-Koordinaten (von -1 bis 1) umzurechnen.
	 * 
	 * @param width die Breite des Pixel-Koordinatensystems
	 * @param height die H�he des Pixel-Koordinatensystems
	 */
	public void loadScreenSize(int width, int height) {
		super.loadVector2f(screenSizeUniformID,new Vector2f(width,height));
	}
	
}
