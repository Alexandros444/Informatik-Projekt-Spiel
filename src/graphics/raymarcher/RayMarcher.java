package graphics.raymarcher;

import org.lwjgl.opengl.GL11;

import gamelogic.Snake;
import graphics.Vao;

/**
 * Der 3D-Renderer unseres Programms, basierend auf RayMarching.<br>
 * Enth�lt Code zum Rendern von Schlange, Futter und Gitter.
 * 
 * @author Ben
 */
public class RayMarcher {
	
	public RayMarcherShader shader;
	public Vao vao;
	
	/**
	 * Erstellt einen neuen RayMarcher-Renderer.
	 */
	public RayMarcher() {
		// erstellt den entsprechenden Shader
		shader = new RayMarcherShader();
		shader.start();

		// Erstellt ein neues VAO mit den Eckpunkten eines Rechtecks
		vao = new Vao(new float[] {-1,-1,-1,1,1,-1,-1,1,1,1,1,-1});
		
		// Setzt die Hintergrundfarbe auf Magenta
		GL11.glClearColor(1,0,1,0);
	}
	
	/**
	 * Rendert die gesamte Szene
	 * 
	 * @param snake die zu rendernde Schlange
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void render(Snake snake, int width, int height) {
		// Setzt den zu rendernden Bereich (bei Fenstergr��en�nderungen wichtig)
		GL11.glViewport(0,0,width,height);
		
		// Setzt den Inhalt des Fensters auf die Hintergrundfarbe zur�ck
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		// l�dt alle Infos in den Shader
		shader.loadViewMatrix(snake.viewDirection);
		shader.loadPosition(snake.cameraPosition);
		shader.loadSnake(snake.snakePositions);
		shader.loadFoodPosition(snake.food.foodPosition);
		shader.loadFoodRadius(snake.food.radius);
		
		// l�dt das aktuelle Seitenverh�ltnis des Fensters in den Shader
		float ratio = (float)width/height;
		shader.loadScreenRatio(ratio);

		// Rendert das Viereck
		vao.render();
	}
	
	/**
	 * L�scht den Renderer und gibt genutzte Ressourcen frei.
	 */
	public void destroy() {
		shader.destroy();
		vao.destroy();
	}
	
}
