package graphics.gui;

import org.lwjgl.glfw.GLFW;

import gamelogic.World;
import graphics.GameRenderer;
import graphics.GuiRenderer;
import graphics.core.Display;
import graphics.gui.engine.ContainerComponent;
import graphics.gui.engine.KeyInput;
import graphics.gui.engine.fonts.Font;
import graphics.gui.engine.fonts.MonospaceFont;
import sounds.SoundManager;
import sounds.core.SoundSource;
import util.Settings;
import util.math.Matrix3f;


/**
 * Klasse f�r den Haupt-Gui-Container unseres Spiels. Verwaltet sowohl das Spiel und den Renderer dazu, als auch das gesamte Gui.
 * 
 * @author Ben
 */
public class MainGuiContainer extends ContainerComponent {
	
	private Font font;
	private Settings settings;
	
	private GameRenderer gameRenderer;
	private GuiRenderer guiRenderer;
	private World world;
	
	private GameGui gameGui;

	private KeyInput pauseKey;
	private PauseMenu pauseMenu;
	private boolean isPauseMenuOpen;
	
	private StartMenu startMenu;
	private boolean hasGameStarted;
	
	private GameModeMenu gameModeMenu;
	private boolean isGameModeMenuOpen;
	
	private DeathMenu deathScreen;
	private boolean isDeathMenuOpen;
	
	private IntroScreen introScreen;
	private boolean isIntroScreenOpen;
	
	private boolean isFullscreen;
	public boolean hasFullscreenChanged;
	
	private float scaleX = 1; 
	private float scaleY = 1;
	
	private int playerCount;
	
	private KeyInput screenshotKey;
	
	private boolean isMusicPlaying;
	private SoundSource music;
	
	/**
	 * Erstellt den Container
	 * 
	 * @param settings Einstellungs-Objekt
	 * @param pauseKey Taste f�r Pausenmen�
	 */
	public MainGuiContainer(Settings settings, SoundManager soundManager, KeyInput pauseKey, KeyInput screenshotKey) {
		super(0,0);
		this.settings = settings;
		this.pauseKey = pauseKey;
		this.screenshotKey = screenshotKey;
		music = soundManager.getMusicSource();
		font = new MonospaceFont("res/font/ascii.png");
		
		gameRenderer = new GameRenderer(settings.isCaveEffectEnabled, settings.isAcidEffectEnabled);
		gameRenderer.setPixelSize(settings.pixelSize);
		gameRenderer.setFOV(settings.fov);
		gameRenderer.setReflectivity(settings.reflectivity);
		guiRenderer = new GuiRenderer();
		world = new World();
		
		gameGui = new GameGui(font,settings);
		super.addComponent(gameGui);
		
		openStartMenu();
		
		introScreen = new IntroScreen();
		super.addComponent(introScreen);
		isIntroScreenOpen = true;
		
		isFullscreen = settings.isFullscreen;
		isMusicPlaying = settings.isMusicEnabled;
		if(settings.isMusicEnabled) {
			music.play();
		}
	}
	
