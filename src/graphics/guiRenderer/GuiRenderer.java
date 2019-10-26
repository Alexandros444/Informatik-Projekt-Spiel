package graphics.guiRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import graphics.FpsCounter;
import graphics.gui.BoxComponent;
import graphics.gui.ContainerComponent;
import graphics.gui.Font;
import graphics.gui.GuiComponent;
import graphics.gui.ImageComponent;
import graphics.gui.MonospaceFont;
import graphics.gui.TextComponent;
import main.Settings;

/**
 * Der Renderer f�r das Gui unseres Programms.<br>
 * Enth�lt bisher nur ein paar einfache Anzeigen als Test, um zu schauen, ob das Gui-Komponentensystem funktioniert
 * 
 * @author Ben
 */
public class GuiRenderer {
	
	private GuiShader shader;
	private GuiComponent crosshairs;
	private TextComponent scoreText, fpsText;
	private ContainerComponent container;
	private Font font;
	
	/**
	 * Erstellt einen neuen Gui-Renderer.
	 */
	public GuiRenderer(Settings settings) {
		shader = new GuiShader();
		font = new MonospaceFont("res/font/ascii.png");
		// erstellt eine neue Gui-Komponente aus dem Bild des Fadenkreuzes
		crosshairs = new ImageComponent("res/"+settings.guiRendererCrosshair+".png");
		// erstellt zwei leere Textkomponenten f�r Punktzahl und FPS
		scoreText = new TextComponent("", font);
		fpsText = new FpsCounter(font);
		
		// legt die Positionen der Elemente fest
		crosshairs.setPosition(GuiComponent.POSITION_CENTER);
		
		scoreText.setPosition(GuiComponent.POSITION_CORNER_TOPLEFT);
		scoreText.setOffset(24,24);
		scoreText.setScale(3);
		
		fpsText.setPosition(GuiComponent.POSITION_CORNER_TOPRIGHT);
		fpsText.setOffset(6,6);
		fpsText.setScale(2);
		
		// Box zum Testen von Boxkomponenten, unten links in der Ecke
		BoxComponent testBox = new BoxComponent(250,100,0x80000000,0xa0808080,3);
		testBox.setPosition(GuiComponent.POSITION_CORNER_BOTTOMLEFT);
		testBox.setOffset(6,6);
		testBox.setInnerOffset(6,6);
		TextComponent testText1 = new TextComponent("TextBox-Test",font);
		TextComponent testText2 = new TextComponent("Element-Flow-Test",font);
		testText1.setScale(2);
		testText2.setScale(2);
		testText1.setOffset(4,4);
		testText2.setOffset(4,4);
		testBox.addComponent(testText1);
		testBox.addComponent(testText2);
		
		// erstellt den Container und f�gt alle Elemente zu ihm zu
		container = new ContainerComponent(640,480);
		container.addComponent(crosshairs);
		container.addComponent(scoreText);
		container.addComponent(fpsText);
		container.addComponent(testBox);
		
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Rendert bisher nur ein einfaches Viereck als Test, soll aber sp�ter die ganze Gui rendern.
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void render(int width, int height) {
		// bindet den Haupt-Framebuffer und bereitet den Gui-Shader vor
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER,0);
		GL11.glViewport(0,0,width,height);
		shader.start();
		shader.loadScreenSize(width,height);

		// passt die Gr��e des Containers an
		container.setSize(width,height);
		
		// updated den Container
		container.update();
		
		// rendert den Container mit allen Elementen
		container.render(shader);
	}

	/**
	 * Zeigt die gegebene Punktzahl als Text an
	 * 
	 * @param score Punktzahl
	 */
	public void displayScore(int score) {
		scoreText.setText("Score: "+score);
	}
	
	/**
	 * L�scht den Renderer und gibt genutzte Ressourcen frei.
	 */
	public void destroy() {
		shader.destroy();
		crosshairs.destroy();
		scoreText.destroy();
		font.destroy();
	}
	
}
