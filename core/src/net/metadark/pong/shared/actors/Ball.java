package net.metadark.pong.shared.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	
	private Sound bounce;
	private Random generator = new Random();
	
	private Camera camera;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	
	private float velocityX;
	private float velocityY;
	
	public Ball(Camera camera, Paddle leftPaddle, Paddle rightPaddle, Sound bounce) {
		this.camera = camera;
		this.leftPaddle = leftPaddle;
		this.rightPaddle = rightPaddle;
		
		this.bounce = bounce;
		
		center();
		width = 10;
		height = 10;
		velocityX = -1;
		velocityY = 0;
	}
	
	public void update() {
		updatePosition();
		checkCollisions();
	}
	
	/**
	 * Center the ball on the viewport
	 */
	private void center() {
		x = (camera.viewportWidth - width) / 2;
		y = generator.nextFloat() * camera.viewportHeight;
	}
	
	/**
	 * Move the ball to it's appropriate position
	 */ 
	private void updatePosition() {
		x += velocityX * 400 * Gdx.graphics.getDeltaTime();
		y += velocityY * 400 * Gdx.graphics.getDeltaTime();
	}
	
	/**
	 * Handle ball collisions
	 */
	private void checkCollisions() {
		
		// Handle collision with paddles
		if (overlaps(leftPaddle)) {
			x = 2 * (leftPaddle.x + leftPaddle.width) - x;
			velocityX = Math.abs(velocityX);
			velocityY = ((y + height / 2) - (leftPaddle.y + leftPaddle.height / 2)) / (leftPaddle.height / 2);
			bounce.play(0.3f);
		} else if (overlaps(rightPaddle)) {
			x = 2 * (rightPaddle.x - width) - x;
			velocityX = -Math.abs(velocityX);
			velocityY = ((y + height / 2) - (rightPaddle.y + rightPaddle.height / 2)) / (rightPaddle.height / 2);
			bounce.play(0.3f);
		}
		
		// Center ball after passing through left and right
		// TODO: update score board when
		if (x < 0 || x > camera.viewportWidth - width) {
			center();
			velocityX = -velocityX;
		}
		
		// Bounce the ball of the top and bottom
		if (y < 0) {
			y = -y;
			velocityY = -velocityY;
		} else if (y > camera.viewportHeight - height) {
			y = 2 * (camera.viewportHeight - height) - y;
			velocityY = -velocityY;
		}
	}
	
}
