package graphics.gui;

import graphics.Texture;

/*
 * Beinhaltet Textur f�r Font
 * 
 * @author Alex
 * 
 * @param relativer Pfad
 */
public class Font {

	public Texture texture;
	
	public Font(String path) {
		texture = new Texture(path);
	}

}
