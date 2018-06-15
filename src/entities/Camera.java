package entities;

/**
 * IMPORTAÇÕES DA LWJGL
 */
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0,1,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {
	//construtor - TO DO	
	}

	public Vector3f getPosition() {
		return position;
	}
	
	/**
	 * Movimentação da câmera
	 * recebe inputs do teclado e movimenta o processo de viweing
	 * 
	 */
	public void move() {
		int rotate = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			position.y+= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			position.y-= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z+= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			rotate +=rotate;
			setYaw(rotate);
		}
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	
}
