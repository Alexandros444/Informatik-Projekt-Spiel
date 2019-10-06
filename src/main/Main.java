package main;

import org.lwjgl.glfw.GLFW;

import gamelogic.Snake;
import graphics.Display;
import graphics.FpsCounter;
import graphics.Timer;
import graphics.guiRenderer.GuiRenderer;
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
		
		// Erstellt den Fps Z�hler
		FpsCounter fps = new FpsCounter();
		
		// Erstellt den RayMarcher-Renderer
		RayMarcher gameRenderer = new RayMarcher();
		// Reduziert die Aufl�sung, um die FPS zu erh�hen
		gameRenderer.setPixelSize(3);
		
		// Erstellt den Gui-Renderer
		GuiRenderer guiRenderer = new GuiRenderer();
		
		// Initialisiert Schlange
		Snake snake = new Snake();
		
		// Initialisiert einen Timer der die Zeit stoppt
		Timer timer = new Timer();
		
		// GAME LOOP l�uft solange das Fenster nicht geschlossen ist
		while(!display.isCloseRequested()) {	
			// updated die Schlange
			snake.update(display);
			
			// Fullscreen an/aus-schalten
			if(display.isKeyPressed(GLFW.GLFW_KEY_F) && (timer.getTimeSec()>0)) {
				// momentan nur im 1 sekunden abstand, sp�ter mit Key-Handler
				timer.reset();
				display.toggleFullscreeMode();
			}
			
			// �berpr�fen ob Schlange gestorben ist, wenn ja Spiel neu-Starten 
			if ((snake.isAlive==false)&&display.isKeyPressed(GLFW.GLFW_KEY_ENTER)){
			    snake = new Snake();
			}
			
			// Spiel wird gerendert
			gameRenderer.render(snake,display.getWidth(),display.getHeight());
			// Punktzahl wird angezeigt
			guiRenderer.displayScore(snake.getScore());
			// Gui wird gerendert
			guiRenderer.render(display.getWidth(),display.getHeight());
			// Display wird aktualisiert
			display.update();
			// Fps-Z�hler wird aktualisiert
			fps.update();
			
		}
	
		// Beendet den Renderer und schlie�t das Fenster
		gameRenderer.destroy();
		guiRenderer.destroy();
		display.close();
	}
	
}
