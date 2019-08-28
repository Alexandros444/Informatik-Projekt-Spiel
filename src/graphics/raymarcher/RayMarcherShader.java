package graphics.raymarcher;

import graphics.Shader;

/**
 * Erster Entwurf des Shaders zum Rendern in 3D. <br>
 * Rendert bisher nur ein 3D-Gitter, soll aber sp�ter die Schlange und Futterk�rner usw. rendern.
 * 
 * @author Ben
 */

public class RayMarcherShader extends Shader{
	
	private static final String VERTEX_FILE = "graphics/raymarcher/vertexShader.txt";
	private static final String FRAGMENT_FILE = "graphics/raymarcher/fragmentShader.txt";
	
	/**
	 * L�dt den Shader.
	 * <br><br>
	 * Er muss anschlie�end noch mit <code>.start()</code> an den OpenGL-Kontext gebunden werden, damit er benutzt werden kann.
	 */
	public RayMarcherShader() {
		super(VERTEX_FILE,FRAGMENT_FILE);
	}
	
	/**
	 * Legt die Inputs f�r den Vertex-Shader fest.
	 */
	protected void bindAttributes() {
		super.bindAttribute(0,"position");
	}
	
}
