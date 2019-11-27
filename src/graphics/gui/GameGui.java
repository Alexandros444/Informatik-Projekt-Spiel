package graphics.gui;

import graphics.gui.engine.ContainerComponent;
import graphics.gui.engine.GuiComponent;
import graphics.gui.engine.components.ImageComponent;
import graphics.gui.engine.components.TextComponent;
import graphics.gui.engine.fonts.Font;
import util.Settings;

/**
 * Enth�lt alles, was im Spiel durchgehend angezeigt wird - also bisher Fadenkreuz, Punktzahl und FPS.
 * 
 * @author Ben
 */
public class GameGui extends ContainerComponent {

	public ImageComponent crosshairs;
	private TextComponent scoreText;
	private FpsCounter fpsCounter;
	private TextComponent secondScoreText;
	
	/**
	 * Erstellt alle Komponenten
	 * 
	 * @param font Schriftart f�r die Punktzahl- und Fps-Texte
	 * @param settings Einstellungen, f�r Fadenkreuz
	 */
	public GameGui(Font font, Settings settings) {
		super(0,0);
		super.setPosition(POSITION_FULL);
		
		// Erstellt das Fadenkreuz
		crosshairs = new ImageComponent(settings.crosshairImagePath);
		crosshairs.setPosition(GuiComponent.POSITION_CENTER);
		
		// Erstellt den Text f�r die Punktzahl
		scoreText = new TextComponent("", font);
		scoreText.setPosition(GuiComponent.POSITION_CORNER_TOPLEFT);
		scoreText.setOffset(24,24);
		scoreText.setScale(3);
		scoreText.setVisibility(VISIBILITY_HIDDEN);
		
		// Erstellt den Text f�r die zweite Punktzahl
		secondScoreText = new TextComponent("", font);
		secondScoreText.setPosition(GuiComponent.POSITION_CORNER_TOPRIGHT);
		secondScoreText.setOffset(24,24);
		secondScoreText.setScale(3);
		secondScoreText.setVisibility(VISIBILITY_HIDDEN);
				
		// Erstellt die FPS-Anzeige
		fpsCounter = new FpsCounter(font);
		fpsCounter.setPosition(GuiComponent.POSITION_CORNER_TOPRIGHT);
		fpsCounter.setOffset(6,6);
		fpsCounter.setScale(2);
		
		// f�gt alle Komponenten zu sich hinzu
		super.addComponent(crosshairs);
		super.addComponent(scoreText);
		super.addComponent(fpsCounter);
		super.addComponent(secondScoreText);
	}
	
	/**
	 * Aktualisiert die angezeigte Punktzahl.
	 * 
	 * @param score derzeitige Punktzahl
	 */
	public void displayScore(int score) {
		scoreText.setText("Score: "+score);
	}
	
	/**
	 * Aktualisiert die f�r den zweiten Spieler angezeigte Punktzahl.
	 * @param score Punktzahl des zweiten Spielers
	 */
	public void displaySecondScore(int score) {
		secondScoreText.setText("SecondScore: "+ score);
	}
	
	/**
	 * Versteckt die Punktzahl-Anzeige
	 */
	public void hideScore() {
		scoreText.setVisibility(VISIBILITY_HIDDEN);
	}
	
	/**
	 * Zeigt die Punktzahl wieder an
	 */
	public void showScore() {
		scoreText.setVisibility(VISIBILITY_VISIBLE);
	}
	
	/**
	 * Versteckt die f�r den zweiten Spieler angezeigte Punktzahl
	 */
	public void hideSecondScore() {
		secondScoreText.setVisibility(VISIBILITY_HIDDEN);
		fpsCounter.setPosition(POSITION_CORNER_TOPRIGHT);
	}
	
	/**
	 * Zeigt die zweite Punktzahl wieder an
	 */
	public void showSecondScore() {
		secondScoreText.setVisibility(VISIBILITY_VISIBLE);
		fpsCounter.setPosition(POSITION_CORNER_BOTTOMRIGHT);
	}
}








