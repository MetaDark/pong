package net.metadark.pong.client.screens;

import static com.badlogic.gdx.scenes.scene2d.utils.Align.*;

import net.metadark.pong.client.PongClient;
import net.metadark.pong.core.ClientInterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LobbyScreen extends PongScreen implements ClientInterface {
	
	Stage stage;
	PongClient client;
	
	protected GameState gameState;
	private String opponentUsername;
	
	public LobbyScreen(Game game, String host, String port, String username) {
		super(game);
		this.client = new PongClient(this, host, Integer.parseInt(port), username);
		this.gameState = GameState.LOBBY;
	}
	
	public LobbyScreen(Game game, PongClient client) {
		super(game);
		this.client = client;
		this.gameState = GameState.LOBBY;
		client.setClientInterface(this);
	}
	
	/**
	 * Initialization
	 */
	
	@Override
	public void show() {
		
		client.match();
		
		// Setup the stage
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		// Load the skin file
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Add waiting label to stage
		Label waitingLabel = new Label("Waiting for another player...", skin);
		waitingLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, center);
		stage.addActor(waitingLabel);
		
	}

	/**
	 * Render loop
	 * @param delta milliseconds since last render
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		
		// Handle game state transitions
		switch (gameState) {
		case LOBBY:
			break;
		case GAME:
			game.setScreen(new GameScreen(game, client, client.getUsername(), opponentUsername));
			break;
		case MAIN:
			game.setScreen(new MainScreen(game));
			break;
		}
	}
	
	@Override
	public void hide() {
		stage.dispose();
	}

	/**
	 * Handle messages from server
	 */
	
	@Override
	public void requestGame(String username) {
		gameState = GameState.GAME;
		opponentUsername = username;
	}
	
	@Override
	public void resetBall(float x, float y, float xVelocity, float yVelocity) {}
	
	@Override
	public void updateScore(int leftScore, int rightScore) {}
	
	@Override
	public void moveUp(float y, boolean toggle) {}
	
	@Override
	public void moveDown(float y, boolean toggle) {}

	@Override
	public void quitGame() {}

	@Override
	public void close() {
		gameState = GameState.MAIN;
	}

}
