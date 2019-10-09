package graphics.gui;

import java.util.ArrayList;
import java.util.List;

import graphics.Matrix3f;
import graphics.Vector3f;
import graphics.guiRenderer.GuiShader;

/**
 * Die Basisklasse f�r Komponenten, die andere Komponenten enthalten k�nnen.<br>
 * Die enthaltenen Komponenten werden von dieser Klasse automatisch positioniert, gerendert und am Ende gel�scht, sodass sie als eine Art Einheit betrachtet werden k�nnen - ein Aufruf der entsprechenden Methode dieser Klasse reicht bereits aus.
 * <br><br>
 * Andere Klassen k�nnen diese Klasse erweitern, um auch als Container f�r andere Komponenten dienen zu k�nnen. Dazu k�nnen sie die Methoden dieser Klasse �berschrieben, sollten sie dabei aber auch immer mit <code>super.xyz()</code> wieder aufrufen, damit keine Funktionalit�t verloren geht.
 * 
 * @author Ben
 */
public class ContainerComponent extends GuiComponent {
	
	private List<GuiComponent> childComponents;
	
	/**
	 * Erstellt einen neuen, leeren ContainerComponent
	 * 
	 * @param width Breite in Pixeln
	 * @param height H�he in Pixeln
	 */
	public ContainerComponent(int width, int height) {
		super(width,height);
		childComponents = new ArrayList<GuiComponent>();
	}
	
	/**
	 * F�gt eine Komponente als Kindelement hinzu
	 * @param component die Komponente
	 */
	public void addComponent(GuiComponent component) {
		childComponents.add(component);
		refreshChildPositions();
	}
	
	/**
	 * Updated die Positionen aller Kindelemente. Sollte immer dann aufgerufen werden, wenn die Gr��e oder Position dieses Elements oder Gr��e, Position oder Offset einer Kindkomponente ge�ndert wurden.
	 */
	protected void refreshChildPositions() {
		Matrix3f baseTransformation = super.getTotalTransform();
		int innerWidth = super.getWidth();
		int innerHeight = super.getHeight();
		for (GuiComponent childComponent:childComponents) {
			Matrix3f transform = baseTransformation.copy();
			Vector3f positionOffset = new Vector3f(0,0,1);
			if (childComponent.getPosition()==POSITION_CENTER) {
				positionOffset.x = (innerWidth-childComponent.getWidth())/2;
				positionOffset.y = (innerHeight-childComponent.getHeight())/2;
			}else if (childComponent.getPosition()==POSITION_CORNER_TOPLEFT){
				positionOffset.x = childComponent.getOffsetX();
				positionOffset.y = childComponent.getOffsetY();
			}else if (childComponent.getPosition()==POSITION_CORNER_TOPRIGHT) {
				positionOffset.x = innerWidth-childComponent.getWidth()-childComponent.getOffsetX();
				positionOffset.y = childComponent.getOffsetY();
			}else if (childComponent.getPosition()==POSITION_CORNER_BOTTOMRIGHT) {
				positionOffset.x = innerWidth-childComponent.getWidth()-childComponent.getOffsetX();
				positionOffset.y = innerHeight-childComponent.getHeight()-childComponent.getOffsetY();
			}else if (childComponent.getPosition()==POSITION_CORNER_BOTTOMLEFT){
				positionOffset.x = childComponent.getOffsetX();
				positionOffset.y = innerHeight-childComponent.getHeight()-childComponent.getOffsetY();
			}
			positionOffset.apply(baseTransformation);
			transform.m20 = positionOffset.x;
			transform.m21 = positionOffset.y;
			childComponent.setTransform(transform);
		}
	}
	
	/**
	 * Passt bei Gr��en�nderungen die Positionen der Kindelemente an. Wird von der Klasse GuiComponent aufgerufen, wenn sich die Gr��e des Elements �ndert.<br>
	 * Diese Methode kann von erweiternden Klassen �berschrieben werden, um eigene Anpassungen an die neue Gr��e vorzunehmen, sollte aber stehts �ber <code>super.onSizeChange()</code> diese Methode auch ausf�hren.
	 */
	protected void onSizeChange() {
		refreshChildPositions();
	}
	
	/**
	 * Updated alle Kindelemente und nimmt gegebenenfalls eine Neupositionierung der Elemente vor.
	 * <br><br>
	 * Erweiternde Klassen k�nnen diese Methode �berschreiben um eigene Funktionalit�t hinzuzuf�gen, sollten sie dabei aber immer nochmal �ber <code>super.update()</code> aufrufen.
	 */
	public void update() {
		boolean wasChanged = false;
		for (GuiComponent childComponent:childComponents) {
			childComponent.update();
			if (childComponent.wasSizeChanged()) {
				wasChanged = true;
				childComponent.clearChangesBuffer();
			}
		}
		if (wasChanged) {
			refreshChildPositions();
		}
	}
	
	/**
	 * Rendert alle Kindelemente.
	 * <br><br>
	 * Erweiternde Klassen, die zus�tzlich noch etwas anderes wie z.B. einen Hintergrund rendern wollen, k�nnen dazu diese Methode �berschreiben, sollten sie aber dabei nochmal �ber <code>super.render(shader)</code> aufrufen, damit die Kindelemente auch gerendert werden.
	 */
	public void render(GuiShader shader) {
		for (GuiComponent childComponent:childComponents) {
			childComponent.render(shader);
		}
	}
	
	/**
	 * L�scht alle Kindelemente, um Ressourcen freizugeben.
	 * <br><br>
	 * Erweiternde Klassen, die ebenfalls Ressourcen freizugeben haben, k�nnen dazu diese Methode �berschreiben, sollten sie aber dabei nochmal �ber <code>super.destroy()</code> aufrufen.
	 */
	public void destroy() {
		for (GuiComponent childComponent:childComponents) {
			childComponent.destroy();
		}
	}
	
}
