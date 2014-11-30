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
	long startTime;
	
	public LobbyScreen(Game game, String host, String port, String username) {
		super(game);
		
		// Setup the stage
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		// Load the skin file
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Add waiting label to stage
		Label waitingLabel = new Label("Waiting for another player...", skin);
		waitingLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, center);
		stage.addActor(waitingLabel);
		
		// Create a new pong client
		client = new PongClient(this, host, Integer.parseInt(port), username);
		
		startTime = System.currentTimeMillis();
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
		
		if (System.currentTimeMillis() - startTime > 2000) {
			game.setScreen(new GameScreen(game, client));
		}
	}

	/**
	 * Handle messages from server
	 */
	
	@Override
	public void moveUp(boolean toggle) {
		System.out.println(toggle);
	}
	
	@Override
	public void moveDown(boolean toggle) {}

}
