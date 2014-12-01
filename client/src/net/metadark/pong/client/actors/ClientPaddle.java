package net.metadark.pong.client.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import net.metadark.pong.core.actors.Paddle;

public class ClientPaddle extends Paddle {

	private static final long serialVersionUID = 1L;

	public ClientPaddle(Camera camera, Side side) {
		super(camera, side);
	}
	
	/**
	 * Draw the paddle
	 * @param shapeRenderer
	 */
	public void render(ShapeRenderer shapeRenderer, float delta) {
		update(delta);
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(x, y, width, height);
	}

}
