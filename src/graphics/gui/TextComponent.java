package graphics.gui;

import graphics.Matrix3f;
import graphics.Texture;
import graphics.Vao;
import graphics.guiRenderer.GuiShader;

/*
 * Klasse f�r Text-Komponenten
 * 
 * @author Alex
 */
public class TextComponent extends GuiComponent{

	private Vao vao;
	private final Texture fontTexture;
	
	private String currentText;
	private int scale = 1;
	
	public TextComponent(String text,Font font) {
		// ruft den GuiComponent-Konstruktor auf
		super(0,0);
		
		fontTexture = font.texture;
		
		// l�dt den Text
		this.setText(text);
		
	}
	
	/**
	 * Setzt den Text der Komponente
	 * 
	 * @param text Text
	 */
	public void setText(String text) {
		// pr�ft, ob der Text �berhaupt ge�ndert werden muss
		if (!text.equals(this.currentText)) {
			this.currentText = text;
			// l�scht altes Vao
			if(vao!=null) {
				vao.destroy();
			}
			// erstellt neues Vao
			vao = createTextVao(text);
			// passt die eigene Gr��e an
			refreshSize();
		}
	}
	
	/**
	 * Setzt die Schriftgr��e bzw. Skalierung
	 * @param scale Faktor
	 */
	public void setScale(int scale) {
		this.scale = scale;
		Matrix3f transform = new Matrix3f();
		transform.scale(scale);
		transform.m22 = 1;
		super.setInnerTransform(transform);
		refreshSize();
	}
	
	/**
	 * Passt die Gr��e des Elements an die Gr��e des Inhalts an.
	 */
	private void refreshSize() {
		super.setSize(6*currentText.length()*scale,8*scale);
	}
	
	/**
	 * Rendert den Text
	 */
	public void render(GuiShader shader) {
		shader.loadTransformationMatrix(super.getTotalTransform());
		fontTexture.bind();
		vao.bind();
		vao.render();
	}
	
	/**
	 * L�scht das Vao, um Speicher freizugeben
	 */
	public void destroy() {
		vao.destroy();
		fontTexture.destroy();
	}


	/**
	 * Erstellt aus dem gegebenen Text ein Vao
	 * 
	 * @param text Text
	 * @return Vao
	 */
	private static Vao createTextVao(String text) {
		float[] positions = new float[text.length()*12];
		float[] textures = new float[text.length()*12];
		
		// Position Textur zuweisen
		int index = 0;
		int x = 0;
		int y = 0;
		for (int textChar = 0; textChar < text.length(); textChar++) {
				// TEXTUREN
			// obere linke Ecke des Buchstabens in der Textur
			float texX = (text.charAt(textChar)%16)/16f;
			float texY = (float) Math.floor(text.charAt(textChar)/16f)/16f-(1/16);
			// Links-Oben
			textures[index]= texX;
			textures[index+1]= texY;
			// Links-Unten
			textures[index+2]= texX;
			textures[index+3]= texY+(1/16f);
			// Rechts-Oben
			textures[index+4]= texX+(1/16f);
			textures[index+5]= texY;
			// Rechts-Oben
			textures[index+6]= texX+(1/16f);
			textures[index+7]= texY;
			// Rechts-Unten
			textures[index+8]= texX+(1/16f);
			textures[index+9]= texY+(1/16f);
			// Links-Unten
			textures[index+10]= texX;
			textures[index+11]= texY+(1/16f);
			
				// POSITIONEN
			// Links-Oben
			positions[index]=x;
			positions[index+1]=y;
			// Links-Unten
			positions[index+2]=x;
			positions[index+3]=y+8;
			// Rechts-Oben
			positions[index+4]=x+8;
			positions[index+5]=y;
			// Rechts-Oben
			positions[index+6]=x+8;
			positions[index+7]=y;
			// Rechts-Unten
			positions[index+8]=x+8;
			positions[index+9]=y+8;
			// Links-Unten
			positions[index+10]=x;
			positions[index+11]=y+8;
			// Verschieben, Abstand zwischen Buchstaben
			x+=6;
			// Inkrementiert Pointer
			index+=12;
		}
		return new Vao(positions,textures);
	}
	
}
