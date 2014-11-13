package net.metadark.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paddle extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	private Camera camera;
	
	public float speed;
	
	public enum Side {
		LEFT,
		RIGHT
	}
	
	public Paddle(Camera camera, Side side) {
		this.camera = camera;
		
		speed = 1;
		width = 10;
		height = camera.viewportHeight / 8;
		
		y = (camera.viewportHeight - height) / 2;
		if (side == Side.LEFT) {
			x = 0;
		} else {
			x = camera.viewportWidth - width;
		}
	}
	
	public void moveUp() {
		y += speed * 600 * Gdx.graphics.getDeltaTime();
		if (y > camera.viewportHeight - height) {
			y = camera.viewportHeight - height;
		}
	}
	
	public void moveDown() {
		y -= speed * 600 * Gdx.graphics.getDeltaTime();
		if (y < 0) {
			y = 0;
		}
	}

	public void render(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(x, y, width, height);
	}
	
}
