package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity{
	
	private static final float SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP = 200;
	
	private static final float TERRAIN_HEIGHT = 0;

	private float upwardSpeed = 0;
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotx, float roty, float rotz, float scale) {
		super(model, position, rotx, roty, rotz, scale);
		// TODO Auto-generated constructor stub
	}

	public void move() {
		checkInput();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRoty())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRoty())));
		super.increasePosition(dx, 0, dz);
		upwardSpeed += GRAVITY + DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y< TERRAIN_HEIGHT) {
			upwardSpeed = 0;
			isInAir = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
	}
	
	public void jump() {
		if(!isInAir) {
			this.upwardSpeed = JUMP;
			isInAir = true;
		}
	}
	
	private void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_I)) {
				this.currentSpeed = SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_K)){
				this.currentSpeed = -SPEED;
		}else {
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_L)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_J)) {
			this.currentTurnSpeed = TURN_SPEED;
		}else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			jump();
		}
	}
	
}
