package net.metadark.pong.client.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import net.metadark.pong.core.actors.Ball;
import net.metadark.pong.core.actors.Paddle;

public class ClientBall extends Ball {

	private static final long serialVersionUID = 1L;

	public ClientBall(Camera camera, Paddle leftPaddle, Paddle rightPaddle) {
		super(camera, leftPaddle, rightPaddle);
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
