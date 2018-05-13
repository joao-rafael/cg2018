package entities;

import org.lwjgl.util.vector.Vector3f;
/**
 * Classe para entidade de luz
 * 
 * cont�m vetores de 3 espa�os para coordenadas de posi��o
 * e um vector 3 para cores(RGB);
 * - as cores definir�o a intensidade da luz
 * 
 */
public class Light {
	private Vector3f position;
	private Vector3f colour;
	
	public Light(Vector3f position, Vector3f colour) {
		super();
		this.position = position;
		this.colour = colour;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	
}