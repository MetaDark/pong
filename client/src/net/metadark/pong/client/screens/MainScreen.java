package net.metadark.pong.client.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainScreen extends PongScreen {
	
	/**
	 * The default port to host on
	 */
	private static int DEFAULT_PORT = 5436;
	
	/**
	 * UI elements to display
	 */
	private Stage stage;
	private TextField nameInput;
	private TextField hostInput;
	private TextField portInput;
	private TextButton connectBtn;
	
	public MainScreen(Game game) {
		super(game);
	}
	
	/**
	 * Initialization
	 */
	@Override
	public void show() {
		
		// Setup the stage
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
	
		// Load the skin file
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Create the layout table
		Table table = new Table(skin);
		
		// Add host input row
		hostInput = new TextField("localhost", skin);
		table.add("Host: ").right();
		table.add(hostInput).size(300, 40).pad(5);
		table.row();
		
		// Add port input row
		portInput = new TextField(Integer.toString(DEFAULT_PORT), skin);
		table.add("Port: ").right();
		table.add(portInput).size(300, 40).pad(5);
		table.row();
		
		// Add username input row
		nameInput = new TextField("", skin);
		table.add("Username: ").right();
		table.add(nameInput).size(300, 40).pad(5);
		table.row();
		
		// Add connect button
		connectBtn = new TextButton("Connect", skin);
		connectBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				connectBtnClicked();
			}
		});
		
		table.add();
		table.add(connectBtn).size(300, 40).pad(5);
		
		// Center table and add to stage
		table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		stage.addActor(table);
		
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
	}
	
	/**
	 * Clean up
	 */
	@Override
	public void hide() {
		stage.dispose();
	}
	
	/**
	 * Connects to server when button is clicked
	 */
	private void connectBtnClicked() {

		LobbyScreen lobbyScreen = new LobbyScreen(game,
			hostInput.getText(),
			portInput.getText(),
			nameInput.getText()
		);
		
		game.setScreen(lobbyScreen);
	}

}