	/**
	 * Updated das Spiel und Gui
	 * 
	 * @param display Fenster, durch dessen Tastendr�cke die Schlange gesteuert wird
	 */
	public void update(Display display) {
		super.update();
		
		world.update(display);
		
		gameGui.displayScore(world.score);
		if(world.hasSecondSnake) {
			gameGui.displaySecondScore(world.secondScore);
		}
		
		if(!hasGameStarted) {
			if (isGameModeMenuOpen) {
				// GameModeMenu ist offen
				if (gameModeMenu.isStartRequested()) {
					world.setGameMode(gameModeMenu.getSelectedMode());
					closeGameModeMenu();
					display.toggleCursor();
					startGame();
					gameGui.showScore();
					if (world.hasSecondSnake) {
						gameGui.showSecondScore();
					}else{
						gameGui.showCrosshairs();
					}
				}else if(pauseKey.wasKeyPressed()) {
					closeGameModeMenu();
					openStartMenu();
				}
			}else {
				// Hauptmen� ist offen
				if(startMenu.isStartRequested()) {
					playerCount = startMenu.getPlayerCount();
					closeStartMenu();
					openGameModeMenu();
				}else if(startMenu.hasSettingsChanged) {
					applyChangedSettings(display);
				}
			}
		}else {	
			// Spiel hat begonnen
			if(isDeathMenuOpen) {
				// Death-Men� ist offen
				if(deathScreen.isExitRequested()) {
					// schlie�t den DeathScreen und �ffnet wieder das Hauptmen�
					closeDeathScreen();
					gameGui.hideScore();
					gameGui.hideSecondScore();
					openStartMenu();
					world.reset();
					}
				else if(display.isKeyPressed(GLFW.GLFW_KEY_ENTER)||display.isKeyPressed(GLFW.GLFW_KEY_SPACE)||deathScreen.isRestartRequested()){ 
					restartGame();
					if(!world.hasSecondSnake) {
						gameGui.showCrosshairs();
					}
					display.toggleCursor();
				}
			}else {
				// Death-Men� ist nicht offen
				if(world.hasSnake&&world.gameOver) {
					// Schlange ist gestorben
					gameGui.hideCrosshairs();
					openDeathScreen();
					saveScore();
					display.toggleCursor();
				}
				// Pausenmen� kann nur ge�ffnet werden wenn DeathMen� geschlossen ist
				if(isPauseMenuOpen) {
					if (pauseMenu.wasScreenshotButtonClicked()) {
						takeScreenshot(display.getWidth(),display.getHeight());
					}
					if (pauseMenu.isContinueRequested()||pauseKey.wasKeyPressed()){
						// schlie�t das Pausenmen� wieder
						closePauseMenu();
						if (!world.hasSecondSnake) {
							gameGui.showCrosshairs();
						}
						display.toggleCursor();
					}else if(pauseMenu.isExitRequested()) {
						// �ffnet das Hauptmen� und setzt die Welt zur�ck
						saveScore();
						closePauseMenu();
						gameGui.hideScore();
						gameGui.hideSecondScore();
						openStartMenu();
						world.reset();
					}else if(pauseMenu.hasSettingsChanged) {
						applyChangedSettings(display);
					}
				} else if(pauseKey.wasKeyPressed() || !display.isFocused()) {
					// �ffnet das Pausenmen�
					openPauseMenu();
					gameGui.hideCrosshairs();
					display.toggleCursor();
				}
			}
		}
		if(isIntroScreenOpen && introScreen.isFinished()) {
			isIntroScreenOpen = false;
			super.removeComponent(introScreen);
			introScreen.destroy();
			introScreen = null;
		}
	}
	
	private void saveScore() {
		if(world.gameMode == World.MODE_NORMAL) {
			if(settings.normalScore<world.score) {
				settings.normalScore = world.score;			
			}
		}else if(world.gameMode == World.MODE_FAST) {
			if(settings.fastScore<world.score) {
				settings.fastScore = world.score;			
			}
		}else {
			if(settings.tunnelScore<world.score) {
				settings.tunnelScore = world.score;			
			}
		}
	}
	
	/**
	 * �ffnet das Hauptmen�
	 */
	private void openStartMenu() {
		startMenu = new StartMenu(font,settings);
		hasGameStarted = false;
		super.addComponent(startMenu);
	}
	
	/**
	 * Schlie�t das Hauptmen�
	 */
	private void closeStartMenu() {
		super.removeComponent(startMenu);
		startMenu.destroy();
		startMenu = null;
	}
	
	/**
	 * �ffnet das GameModeMenu
	 */
	private void openGameModeMenu() {
		gameModeMenu = new GameModeMenu(font,settings);
		isGameModeMenuOpen = true;
		super.addComponent(gameModeMenu);
	}
	
	/**
	 * Schlie�t das GameModeMenu
	 */
	private void closeGameModeMenu() {
		isGameModeMenuOpen = false;
		super.removeComponent(gameModeMenu);
		gameModeMenu.destroy();
		gameModeMenu = null;
	}
	
