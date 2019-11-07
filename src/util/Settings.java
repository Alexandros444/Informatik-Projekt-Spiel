package util;

/**
 * Speichert die derzeitigen Einstellungen
 * 
 * @author Alex
 */
public class Settings {
	// Variablen Deklaration
	private static String[] STANDARD_NAMES;
	private static String[] STANDARD_VALUES;

	private SettingsLoader config;

	public int displayWidth;
	public int displayHeight;
	public int snakeScore;
	public int crosshairFrame, crosshairCount = 4;
	public boolean displayFullscreen;
	public String crosshairPath;

	/**
	 * Instanz der Klasse erstellt Standard-Werte und l�dt Einstellungen
	 */
	public Settings() {
		// Initailisiert Standard-Werte 
		STANDARD_NAMES = new String[] {"DISPLAY_WIDTH","DISPLAY_HEIGHT","SNAKE_SCORE","CROSSHAIR_FRAME","DISPLAY_FULLSCREEN"};
		STANDARD_VALUES = new String[] {""+960,""+540,""+0,""+0,""+false};

		// Erstellt neue Instanz der Einstellungen--> Einstellungen werden geladen
		config = new SettingsLoader(STANDARD_NAMES,STANDARD_VALUES);
		
		// Variablen werden aus Einstellungen geladen 
		displayWidth = config.getInt("DISPLAY_WIDTH");
		displayHeight = config.getInt("DISPLAY_HEIGHT");
		snakeScore = config.getInt("SNAKE_SCORE");
		crosshairFrame = config.getInt("CROSSHAIR_FRAME");
		displayFullscreen = config.getBoolean("DISPLAY_FULLSCREEN");
		crosshairPathRenew();
	}

	public void crosshairPathRenew() {
		crosshairPath = "res/crosshairs"+crosshairFrame+".png";
	}
	
	/**
	 * Speichert die Werte von Values in Einstellungen(Settings)
	 */
	public void save() {
		// setzt die Werte
		config.setValue("CROSSHAIR_FRAME",""+crosshairFrame);
		config.setValue("SNAKE_SCORE",""+snakeScore);
		config.setValue("DISPLAY_WIDTH",""+displayWidth);
		config.setValue("DISPLAY_HEIGHT",""+displayHeight);
		config.setValue("DISPLAY_FULLSCREEN",""+displayFullscreen);
		// speichert die gesetzten Werte
		config.saveToFile();
	}

}
