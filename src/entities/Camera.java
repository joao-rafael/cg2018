package entities;

/**
 * IMPORTAÇÕES DA LWJGL
 */
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private float distanceFromPlayer = 10;
	private float angleAround = 0;
	
	private Vector3f position = new Vector3f(0,5,0);
	private float pitch = 10;
	private float yaw ;
	private float roll;
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
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
		/*calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 100 - (player.getRoty() + angleAround);*/
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=0.2f;
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
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRoty() + angleAround;
		float offsetX = (float)  (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float)  (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance;
	}
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAround -= angleChange;
		}
	}
}
