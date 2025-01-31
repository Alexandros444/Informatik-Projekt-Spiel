package graphics.gui;

import graphics.gui.engine.ContainerComponent;
import graphics.gui.engine.components.BoxComponent;
import graphics.gui.engine.components.TextButtonComponent;
import graphics.gui.engine.fonts.Font;
import util.Settings;

public class PauseMenu extends BoxComponent {
	
	private TextButtonComponent continueButton;
	private TextButtonComponent settingsButton;
	private TextButtonComponent exitButton;
	private TextButtonComponent screenshotButton;
	private boolean isContinueRequested, isExitRequested;
	
	private Font font;
	private Settings settings;
	public SettingsGui settingsGui;
	private boolean areSettingsOpen;
	public boolean hasSettingsChanged;
	
	/**
	 * Konstruktor	
	 * 
	 * @param font Schriftart
	 * @param settings jetzige Einstellungen
	 */
	public PauseMenu(Font font, Settings settings) {
		super(0,0,0x80000000,0,0);
		super.setPosition(POSITION_FULL);
		ContainerComponent container = new ContainerComponent(0,0);
		container.setWidthMode(WIDTH_AUTO);
		container.setHeightMode(HEIGHT_AUTO);
		container.setPosition(POSITION_CENTER);
		super.addComponent(container);
		
		this.font = font;
		this.settings = settings;
		settingsGui = null;
		areSettingsOpen = false;
		
		continueButton = new TextButtonComponent(200, 50, "Continue", font);
		continueButton.setOffset(4,4);
		container.addComponent(continueButton);
		settingsButton = new TextButtonComponent(200, 50, "Settings", font);
		settingsButton.setOffset(4,4);
		container.addComponent(settingsButton);
		exitButton = new TextButtonComponent(200, 50, "Exit", font);
		exitButton.setOffset(4,4);
		container.addComponent(exitButton);

		screenshotButton = new TextButtonComponent(200, 50, "Screenshot (P)", font);
		screenshotButton.setPosition(POSITION_CORNER_BOTTOMLEFT);
		screenshotButton.setBorderColor(0x80404040);
		screenshotButton.setOffset(4,4);
		this.addComponent(screenshotButton);
	}
	
	
	/**
	 * Methode zum Updaten des Pause-Men�s
	 */
	public void update() {
		if(continueButton.wasClicked()) {
			// Spiel Wiederaufnehmen
			isContinueRequested = true;
		}
		if(exitButton.wasClicked()) {
			// Zur�ck zum Hauptmen�
			isExitRequested = true;
		}
		// SETTINGS-MEN�
		if(areSettingsOpen) {
			// Einstellungen SIND offen
			settingsGui.update();
			if(settingsGui.hasSettingsChanged) {
				// Einstellungen wurden ge�ndert
				hasSettingsChanged = true;
				settingsGui.hasSettingsChanged = false;
			}else {
				hasSettingsChanged = false;
			}
			if(settingsGui.isCloseRequested()) {
				// Einstellungen schlie�en
				areSettingsOpen = false;
				super.removeComponent(settingsGui);
				settingsGui.destroy();
				settingsGui = null;
			}
		}else if (settingsButton.wasClicked()) {
			// Einstllungen �ffnen
			areSettingsOpen = true;
			settingsGui = new SettingsGui(font,settings);
			super.addComponent(settingsGui);
		} 		
	}
	
	/**
	 * Ob das spiel wiederaufgenommen werden soll
	 * 
	 * @return isContinueRequest
	 */
	public boolean isContinueRequested() {
		return isContinueRequested;
	}

	/**
	 * Ob das Men� ge�ffnet werden soll
	 * 
	 * @return isExitRequested
	 */
	public boolean isExitRequested() {
		return isExitRequested;
	}
	
	/**
	 * Gibt zur�ck, ob der Screenshot-Button geklickt wurde
	 * 
	 * @return ob der Screenshot-Button geklickt wurde
	 */
	public boolean wasScreenshotButtonClicked() {
		return screenshotButton.wasClicked();
	}
	
}
