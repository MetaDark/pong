package net.metadark.pong.client;

import net.metadark.pong.shared.Paddle.Side;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Pong extends ApplicationAdapter {

	private Input inputProcessor;
	private OrthographicCamera camera;
	private Music music;
	private ShapeRenderer shapeRenderer;

	private ClientBall ball;
	private ClientPaddle leftPaddle;
	private ClientPaddle rightPaddle;

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

		Sound bounce = Gdx.audio.newSound(Gdx.files.internal("bounce.ogg"));

		// Start the client
		PongClient client = new PongClient(this);
		
		// Setup the shape render and the objects
		shapeRenderer = new ShapeRenderer();
		leftPaddle = new ClientPaddle(client, camera, Side.LEFT);
		rightPaddle = new ClientPaddle(client, camera, Side.RIGHT);
		ball = new ClientBall(camera, leftPaddle, rightPaddle, bounce);

		// Handle player input
		inputProcessor = new Input(this);
		Gdx.input.setInputProcessor(inputProcessor);

	}

	@Override
	public void render () {

		// Reset display
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		camera.update();

		// Start drawing shapes
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);

		// Draw separator
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
		for (int i = 0; i < 24; i++) {
			shapeRenderer.rect((camera.viewportWidth - 10) / 2, i * 20, 5, 10);
		}

		// Draw ball and paddles
		ball.render(shapeRenderer);
		leftPaddle.render(shapeRenderer);
		rightPaddle.render(shapeRenderer);

		// Stop drawing shapes
		shapeRenderer.end();

	}
	
	public ClientBall getBall() {
		return ball;
	}

	public ClientPaddle getLeftPaddle() {
		return leftPaddle;
	}

	public ClientPaddle getRightPaddle() {
		return rightPaddle;
	}

}
