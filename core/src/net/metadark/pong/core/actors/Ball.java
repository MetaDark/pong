package net.metadark.pong.core.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Rectangle {
	
	public enum Side {
		DEFAULT,
		LEFT,
		RIGHT
	}
	
	private static final long serialVersionUID = 1L;
	
	private Camera camera;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	
	private float xVelocity;
	private float yVelocity;
	
	public Ball(Camera camera, Paddle leftPaddle, Paddle rightPaddle) {
		this.camera = camera;
		this.leftPaddle = leftPaddle;
		this.rightPaddle = rightPaddle;
		
		width = 10;
		height = 10;
	}
	
	public void update(float delta) {
		updatePosition(delta);
		checkCollisions();
	}
	
	/**
	 * Reset the ball position and velocity
	 */
	public void reset(float x, float y, float xVelocity, float yVelocity) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Move the ball to it's appropriate position
	 */ 
	private void updatePosition(float delta) {
		x += xVelocity * 400 * delta;
		y += yVelocity * 400 * delta;
	}
	
	/**
	 * Handle ball collisions
	 */
	private void checkCollisions() {
		
		// Handle collision with paddles
		if (overlaps(leftPaddle)) {
			x = 2 * (leftPaddle.x + leftPaddle.width) - x;
			xVelocity = Math.abs(xVelocity);
			yVelocity = ((y + height / 2) - (leftPaddle.y + leftPaddle.height / 2)) / (leftPaddle.height / 2);
		} else if (overlaps(rightPaddle)) {
			x = 2 * (rightPaddle.x - width) - x;
			xVelocity = -Math.abs(xVelocity);
			yVelocity = ((y + height / 2) - (rightPaddle.y + rightPaddle.height / 2)) / (rightPaddle.height / 2);
		}
		
		// Bounce the ball of the top and bottom
		if (y < 0) {
			y = -y;
			yVelocity = -yVelocity;
		} else if (y > camera.viewportHeight - height) {
			y = 2 * (camera.viewportHeight - height) - y;
			yVelocity = -yVelocity;
		}
	}
	
	/**
	 * Check if ball is past a "net"
	 */
	
	public Side getSide() {
		Side side = Side.DEFAULT;
		
		if (x < 0) {
			side = Side.LEFT;
		} else if(x > camera.viewportWidth - width) {
			side = Side.RIGHT;
		}
		
		return side;
	}
	
}
