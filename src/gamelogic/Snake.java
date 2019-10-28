package gamelogic;

import org.lwjgl.glfw.GLFW;

import graphics.core.Display;
import util.math.Matrix3f;
import util.math.Vector3f;

/**
 * Die Klasse f�r die Schlange.<br>
 * 
 *  @author Jakopo
 */
public class Snake {

	private static final int MAX_LENGTH = 64;
	
	public boolean isAlive;
	public Vector3f[] snakePositions;
	
	private long lastFrame;
	
	public Vector3f cameraPosition;
	public Matrix3f viewDirection;

	private float rotationSpeed = 0.75f;
	private float movementSpeed = 0.0045f;

	public float sphereRadius = 0.05f;
	
	/**
	 * Erstellt eine neue Schlange
	 */
	public Snake(){
		
	    snakePositions = new Vector3f[5];
	    viewDirection = new Matrix3f();
	    cameraPosition = new Vector3f(0,0,0.5f); 
	    
	    //Startposition der Kugeln des SchlangenSchwanzes
	     for (int l = 0; l < snakePositions.length; l++) {
			 snakePositions[l] = new Vector3f(); 
		}
	     
	     isAlive = true;
	     
	     lastFrame = System.nanoTime();
	}

	/**
	 * Updated und bewegt die Schlange
	 * 
	 * @param display Das Display, von dem aus Tastendr�cke eingelesen werden sollen
	 */
	
	public void update(Display display) {
		// Vergangene Zeit wird berechnet
		float deltaTime = System.nanoTime()-lastFrame;
		lastFrame = System.nanoTime();
		
		// �berpr�fen ob die Schlange noch lebt
		if(!isAlive)return;
		
		// STEUERUNG
		// dreht die Sichtmatrix je nach Tasteninput und l�dt sie in den Shader
	
		// dreht Sichtmatrix nach oben					
		if (display.isKeyPressed(GLFW.GLFW_KEY_W) || display.isKeyPressed(GLFW.GLFW_KEY_UP) ) {
			viewDirection.rotate(-rotationSpeed* (deltaTime*(1e-7f)), 0, 0);
		}
		// dreht Sichtmatrix nach unten
		if (display.isKeyPressed(GLFW.GLFW_KEY_S) || display.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
			viewDirection.rotate(rotationSpeed* (deltaTime*(1e-7f)), 0, 0);
		}
		// dreht Sichtmatrix nach links
		if (display.isKeyPressed(GLFW.GLFW_KEY_A) || display.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
			viewDirection.rotate(0, -rotationSpeed* (deltaTime*(1e-7f)), 0);
		}
		// Dreht Sichtmatrix nach rechts 
		if (display.isKeyPressed(GLFW.GLFW_KEY_D) || display.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
			viewDirection.rotate(0, rotationSpeed* (deltaTime*(1e-7f)), 0);
		}
		// Rotiert Sichtmatrix nach links
		if (display.isKeyPressed(GLFW.GLFW_KEY_Q)) {
			viewDirection.rotate(0, 0, rotationSpeed* (deltaTime*(1e-7f)));
		}
		// Rotiert Sichtmatrix nach rechts
		if (display.isKeyPressed(GLFW.GLFW_KEY_E)) {
			viewDirection.rotate(0, 0, -rotationSpeed* (deltaTime*(1e-7f)));
		}
		//wenn Leertaste gedr�ckt dann stop
		if (!display.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
			// Setzt den BewegungsVektor zur�ck
			Vector3f movement = new Vector3f(0,0,movementSpeed);
			// Bestimmt Geschwindigkeit pro Frame
			movement.scale(deltaTime*(1e-7f));
			// dreht den BewegungsVektor durch die SichtMatrix
			movement.apply(viewDirection);
			// addiert den BewegungsVektor zum Kamera-Positions-Vektor 
			cameraPosition.add(movement);
		}

		// bewegt die Schlange
		updateSnakePositions(); 
	}
	
	/**
	 * F�gt eine Kugel zur Schlange hinzu, es sei denn die Schlange hat bereits ihre Maximall�nge erreicht
	 */
	public void addSphere() {
		if(snakePositions.length<MAX_LENGTH) {
			Vector3f[] temp =  new Vector3f [snakePositions.length+1];
			for(int i = 0;i<snakePositions.length;i++) {
				temp[i] = snakePositions[i];
			}
			temp[snakePositions.length] = snakePositions[snakePositions.length-1].copy();
			snakePositions = temp;
		}
	}

	/**
	 * Updated die Positionen des K�rpers der Schlange
	 */
	private void updateSnakePositions() {
		 snakePositions[0] = cameraPosition.copy();
		for (int i=1;i< snakePositions.length;i++){
		    Vector3f delta =  snakePositions[i-1].copy();
			Vector3f temp =  snakePositions[i].copy();
			temp.scale(-1f);
		    delta.add(temp);
		    if (delta.getLength()>2*sphereRadius){
			    delta.setLength(delta.getLength()-2*sphereRadius);
			    snakePositions[i].add(delta);
		    }
		}
	}
	
}