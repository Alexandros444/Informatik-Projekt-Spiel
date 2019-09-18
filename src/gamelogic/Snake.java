package gamelogic;

import org.lwjgl.glfw.GLFW;
/**
 * Die Klasse f�r die Schlange.<br>
 * 
 *  @author Jakopo
 */

import graphics.Display;
import graphics.Matrix3f;
import graphics.Vector3f;
import gamelogic.Food;

public class Snake {
	
	public Vector3f cameraPosition, movement;
	public Matrix3f viewDirection;
	public boolean isAlive = true;
	public Vector3f[]  snakePositions;
	public Food food;
	public boolean goodPosition = false;
	
	private float rotationSpeed = 2f;
	private float movementSpeed = 0.0025f;
	private float sphereRadius = 0.05f;
	private float foodSize = 0.05f;

	
	/**
	 * Erstellt eine neue Schlange
	 * 
	 */
	public Snake(){
	    cameraPosition = new Vector3f(0,0,0.5f);  
	    viewDirection = new Matrix3f();
	    movement = new Vector3f();
	     snakePositions = new Vector3f[2];   
	     food = new Food(0.02f);
	    
	    //Startposition der Kugeln des SchlangenSchwanzes
	     for (int l = 0; l < 2; l++) {
			 snakePositions[l] = new Vector3f(); 
		}
	}

	/**
	 * Updated und bewegt die Schlange
	 * 
	 * @param display Das Display, von dem aus Tastendr�cke eingelesen werden sollen
	 */
	
	public void update(Display display) {
		if(isAlive==true) {
			// dreht die Sichtmatrix je nach Tasteninput und l�dt sie in den Shader
		
			// dreht Sichtmatrix nach oben					
			if (display.isKeyPressed(GLFW.GLFW_KEY_W) || display.isKeyPressed(GLFW.GLFW_KEY_UP) ) {
				viewDirection.rotate(-rotationSpeed, 0, 0);
			}
			// dreht Sichtmatrix nach unten
			if (display.isKeyPressed(GLFW.GLFW_KEY_S) || display.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
				viewDirection.rotate(rotationSpeed, 0, 0);
			}
			// dreht Sichtmatrix nach links
			if (display.isKeyPressed(GLFW.GLFW_KEY_A) || display.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
				viewDirection.rotate(0, -rotationSpeed, 0);
			}
			// Dreht Sichtmatrix nach rechts 
			if (display.isKeyPressed(GLFW.GLFW_KEY_D) || display.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
				viewDirection.rotate(0, rotationSpeed, 0);
			}
			// Rotiert Sichtmatrix nach links
			if (display.isKeyPressed(GLFW.GLFW_KEY_Q)) {
				viewDirection.rotate(0, 0, rotationSpeed);
			}
			// Rotiert Sichtmatrix nach rechts
			if (display.isKeyPressed(GLFW.GLFW_KEY_E)) {
				viewDirection.rotate(0, 0, -rotationSpeed);
			}
			//wenn Leertaste gedr�ckt dann stop
			if (!display.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
				// Setzt den BewegungsVektor zur�ck
				movement.x = 0;
				movement.y = 0;
				movement.z = movementSpeed;
				// dreht den BewegungsVektor durch die SichtMatrix
				movement.apply(viewDirection);
				// addiert den BewegungsVektor zum Kamera-Positions-Vektor 
				cameraPosition.add(movement);
				
				food.update();
			}
		
			// ruft die Methode zum Updaten der Positionen aufd
			updateSnakePositions(); 
			
			//checkt f�r jede Kugel ob man kollidiert
			for(int i=2;i<snakePositions.length;i++) {
				if(sphereDistance(snakePositions[0],snakePositions[i])<0) {
					isAlive=false;
					System.out.println("Du ficker bist gestorben");
				}
			}
			
			 	
			//falls Schlangenkopf Essen trifft dann neues Essen erstellen                   
			if(food.distanceTo(snakePositions[0])<sphereRadius) {   

				food = new Food(foodSize ); 
				System.out.println("Korn gefressen!");
				System.out.println("L�nge "+(snakePositions.length+1) );
				
				if(snakePositions.length < 32) {
					Vector3f[] temp =  new Vector3f [snakePositions.length +1];	
					for(int i = 0;i<snakePositions.length;i++) {
						temp[i] = snakePositions[i];
						temp[snakePositions.length] = snakePositions[snakePositions.length-1].copy();
						snakePositions = temp;
					}
					goodPosition = false;
					//solange keine gute Position gefunden wurde soll das korn woanders erscheinen
					while(!goodPosition) {
						food = new Food(foodSize);
						goodPosition = true;
						for(int i=0;i<snakePositions.length;i++) {
							if(food.distanceTo(snakePositions[i])<sphereRadius) {
								goodPosition = false;
							}
						}
					}
				}  
			}
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
	
	/**
	 * Gibt die Distanz zwischen zwei Kugeln der Schlange zur�ck.
	 * 
	 * @param a Ortsvektor der ersten Kugel
	 * @param b OrtsVektor der zweiten Kugel
	 * @return Distanz zwischen den Kugeln
	 */
	private float sphereDistance(Vector3f a, Vector3f b) { 
		// setzt  temp auf den Gegenvektor von a und addiert dann b
		// temp ist dann b-a
		Vector3f temp = a.copy();
		temp.scale(-1);
		temp.add(b);
		// bringt alle Werte mit Modulo in den Bereich von -0.5f bis 0.5f       
		// sorgt so f�r Kollision mit Schlangen aus anderen K�sten
		temp.x = (temp.x+10.5f)%1-0.5f;
		temp.y = (temp.y+10.5f)%1-0.5f;
		temp.z = (temp.z+10.5f)%1-0.5f;
		// gibt die Distanz zwischen den Mittelpunkten minus die Radien zur�ck
		return temp.getLength()-2*sphereRadius;
	}
	
	private boolean foodDistanceSnake() {
		
		
		
	return false;
	}
	
	private boolean foodDistanceHead() {
		
		
		
	return false;
	}
	
	public void addSphere() {
		
		
	}
	
}