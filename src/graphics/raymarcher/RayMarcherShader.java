package graphics.raymarcher;

import graphics.core.Shader;
import util.math.Matrix3f;
import util.math.Vector3f;

/**
 * Der Shader zum Rendern in 3D, mithilfe von RayMarching.<br>
 * Der eigentliche Rendering-Prozess findet im Fragmentshader statt, wo aus den Koordinaten jedes Pixels jeweils die Farbe bestimmt wird - diese Klasse enth�lt blo� Code, um den Shader zu initialisieren und Werte in ihn zu laden.
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
	private int snakeRadiusUniformID;
	private int foodPositionUniformID;
	private int foodRadiusUniformID;
	private int foodRotationUniformID;
	private int gridWidthUniformID;
	private int secondSnakePositionsUniformID;
	private int secondSnakeLengthUniformID;
	private int secondSnakeRadiusUniformID;
	
	/**
	 * L�dt den Shader.
	 * <br><br>
	 * Er muss anschlie�end noch mit <code>.start()</code> an den OpenGL-Kontext gebunden werden, damit er benutzt werden kann.
	 */
	public RayMarcherShader(boolean useCaveEffect, boolean useAcidEffect) {
		super(VERTEX_FILE,FRAGMENT_FILE,(useCaveEffect?"#define EFFECT_CAVE\n":"")+(useAcidEffect?"#define EFFECT_ACID\n":""));
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
		snakeRadiusUniformID = super.getUniformLocation("snakeSphereRadius");
		foodPositionUniformID = super.getUniformLocation("foodPosition");
		foodRadiusUniformID = super.getUniformLocation("foodRadius");
		foodRotationUniformID = super.getUniformLocation("foodRotation");
		gridWidthUniformID = super.getUniformLocation("gridWidth");
		secondSnakePositionsUniformID = super.getUniformLocation("secondSnakePositions");
		secondSnakeLengthUniformID = super.getUniformLocation("secondSnakeLength");
		secondSnakeRadiusUniformID = super.getUniformLocation("secondSnakeSphereRadius");
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
	 * L�dt die Dicke der Schlange.
	 * 
	 * @param radius Radius der Kugeln der Schlange
	 */
	public void loadSnakeSphereRadius(float radius) {
		super.loadFloat(snakeRadiusUniformID,radius);
	}
	
	/**
	 * L�dt die Position des Essens.
	 * 
	 * @param position Ortsvektor
	 */
	public void loadFoodPosition(Vector3f position) {
		super.loadVector3f(foodPositionUniformID,position);
	}
	
	/**
	 * L�dt die Gr��e des Essens.
	 * 
	 * @param radius Radius
	 */
	public void loadFoodRadius(float radius) {
		super.loadFloat(foodRadiusUniformID, radius);
	}
	
	/**
	 * L�dt die Matrix zum Drehen des Essens.
	 * 
	 * @param matrix Rotationsmatrix
	 */
	public void loadFoodRotation(Matrix3f matrix) {
		super.loadMatrix3f(foodRotationUniformID, matrix);
	}
	
	/**
	 * L�dt die Dicke des Gitters
	 * 
	 * @param gridWidth Dicke der Gitterst�be
	 */
	public void loadGridWidth(float gridWidth) {
		super.loadFloat(gridWidthUniformID,gridWidth);
	}
	/**
	 * L�dt die Position der zweiten Schlange.
	 * 
	 * @param positions die Ortsvektoren der einzelnen Teile der Schlange, so sortiert, dass der Kopf an erster Stelle steht
	 */
	public void loadSecondSnake(Vector3f[] positions) {
		super.loadVector3fArray(secondSnakePositionsUniformID,positions);
		super.loadInt(secondSnakeLengthUniformID,positions.length);
	}
	
	/**
	 * L�dt die Dicke der zweiten Schlange.
	 * 
	 * @param radius Radius der Kugeln der Schlange
	 */
	public void loadSecondSnakeSphereRadius(float radius) {
		super.loadFloat(secondSnakeRadiusUniformID,radius);
	}
}

