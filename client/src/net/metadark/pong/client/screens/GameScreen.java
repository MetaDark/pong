package net.metadark.pong.client.screens;

import net.metadark.pong.client.PongClient;
import net.metadark.pong.client.actors.ClientBall;
import net.metadark.pong.client.actors.ClientPaddle;
import net.metadark.pong.core.ClientInterface;
import net.metadark.pong.core.actors.Paddle.Side;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen extends PongScreen implements ClientInterface {
	
	private PongClient client;
	protected GameState gameState;
	
	private ClientBall ball;
	private ClientPaddle leftPaddle;
	private ClientPaddle rightPaddle;
	
	private String leftUsername;
	private String rightUsername;
	private int leftScore;
	private int rightScore;

	private OrthographicCamera camera;
	private Music music;
	private ShapeRenderer shapeRenderer;
	private BitmapFont titleFont;
	private SpriteBatch spriteBatch;
	
	public GameScreen(Game game, PongClient client, String leftUsername, String rightUsername) {
		super(game);
		
		this.client = client;
		this.leftUsername = leftUsername;
		this.rightUsername = rightUsername;
		
		// Set this object as the client interface and accept the game
		client.setClientInterface(this);
		
		// Define the game state
		gameState = GameState.GAME;
	}
	
	/**
	 * Initialization
	 */
	@Override
	public void show() {
		
		// Setup the game camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Load and play music
		music = Gdx.audio.newMusic(Gdx.files.internal("song.ogg"));
		music.setLooping(true);
		music.setVolume(0.3f);
		music.play();
		
		// Setup the shape render and the actors
		shapeRenderer = new ShapeRenderer();
		leftPaddle = new ClientPaddle(camera, Side.LEFT);
		rightPaddle = new ClientPaddle(camera, Side.RIGHT);
		ball = new ClientBall(camera, leftPaddle, rightPaddle);

		// Bind keyboard events
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				return GameScreen.this.keyDown(keycode);
			}
			
			@Override
			public boolean keyUp(int keycode) {
				return GameScreen.this.keyUp(keycode);
			}
		});
		
		renderFonts();
	    
	    // Create sprite batch
	    spriteBatch = new SpriteBatch();
	    
	    // Accept a new game
	    client.acceptGame();
	}
	
	
	/**
	 * Render loop
	 */
	@Override
	public void render(float delta) {
		
		// Reset display
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Draw usernames and scores
		spriteBatch.begin();
		float height = camera.viewportHeight - 10;
		titleFont.draw(spriteBatch, leftUsername, 10, height);
		titleFont.draw(spriteBatch, Integer.toString(leftScore), camera.viewportWidth / 2 - 30, height);
		titleFont.draw(spriteBatch, rightUsername, camera.viewportWidth / 2 + 10, height);
		titleFont.draw(spriteBatch, Integer.toString(rightScore), camera.viewportWidth - 30, height);
		spriteBatch.end();

		// Start drawing shapes
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);

		// Draw separator
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
		for (int i = 0; i < 24; i++) {
			shapeRenderer.rect((camera.viewportWidth - 10) / 2 + 2.5f, i * 20, 5, 10);
		}

		// Draw ball and paddles
		ball.render(shapeRenderer, delta);
		leftPaddle.render(shapeRenderer, delta);
		rightPaddle.render(shapeRenderer, delta);

		// Stop drawing shapes
		shapeRenderer.end();
		
		// Handle game state transitions
		switch (gameState) {
		case GAME:
			break;
		case LOBBY:
			game.setScreen(new LobbyScreen(game, client));
			break;
		case MAIN:
			game.setScreen(new MainScreen(game));
			break;
		}

	}
	
	/**
	 * Clean up
	 */
	@Override
	public void hide() {
		music.stop();
		music.dispose();
		shapeRenderer.dispose();
		titleFont.dispose();
		spriteBatch.dispose();
	}
	
	private void renderFonts() {
		FileHandle fontFile = Gdx.files.internal("LogoCraft.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    titleFont = generator.generateFont(parameter);
	    generator.dispose();
	}

	/**
	 * Handle keyboard events
	 */
	
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			client.moveUp(leftPaddle.y, true);
			leftPaddle.moveUp(true);
			break;
		case Keys.DOWN:
			client.moveDown(leftPaddle.y, true);
			leftPaddle.moveDown(true);
			break;
		}
		
		return true;
	}
	
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			client.moveUp(leftPaddle.y, false);
			leftPaddle.moveUp(false);
			break;
		case Keys.DOWN:
			client.moveDown(leftPaddle.y, false);
			leftPaddle.moveDown(false);
			break;
		}
		
		return true;
	}
	
	/**
	 * Handle messages from client
	 */
	
	@Override
	public void requestGame(String username) {}
	
	@Override
	public void resetBall(float x, float y, float xVelocity, float yVelocity) {
		ball.reset(x, y, xVelocity, yVelocity);
	}
	
	@Override
	public void updateScore(int leftScore, int rightScore) {
		this.leftScore = leftScore;
		this.rightScore = rightScore;
	}
	
	@Override
	public void moveUp(float y, boolean toggle) {
		rightPaddle.y = y;
		rightPaddle.moveUp(toggle);
	}
	
	@Override
	public void moveDown(float y, boolean toggle) {
		rightPaddle.y = y;
		rightPaddle.moveDown(toggle);
	}

	@Override
	public void close() {
		gameState = GameState.MAIN;
	}

	@Override
	public void quitGame() {
		gameState = GameState.LOBBY;
	}

}
