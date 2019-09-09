package gamelogic;

import org.lwjgl.glfw.GLFW;

import graphics.Display;
import graphics.Matrix3f;
import graphics.Vector3f;

public class Snake {
	public Vector3f cameraPosition;
	public Matrix3f viewDirection;

	public Snake(){
	    cameraPosition = new Vector3f();
	    viewDirection = new Matrix3f();
	}
	public void update(Display display) {
		// dreht die Sichtmatrix je nach Tasteninput und l�dt sie in den Shader
					if (display.isKeyPressed(GLFW.GLFW_KEY_W) || display.isKeyPressed(GLFW.GLFW_KEY_UP) ) {
						viewDirection.rotate(-0.25f, 0, 0);
					}
					if (display.isKeyPressed(GLFW.GLFW_KEY_S) || display.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
						viewDirection.rotate(0.25f, 0, 0);
					}
					if (display.isKeyPressed(GLFW.GLFW_KEY_A) || display.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
						viewDirection.rotate(0, -0.25f, 0);
					}
					if (display.isKeyPressed(GLFW.GLFW_KEY_D) || display.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
						viewDirection.rotate(0, 0.25f, 0);
					}
					if (display.isKeyPressed(GLFW.GLFW_KEY_E)) {
						viewDirection.rotate(0, 0, 0.25f);
					}
					if (display.isKeyPressed(GLFW.GLFW_KEY_Q)) {
						viewDirection.rotate(0, 0, -0.25f);
					}
					Vector3f movement = new Vector3f (0,0,0.01f);
					movement.apply(viewDirection);
					cameraPosition.add(movement);
	}
}