	/**
	 * Startet das Spiel
	 */
	private void startGame() {
		hasGameStarted = true;
		world.spawnSnake();
		if(playerCount == 2){ 
			world.spawnSecondSnake();
		}
	}
	/**
	 * Schlie�t das deathMenu und startet das Spiel neu
	 */
	private void restartGame() {
		hasGameStarted = true; 
		closeDeathScreen();
		world.reset();
		world.spawnSnake();	
		if(playerCount == 2) {
			world.spawnSecondSnake();
		}
	}
	
	/**
	 * Pausiert das Spiel und �ffnet das Pausenmen�
	 */
	private void openPauseMenu() {
		isPauseMenuOpen = true;
		pauseMenu = new PauseMenu(font,settings);
		super.addComponent(pauseMenu);
		world.pause();
	}
	
	/**
	 * Schlie�t das Pausenmen� und de-pausiert das Spiel
	 */
	private void closePauseMenu() {
		isPauseMenuOpen = false;
		super.removeComponent(pauseMenu);
		pauseMenu.destroy();
		pauseMenu = null;
		world.unpause();
	}
	
	/**
	 * �ffnet den Deathscreen
	 */
	private void openDeathScreen() {
		isDeathMenuOpen = true;
		deathScreen = new DeathMenu(font,world,settings);
		super.addComponent(deathScreen);
	}
	
	/**
	 * Schlie�t den Deathscreen
	 */
	private void closeDeathScreen() {
		isDeathMenuOpen = false;
		super.removeComponent(deathScreen);
		deathScreen.destroy();
		deathScreen = null;
	}
	
	/**
	 * Wendet �nderungen der Settings auf alles an
	 */
	private void applyChangedSettings(Display display) {
		gameGui.crosshairs.loadImage(settings.crosshairImagePath);
		gameRenderer.setPixelSize(settings.pixelSize);
		gameRenderer.setFOV(settings.fov);
		gameRenderer.setReflectivity(settings.reflectivity);
		if(isFullscreen != settings.isFullscreen) {
			hasFullscreenChanged = true;
		}
		isFullscreen = settings.isFullscreen;
		if(settings.cursorFrame!=0) {
			display.setCursor(settings.cursorImagePath);
		}else {
			display.setStandardCursor();
		}
		gameRenderer.applyEffects(settings.isCaveEffectEnabled,settings.isAcidEffectEnabled);
		if (isMusicPlaying!=settings.isMusicEnabled) {
			isMusicPlaying = settings.isMusicEnabled;
			if(settings.isMusicEnabled) {
				music.play();
			}else {
				music.stop();
			}
		}
	}
	
	/**
	 * Rendert das Spiel und das Gui
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public void render(int width, int height) {
		if (screenshotKey.wasKeyPressed()) {
			takeScreenshot(width,height);
		}
		gameRenderer.render(world,width,height);
		guiRenderer.render(width,height,this);
	}
	
	/**
	 * Nimmt einen Screenshot des Spiels auf.
	 */
	private void takeScreenshot(int width, int height) {
		gameRenderer.renderToFile("Snake3001_Screenshot",width,height,world);
		gameGui.showMessage("Screenshot saved as Snake3001_Screenshot.png");
	}


	
	/**
	 * Gibt zur�ck, ob das Spiel geschlossen werden soll
	 * 
	 * @return ob das Spiel geschlossen werden soll
	 */
	public boolean isCloseRequested() {
		return !hasGameStarted&&!isGameModeMenuOpen&&startMenu.isCloseRequested(); 
	}
	
	/**
	 * L�scht alle zugeh�rigen Ressourcen, um wieder Speicher freizugeben
	 */
	public void destroy() {
		super.destroy();
		font.destroy();
		gameRenderer.destroy();
		guiRenderer.destroy();
	}
	
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		Matrix3f scaleMatrix = new Matrix3f(); 
		 
		scaleMatrix.m00 *= scaleX;
		scaleMatrix.m11 *= scaleY;
		super.setInnerTransform(scaleMatrix);
	}
	
	public void setSize(int width, int height) {
		super.setSize((int)(width/scaleX),(int)(height/scaleY));
	}
}
