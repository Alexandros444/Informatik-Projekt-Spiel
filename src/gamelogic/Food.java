package gamelogic;

import graphics.Vector3f;

/**
 * Die Klasse f�r das Essen was die Schlange wachsen l�sst.<br>
 * 
 *  @author Jakopo
 */

public class Food {
	public Vector3f foodPosition;
	
		public Food() {
			//setzt das Essen auf eine zuf�llige Position
			float a = (float) (Math.random()-0.5);
			float b = (float) (Math.random()-0.5);
			float c = (float) (Math.random()-0.5);
			
			foodPosition = new Vector3f(a,b,c);				
		}
		//errechnet Distanz zum Essen
		public float distanceTo(Vector3f a) {
			Vector3f temp = a.copy();
			temp.scale(-1);
			temp.add(foodPosition);
			//sorgt f�r Kollision mit Essen aus anderen K�sten
			temp.x = ((temp.x+0.5f)%1+1)%1-0.5f;
			temp.y = ((temp.y+0.5f)%1+1)%1-0.5f;
			temp.z = ((temp.z+0.5f)%1+1)%1-0.5f;
			//gibt Distanz zwischen Kopf und essen zur�ck
			return temp.getLength();
		}
}
