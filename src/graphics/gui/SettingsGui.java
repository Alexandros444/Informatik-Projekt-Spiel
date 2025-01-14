package graphics.gui;

import graphics.gui.engine.components.BoxComponent;
import graphics.gui.engine.components.ImageComponent;
import graphics.gui.engine.components.TextButtonComponent;
import graphics.gui.engine.components.TextComponent;
import graphics.gui.engine.fonts.Font;
import util.Settings;

/**
 * Einstellungs Men�
 * <br><br>
 * @author Alex
 */
public class SettingsGui extends BoxComponent {
	// Variablen
	private TextButtonComponent closeButton, saveButton, crosshairButton, fullscreenButton, pixelSizeButton,
	cursorButton, caveButton, acidButton, fovButton, reflectionButton, musicButton;
	private ImageComponent crosshairImage, fullscreenImage, cursorImage, musicImage;
	private TextComponent crosshairText, fullscreenText, pixelSizeText, cursorText, fovText, reflectionText;
	private Settings settings;
	private TextComponent headlineText;
	

	public boolean hasSettingsChanged;
	private int crosshairFrame, pixelSize, maxPixelSize = 10, cursorFrame, maxFov = 16; 
	private float minFov = 0.25f;
	private float fov;
	private float reflectivity;
	private boolean isFullscreen, isCaveEffect, isAcidEffect, isMusic;
	/**
	 * Konstruktor
	 * 
	 * @param font Schriftart
	 * @param settings Einstellungn
	 */
	public SettingsGui(Font font, Settings settings) {
		// SuperKonstruktor
		super(600,500,0xf0000000,0xbf808080,4);
		super.setPosition(POSITION_CENTER);
		super.setWidthMode(WIDTH_STATIC);
		super.setHeightMode(HEIGHT_STATIC);
		super.setInnerOffset(160,240);
		
		this.settings = settings;
		crosshairFrame = settings.crosshairFrame;
		pixelSize = settings.pixelSize;
		isFullscreen = settings.isFullscreen;
		cursorFrame = settings.cursorFrame;
		fov = settings.fov;
		reflectivity = settings.reflectivity;
		isCaveEffect = settings.isCaveEffectEnabled;
		isAcidEffect = settings.isAcidEffectEnabled;
		isMusic = settings.isMusicEnabled;
		
		// Komponenten
		headlineText= new TextComponent("Settings",font);
		headlineText.setScale(3);
		headlineText.setPosition(POSITION_CORNER_TOPLEFT);
		headlineText.setOffset(220,-30);
		super.addComponent(headlineText);
		
		
		closeButton = new TextButtonComponent(125,40,"Close",font);
		closeButton.setPosition(POSITION_CORNER_BOTTOMRIGHT);
		closeButton.setOffset(12,10);
		super.addComponent(closeButton);
		
		
		saveButton = new TextButtonComponent(125,40,"Apply",font);
		saveButton.setPosition(POSITION_CORNER_BOTTOMLEFT);
		saveButton.setOffset(12,10);
		super.addComponent(saveButton);
		
		//Crosshairs
		crosshairButton = new TextButtonComponent(60,60,"",font);
		crosshairButton.setPosition(POSITION_CORNER_TOPLEFT);
		crosshairButton.setOffset(12,18);
		
		crosshairImage = new ImageComponent(settings.crosshairImagePath);
		crosshairImage.setPosition(POSITION_CORNER_TOPLEFT);
		crosshairImage.setSize(40,40);
		crosshairImage.setOffset(10,10);

		crosshairButton.addComponent(crosshairImage);
		super.addComponent(crosshairButton);
		
		crosshairText = new TextComponent("Crosshair",font);
		crosshairText.setPosition(POSITION_CORNER_TOPLEFT);
		crosshairText.setOffset(84,39);
		crosshairText.setScale(2);
		super.addComponent(crosshairText);

		//Fullsceen
		fullscreenButton = new TextButtonComponent(60,60,"",font);
		fullscreenButton.setPosition(POSITION_CORNER_TOPLEFT);
		fullscreenButton.setOffset(12,118);

		fullscreenImage = new ImageComponent("res/fullscreen"+isFullscreen+".png");
		fullscreenImage.setPosition(POSITION_CORNER_TOPLEFT);
		fullscreenImage.setSize(40,40);
		fullscreenImage.setOffset(10,10);
		
		fullscreenButton.addComponent(fullscreenImage);
		super.addComponent(fullscreenButton);
		
		fullscreenText = new TextComponent("Fullscreen",font);
		fullscreenText.setPosition(POSITION_CORNER_TOPLEFT);
		fullscreenText.setOffset(84,139);
		fullscreenText.setScale(2);
		super.addComponent(fullscreenText);
		
		//PixelSize
		pixelSizeButton = new TextButtonComponent(60,60,""+pixelSize,font);
		pixelSizeButton.setPosition(POSITION_CORNER_TOPLEFT);
		pixelSizeButton.setOffset(12,218);
		super.addComponent(pixelSizeButton);
		
		pixelSizeText = new TextComponent("Pixel Size",font);
		pixelSizeText.setPosition(POSITION_CORNER_TOPLEFT);
		pixelSizeText.setOffset(84,239);
		pixelSizeText.setScale(2);
		super.addComponent(pixelSizeText);
		
		//CURSOR
		cursorButton = new TextButtonComponent(60,60,"",font);
		cursorButton.setPosition(POSITION_CORNER_TOPLEFT);
		cursorButton.setOffset(12,318);

		cursorImage = new ImageComponent(settings.cursorImagePath);
		cursorImage.setPosition(POSITION_CORNER_TOPLEFT);
		cursorImage.setSize(40,40);
		cursorImage.setOffset(10,10);
		
		cursorButton.addComponent(cursorImage);
		super.addComponent(cursorButton);
		
		cursorText = new TextComponent("Cursor",font);
		cursorText.setPosition(POSITION_CORNER_TOPLEFT);
		cursorText.setOffset(84,339);
		cursorText.setScale(2);
		super.addComponent(cursorText);
		
		//FOV
		fovButton = new TextButtonComponent(60,60,""+fov,font);
		fovButton.setPosition(POSITION_CORNER_TOPLEFT);
		fovButton.setOffset(312,18);
		super.addComponent(fovButton);
		
		fovText = new TextComponent("FOV",font);
		fovText.setPosition(POSITION_CORNER_TOPLEFT);
		fovText.setOffset(384,39);
		fovText.setScale(2);
		super.addComponent(fovText);
		
		//REFLECTION
		reflectionButton = new TextButtonComponent(60,60,(int)(reflectivity*100)+"%",font);
		reflectionButton.setPosition(POSITION_CORNER_TOPLEFT);
		reflectionButton.setOffset(312,118);
		super.addComponent(reflectionButton);
		
		reflectionText = new TextComponent("Reflections",font);
		reflectionText.setPosition(POSITION_CORNER_TOPLEFT);
		reflectionText.setOffset(384,139);
		reflectionText.setScale(2);
		super.addComponent(reflectionText);
		
		//CAVE EFFECT
		caveButton = new TextButtonComponent(200,60,"Cave Effect:"+(isCaveEffect?"On":"Off"),font);
		caveButton.setPosition(POSITION_CORNER_TOPLEFT);
		caveButton.setOffset(312,218);
		super.addComponent(caveButton);
		if(isCaveEffect) {
			caveButton.setBackgroundColor(0x80404040);
		}else {
			caveButton.setBackgroundColor(0x80000000);
		}
		
		
		//ACID EFFECT
		acidButton = new TextButtonComponent(200,60,"Acid Effect:"+(isAcidEffect?"On":"Off"),font);
		acidButton.setPosition(POSITION_CORNER_TOPLEFT);
		acidButton.setOffset(312,318);
		super.addComponent(acidButton);
		if(isAcidEffect) {
			acidButton.setBackgroundColor(0x80404040);
		}else {
			acidButton.setBackgroundColor(0x80000000);
		}
		
		
		//MUSIC
		musicButton = new TextButtonComponent(60,60,"",font);
		musicButton.setPosition(POSITION_CORNER_TOPLEFT);
		musicButton.setOffset(312,418);

		musicImage = new ImageComponent("res/music_"+isMusic+".png");
		musicImage.setPosition(POSITION_CORNER_TOPLEFT);
		musicImage.setSize(40,40);
		musicImage.setOffset(10,10);
		musicButton.addComponent(musicImage);
		super.addComponent(musicButton);
	}
	
