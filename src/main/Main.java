package main;

import org.lwjgl.glfw.GLFW;

import gamelogic.Snake;
import graphics.Display;
import graphics.raymarcher.RayMarcher;

/**
 * Die Klasse mit der Main-Methode unseres Programms.<br>
 * 
 * @author Ben
 * @author Alex
 */

public class Main {
	
	/**
	 * Die Main-Methode selbst. Sie �ffnet das Fenster und updated es regelm��ig.<br>
	 * Alles wichtige steht quasi hier.
	 * 
	 * @param args Kommandozeilenparameter
	 */
	
	public static void main(String[] args) {
		
		// Erstellt ein neues Fenster
		Display display = new Display(960,540,"SNAKE 3001");
		// Setzt das Fenster-Symbol
		display.setWindowIcon("res/icon.png");
		
		// Erstellt und den RayMarcher-Renderer
		RayMarcher renderer = new RayMarcher();
		// Initialisiert Schlange
		Snake snake = new Snake();
		
		while(!display.isCloseRequested()) {	
			// dreht die Sichtmatrix je nach Tasteninput und l�dt sie in den Shader
			snake.update(display);
			// nimmt Zeit f�r snake.update
			
			if ((snake.isAlive==false)&&display.isKeyPressed(GLFW.GLFW_KEY_ENTER)){
			    snake = new Snake();
			}
			
			// rendert und updated den Bildschirm
			renderer.render(snake,display.getWidth(),display.getHeight());
			display.update();
		}
	
		// Beendet den Renderer und schlie�t das Fenster
		renderer.destroy();
		display.close();
	}
	
}
