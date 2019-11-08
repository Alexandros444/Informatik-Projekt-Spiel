package graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.sun.xml.internal.fastinfoset.util.ContiguousCharArrayArray;

import gamelogic.World;
import graphics.gui.DeathMenu;
import graphics.gui.GameGui;
import graphics.gui.PauseMenu;
import graphics.gui.StartMenu;
import graphics.gui.engine.ContainerComponent;
import graphics.gui.engine.KeyInput;
import graphics.gui.engine.MouseEvent;
import graphics.gui.engine.fonts.Font;
import graphics.gui.engine.fonts.MonospaceFont;
import graphics.gui.renderer.GuiShader;
import util.Settings;

/**
 * Der Renderer f�r das Gui unseres Programms.<br>
 * Enth�lt bisher nur ein paar einfache Anzeigen als Test, um zu schauen, ob das Gui-Komponentensystem funktioniert
 * 
 * @author Ben
 */
public class GuiRenderer {
	
	private GuiShader shader;
	private ContainerComponent container;
	private GameGui gameGui;
	private Font font;
	private Settings settings;
	
	private KeyInput pauseKey;
	private PauseMenu pauseMenu;
	private boolean isPauseMenuOpen;
	
	private StartMenu startMenu;
	private boolean hasGameStarted;
	
	private DeathMenu deathMenu;
	private boolean isDeathMenuOpen;
	public boolean isSnakeDead;
	
	/**
	 * Erstellt einen neuen Gui-Renderer.
	 */
	public GuiRenderer(Settings settings, KeyInput pauseKey) {
		this.settings = settings;
		this.pauseKey = pauseKey;
		shader = new GuiShader();
		font = new MonospaceFont("res/font/ascii.png");
		
		
		// erstellt den Container und f�gt ihm das "GameGui" mit FPS-Counter, Punktzahl und Fadenkreuz hinzu
		container = new ContainerComponent(640,480);
		gameGui = new GameGui(font,settings);
		container.addComponent(gameGui);
		
		startMenu = new StartMenu(font,settings);
		hasGameStarted = false;
		container.addComponent(startMenu);
		
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Rendert bisher nur ein einfaches Viereck als Test, soll aber sp�ter die ganze Gui rendern.
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void render(int width, int height, World world, MouseEvent mouseEvent) {
		// bindet den Haupt-Framebuffer und bereitet den Gui-Shader vor
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER,0);
		GL11.glViewport(0,0,width,height);
		shader.start();
		shader.loadScreenSize(width,height);
		
		
		// GUI-MANAGEMENT
		if(!hasGameStarted) {
			// Hauptmen� ist offen
			if(startMenu.isStartRequested()) {
				// Start Button wurde gedr�ckt
				hasGameStarted = true;
				container.removeComponent(startMenu);
				startMenu.destroy();
				startMenu = null;
			}else if(startMenu.hasSettingsChanged) {
				// Einstellungen im Start-Men� wurden ge�ndert
				gameGui.crosshairs.reloadImage(settings.crosshairPath);
			}
		}else {
			// Spiel hat begonnen
			if(isDeathMenuOpen) {
				// Death-Men� ist offen
				if(deathMenu.isCloseRequested()) {
					// zum Hauptmen� zur�ckkehren
					// DeathMen� beenden
					isDeathMenuOpen = false;
					container.removeComponent(deathMenu);
					deathMenu.destroy();
					deathMenu = null;
					// Hauptmen� "Starten"
					startMenu = new StartMenu(font,settings);
					hasGameStarted = false;
					container.addComponent(startMenu);
					// Welt neu starten
					world.respawnSnake();
					isSnakeDead = false;
				}
			}else {
				// Death-Men� ist nich offen
				if(isSnakeDead) {
					// Schlange ist gestorben
					isDeathMenuOpen = true;
					deathMenu = new DeathMenu(font,world);
					container.addComponent(deathMenu);
				}
				// Pausenmen� kann nur ge�ffnet werden wenn DeathMen� geschlossen ist
				if(isPauseMenuOpen) {
					// Pausenmen� IST offen
					if (pauseMenu.isContinueRequested()||pauseKey.wasKeyPressed()){
						// Pausenmen� soll geschlossen werden
						isPauseMenuOpen = false;
						container.removeComponent(pauseMenu);
						pauseMenu.destroy();
						pauseMenu = null;
						world.unpause();
					}else if(pauseMenu.isExitRequested()) {
						// zum Hauptmen� zur�ckkehren
						// Pausenmen� beenden
						isPauseMenuOpen = false;
						container.removeComponent(pauseMenu);
						pauseMenu.destroy();
						pauseMenu = null;
						// Hauptmen� "Starten"
						startMenu = new StartMenu(font,settings);
						hasGameStarted = false;
						container.addComponent(startMenu);
						// Welt Speichern und neu starten
						world.deathEvent();
						world.respawnSnake();
						world.unpause();
					}else if(pauseMenu.hasSettingsChanged) {
						// Einstellungen wurden ge�ndert
						gameGui.crosshairs.reloadImage(settings.crosshairPath);
					}
				} else if(pauseKey.wasKeyPressed()) {
					// Pausenmen� soll ge�ffnet werden
					isPauseMenuOpen = true;
					pauseMenu = new PauseMenu(font,settings);
					container.addComponent(pauseMenu);
					world.pause();
				}
			}
		}
		//ENDE GUI-MANAGEMENT
		
		// passt die Gr��e des Containers an
		container.setSize(width,height);

		// verarbeitet Maus-Events
		container.receiveMouseEvent(true,mouseEvent);
		
		// updated den Container
		container.update();
		
		// rendert den Container mit allen Elementen
		container.render(shader);
	}
	
	public boolean isCloseRequested() {
		return !hasGameStarted&&startMenu.isCloseRequested(); 
	}

	/**
	 * Zeigt die gegebene Punktzahl als Text an
	 * 
	 * @param score Punktzahl
	 */
	public void displayScore(int score) {
		gameGui.displayScore(score);
	}
	
	/**
	 * L�scht den Renderer und gibt genutzte Ressourcen frei.
	 */
	public void destroy() {
		shader.destroy();
		container.destroy();
		font.destroy();
	}
	
}
