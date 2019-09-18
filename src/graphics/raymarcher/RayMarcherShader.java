package graphics.raymarcher;

import graphics.Matrix3f;
import graphics.Shader;
import graphics.Vector3f;

/**
 * Erster Entwurf des Shaders zum Rendern in 3D. <br>
 * Rendert bisher nur ein 3D-Gitter, soll aber sp�ter die Schlange und Futterk�rner usw. rendern.
 * 
 * @author Ben
 */

public class RayMarcherShader extends Shader{
	
	private static final String VERTEX_FILE = "graphics/raymarcher/vertexShader.txt";
	private static final String FRAGMENT_FILE = "graphics/raymarcher/fragmentShader.txt";
	
	private int viewMatrixUniformID;
	private int positionUniformID;
	private int screenRatioUniformID;
	private int snakePositionsUniformID;
	private int snakeLengthUniformID;
	private int foodPositionUniformID;
	
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
	
	/**
	 * L�dt die IDs der verwendeten Uniforms.
	 */
	protected void getUniformLocations() {
		viewMatrixUniformID = super.getUniformLocation("viewDirection");
		positionUniformID = super.getUniformLocation("cameraPosition");
		screenRatioUniformID = super.getUniformLocation("screenRatio");
		snakePositionsUniformID = super.getUniformLocation("snakePositions");
		snakeLengthUniformID = super.getUniformLocation("snakeLength");
		foodPositionUniformID = super.getUniformLocation("foodPosition");
	}
	
	/**
	 * Setzt des Seitenverh�ltnis des zu rendernden Bilds
	 * 
	 * @param ratio Seitenverh�ltnis in Breite/H�he
	 */
	public void loadScreenRatio(float ratio) {
		super.loadFloat(screenRatioUniformID,ratio);
	}
	
	/**
	 * L�dt die Matrix als Sichtrichtung.
	 * 
	 * @param matrix die Sichtmatrix.
	 */
	public void loadViewMatrix(Matrix3f matrix) {
		super.loadMatrix3f(viewMatrixUniformID,matrix);
	}
	
	/**
	 * Setzt die Position der Kamera auf den �bergebenen Ortsvektor.
	 * 
	 * @param position Ortsvektor
	 */
	public void loadPosition(Vector3f position) {
		super.loadVector3f(positionUniformID,position);
	}
	
	/**
	 * L�dt die Position der Schlange.
	 * 
	 * @param positions die Ortsvektoren der einzelnen Teile der Schlange, so sortiert, dass der Kopf an erster Stelle steht
	 */
	public void loadSnake(Vector3f[] positions) {
		super.loadVector3fArray(snakePositionsUniformID,positions);
		super.loadInt(snakeLengthUniformID,positions.length);
	}
	
	/**
	 * L�dt die Position des Essens.
	 * 
	 * @param position Ortsvektor
	 */
	public void loadFoodPosition(Vector3f position) {
		super.loadVector3f(foodPositionUniformID,position);
	}
	
}