	/**
	 * Methode zum Updaten des Settings-Men�s
	 */
	public void update() {
		if(crosshairButton.wasClicked()) {
			// Fadenkreuz wechseln
			crosshairFrame++;
			crosshairFrame %= settings.crosshairCount;
			crosshairImage.loadImage("res/crosshairs"+crosshairFrame+".png");
			buttonPressed();
		}
		if(crosshairButton.wasRightClicked()) {
			// Fadenkreuz wechseln
			crosshairFrame--;
			if(crosshairFrame < 0) {
				crosshairFrame = settings.crosshairCount-1;
			}
			System.out.println(crosshairFrame);
			crosshairImage.loadImage("res/crosshairs"+crosshairFrame+".png");
			buttonPressed();
		}
		
		if(fullscreenButton.wasClicked()) {
			// Fadenkreuz wechseln
			if(isFullscreen) {
				isFullscreen = false;
			}else {
				isFullscreen = true;
			}
			fullscreenImage.loadImage("res/fullscreen"+isFullscreen+".png");
			buttonPressed();
		}
		
		if(pixelSizeButton.wasClicked()) {
			pixelSize %= maxPixelSize;
			pixelSize++;
			pixelSizeButton.setText(""+pixelSize);
			buttonPressed();
		}
		if(pixelSizeButton.wasRightClicked()) {
			pixelSize--;
			if(pixelSize == 0) {
				pixelSize = 10;
			}
			pixelSizeButton.setText(""+pixelSize);
			buttonPressed();
		}
		if(cursorButton.wasClicked()) {
			cursorFrame++;
			cursorFrame%=2;
			cursorImage.loadImage("res/cursor"+cursorFrame+".png");
			buttonPressed();
		}
		if(fovButton.wasClicked()) {
			fov *= 2;
			fov = Math.round(100*fov)/100f;
			if(fov > maxFov) {
				fov = minFov;
			}
			fovButton.setText(""+fov);
			buttonPressed();
		}
		if(fovButton.wasRightClicked()) {
			fov /= 2;
			fov = Math.round(100*fov)/100f;
			if(fov < minFov) {
				fov = maxFov;
			}
			fovButton.setText(""+fov);
			buttonPressed();
		}
		if(reflectionButton.wasClicked()) {
			reflectivity = (reflectivity+0.25f)%1.25f;
			reflectionButton.setText((int)(reflectivity*100)+"%");
			buttonPressed();
		}
		if(reflectionButton.wasRightClicked()) {
			reflectivity = (reflectivity-0.25f)%1.25f;
			if(reflectivity < 0) {
				reflectivity = 1;
			}
			reflectionButton.setText((int)(reflectivity*100)+"%");
			buttonPressed();
		}
		if(caveButton.wasClicked()) {
			if(isCaveEffect) {
				isCaveEffect = false;
				caveButton.setBackgroundColor(0x80000000);
			}else {
				isCaveEffect = true;
				caveButton.setBackgroundColor(0x80404040);
			}
			caveButton.setText("Cave_Effect:"+(isCaveEffect?"On":"Off"));
			buttonPressed();
		}
		if(acidButton.wasClicked()) {
			if(isAcidEffect) {
				isAcidEffect = false;
				acidButton.setBackgroundColor(0x80000000);
			}else {
				isAcidEffect = true;
				acidButton.setBackgroundColor(0x80404040);
			}
			acidButton.setText("Acid_Effect:"+(isAcidEffect?"On":"Off"));
			buttonPressed();
		}
		if(musicButton.wasClicked()) {
			if(isMusic) {
				isMusic = false;
				musicImage.loadImage("res/music_"+isMusic+".png");
			}else {
				isMusic = true;
				musicImage.loadImage("res/music_"+isMusic+".png");
			}
			buttonPressed();
		}
		
		
		if(saveButton.wasClicked()) {
			// Einstellungen Speichern
			saveButton.setBackgroundColor(0x80000000);
			saveSettings();
		}
		
	}
	
	private void buttonPressed() {
		//ein Button wurde gedr�ckt
		saveButton.setBackgroundColor(0xFF222255);
	}
	
	/**
	 * Speichert die Werte in Datei
	 */
	private void saveSettings() {
		hasSettingsChanged = true;
		settings.crosshairFrame = crosshairFrame;
		settings.crosshairImagePathRenew();
		settings.pixelSize = pixelSize;
		settings.isFullscreen = isFullscreen;
		settings.cursorFrame = cursorFrame;
		settings.curserImagePathRenew();
		settings.isCaveEffectEnabled = isCaveEffect;
		settings.isAcidEffectEnabled = isAcidEffect;
		settings.fov = fov;
		settings.reflectivity = reflectivity;
		settings.isMusicEnabled = isMusic;
		settings.save();
	}
	
	
	/** 
	 * @return isCloseRequested Ob die Einstellungen geschlossen werden sollen
	 */
	public boolean isCloseRequested() {
		return closeButton.wasClicked();
	}
}
