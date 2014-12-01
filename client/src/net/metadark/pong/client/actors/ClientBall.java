package net.metadark.pong.client.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import net.metadark.pong.core.actors.Ball;
import net.metadark.pong.core.actors.Paddle;

public class ClientBall extends Ball {

	private static final long serialVersionUID = 1L;
	private Sound bounce;
	private boolean visible;

	public ClientBall(Camera camera, Paddle leftPaddle, Paddle rightPaddle) {
		super(camera, leftPaddle, rightPaddle);
		
		// Load bounce sound
		bounce = Gdx.audio.newSound(Gdx.files.internal("bounce.ogg"));
	}
	
	/**
	 * Draw the ball
	 * @param shapeRenderer
	 */
	public void render(ShapeRenderer shapeRenderer, float delta) {
		if (!visible) return;
		
		update(delta);
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(x, y, width, height);
	}
	
	@Override
	public void reset(float x, float y, float xVelocity, float yVelocity) {
		super.reset(x, y, xVelocity, yVelocity);
		visible = true;
	}
	
	@Override
	protected void onCollision() {
		bounce.play(0.3f);
	}

}
