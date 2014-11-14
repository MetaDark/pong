package net.metadark.pong.client;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import net.metadark.pong.shared.Ball;
import net.metadark.pong.shared.Paddle;

public class ClientBall extends Ball {

	private static final long serialVersionUID = 1L;

	public ClientBall(Camera camera, Paddle leftPaddle, Paddle rightPaddle, Sound bounce) {
		super(camera, leftPaddle, rightPaddle, bounce);
	}
	
	/**
	 * Draw the ball
	 * @param shapeRenderer
	 */
	public void render(ShapeRenderer shapeRenderer) {
		update();
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(x, y, width, height);
	}

}
