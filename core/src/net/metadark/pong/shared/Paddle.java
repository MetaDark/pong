package net.metadark.pong.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

public class Paddle extends Rectangle {

	private static final long serialVersionUID = 1L;

	private Camera camera;
	
	private float speed;
	
	private boolean moveUp;
	private boolean moveDown;
	private boolean moveLeft;
	private boolean moveRight;
	
	public enum Side {
		LEFT,
		RIGHT
	}
	
	public Paddle(Camera camera, Side side) {
		this.camera = camera;
		
		speed = 1;
		width = 10;
		height = camera.viewportHeight / 12;
		
		y = (camera.viewportHeight - height) / 2;
		if (side == Side.LEFT) {
			x = camera.viewportWidth / 8;
		} else {
			x = camera.viewportWidth - width - (camera.viewportWidth / 8);
		}
	}
	
	/**
	 * Toggle moving the paddle up
	 * TODO: Send packet to server
	 * 
	 * @param t toggle
	 */
	public void moveUp(boolean toggle) {
		moveUp = toggle;
	}
	
	/**
	 * Toggle moving the paddle down
	 * TODO: Send packet to server
	 * 
	 * @param t
	 */
	public void moveDown(boolean toggle) {
		moveDown = toggle;
	}
	
	public void moveLeft(boolean toggle) {
		moveLeft = toggle;
	}
	
	public void moveRight(boolean toggle) {
		moveRight = toggle;
	}
	
	public void update() {
		updatePosition();
	}
	
	public void pathIntersects(float x1, float x2, float y1, float y2) {
		
	}
	
	/**
	 * Move the paddle to it's appropriate position
	 */
	private void updatePosition() {
		if (moveUp) {
			y += speed * 600 * Gdx.graphics.getDeltaTime();
		}
		
		if (moveDown) {
			y -= speed * 600 * Gdx.graphics.getDeltaTime();
		}
		
		if (moveLeft) {
			x -= speed * 600 * Gdx.graphics.getDeltaTime();
		}
		
		if (moveRight) {
			x += speed * 600 * Gdx.graphics.getDeltaTime();
		}
		
		// Bound the paddle to the camera viewport
		if (y < 0) {
			y = 0;
		} else if (y > camera.viewportHeight - height) {
			y = camera.viewportHeight - height;
		}
	}
	
}
