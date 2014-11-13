package net.metadark.pong;

import net.metadark.pong.Paddle.Side;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Pong extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private Music music;
	private ShapeRenderer shapeRenderer;
	
	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	
	@Override
	public void create () {
		
		// Setup the game camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Load and play music
		music = Gdx.audio.newMusic(Gdx.files.internal("song.ogg"));
		music.setLooping(true);
		music.setVolume(0.3f);
		music.play();
		
		// Setup the shape render and the objects
		shapeRenderer = new ShapeRenderer();
		leftPaddle = new Paddle(camera, Side.LEFT);
		rightPaddle = new Paddle(camera, Side.RIGHT);
		ball = new Ball(camera);
		
	}

	@Override
	public void render () {
		
		// Handle keyboard input
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			leftPaddle.moveUp();
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			leftPaddle.moveDown();
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			rightPaddle.moveUp();
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			rightPaddle.moveDown();
		}
		
		// TODO: Handle collisions
		if (ball.overlaps(leftPaddle) || ball.overlaps(rightPaddle)) {
			ball.velocityX = -ball.velocityX;
			ball.velocityY = 1;
		}
		
		// Reset display
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		// Draw shapes
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		
		// Draw game separator
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
		shapeRenderer.rect((camera.viewportWidth - 10) / 2, 0, 10, camera.viewportHeight);
		
		// Draw ball and paddles
		ball.render(shapeRenderer);
		leftPaddle.render(shapeRenderer);
		rightPaddle.render(shapeRenderer);
		
		shapeRenderer.end();
		
	}
	
}
