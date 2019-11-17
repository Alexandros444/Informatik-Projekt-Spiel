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
	public int normalScore,fastScore,tunnelScore;
	public int crosshairFrame, crosshairCount = 5;
	public boolean isFullscreen;
	public int pixelSize;
	public String crosshairImagePath; //, difficultyImagePath;

	/**
	 * Instanz der Klasse erstellt Standard-Werte und l�dt Einstellungen
	 */
	public Settings() {
		// Initailisiert Standard-Werte 
		STANDARD_NAMES = new String[] {"DISPLAY_WIDTH","DISPLAY_HEIGHT","NORMAL_SCORE","FAST_SCORE","TUNNEL_SCORE","CROSSHAIR_FRAME","IS_FULLSCREEN","PIXELSIZE"};
		STANDARD_VALUES = new String[] {""+960,""+540,""+0,""+0,""+0,""+0,""+false,""+3};

		// Erstellt neue Instanz der Einstellungen--> Einstellungen werden geladen
		config = new SettingsLoader(STANDARD_NAMES,STANDARD_VALUES);
		
		// Variablen werden aus Einstellungen geladen 
		displayWidth = config.getInt("DISPLAY_WIDTH");
		displayHeight = config.getInt("DISPLAY_HEIGHT");
		normalScore = config.getInt("NORMAL_SCORE");
		fastScore = config.getInt("FAST_SCORE");
		tunnelScore = config.getInt("TUNNEL_SCORE");
		crosshairFrame = config.getInt("CROSSHAIR_FRAME");
		isFullscreen = config.getBoolean("IS_FULLSCREEN");
		pixelSize = config.getInt("PIXELSIZE");
		crosshairImagePathRenew();
		//difficultyImagePathRenew();
	}
	
	/**
	 * l�dt den Pfad des Crosshairs neu falls er ge�ndert wurde
	 */
	public void crosshairImagePathRenew() {
		crosshairImagePath = "res/crosshairs"+crosshairFrame+".png";
	}
	/** AUSKOMMENTIERT WEIL IM MOMENT NICHT GEBRAUCHT
	 * l�dt den Pfad des Difficulty Images neu falls er ge�ndert wurde
	 */
	//public void difficultyImagePathRenew() {
	//	difficultyImagePath = "res/difficulty"+difficulty+".png";
	//}
	
	
	/**
	 * Speichert die Werte von Values in Einstellungen(Settings)
	 */
	public void save() {
		// setzt die Werte
		config.setValue("CROSSHAIR_FRAME",""+crosshairFrame);
		config.setValue("NORMAL_SCORE",""+normalScore);
		config.setValue("FAST_SCORE",""+fastScore);
		config.setValue("TUNNEL_SCORE",""+tunnelScore);
		config.setValue("DISPLAY_WIDTH",""+displayWidth);
		config.setValue("DISPLAY_HEIGHT",""+displayHeight);
		config.setValue("IS_FULLSCREEN",""+isFullscreen);
		config.setValue("PIXELSIZE",""+pixelSize);
		// speichert die gesetzten Werte
		config.saveToFile();
	}

}
