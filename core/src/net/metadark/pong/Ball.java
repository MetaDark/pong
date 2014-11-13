package net.metadark.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	private Camera camera;
	
	public float velocityX;
	public float velocityY;
	
	public Ball(Camera camera) {
		this.camera = camera;
		reset();
		
		width = 10;
		height = 10;
		velocityX = 1;
		velocityY = 0;
	}
	
	public void reset() {
		x = (camera.viewportWidth - width) / 2;
		y = (camera.viewportHeight - height) / 2;
	}
	
	public void render(ShapeRenderer shapeRenderer) {
		x += velocityX * 400 * Gdx.graphics.getDeltaTime();
		y += velocityY * 400 * Gdx.graphics.getDeltaTime();
		
		if (x < 0 || x > camera.viewportWidth - width) {
			reset();
		}
		
		if (y < 0 || y > camera.viewportHeight - height) {
			velocityY = -velocityY;
		}
		
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(x, y, width, height);
	}
	
}
