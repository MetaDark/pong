package net.metadark.pong.client;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import net.metadark.pong.shared.Paddle;

public class ClientPaddle extends Paddle {

	private static final long serialVersionUID = 1L;
	
	private PongClient client;

	public ClientPaddle(PongClient client, Camera camera, Side side) {
		super(camera, side);
		this.client = client;
	}
	
	public void render(ShapeRenderer shapeRenderer) {
		update();
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(x, y, width, height);
	}
	
	public void moveUp(boolean toggle) {
		moveUpPure(toggle);
		client.moveUp(toggle);
	}
	
	public void moveDown(boolean toggle) {
		moveDownPure(toggle);
		client.moveDown(toggle);
	}

}
