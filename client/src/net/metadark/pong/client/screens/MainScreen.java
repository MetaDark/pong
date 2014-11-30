package net.metadark.pong.client.screens;

import static com.badlogic.gdx.scenes.scene2d.utils.Align.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainScreen extends GameScreen {
	
	private Stage stage;
	private TextField nameInput;
	private TextField hostInput;
	private TextField portInput;
	private TextButton connectBtn;
	
	public MainScreen(Game game) {
		super(game);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		nameInput = new TextField("Username", skin);
		nameInput.setSize(300, 50);
		nameInput.setPosition((Gdx.graphics.getWidth() - nameInput.getWidth()) / 2, 310);
		stage.addActor(nameInput);
		
		Label nameLabel = new Label("bob", skin);
		nameLabel.setAlignment(right);
		nameInput.setSize(300, 50);
		nameInput.setPosition((Gdx.graphics.getWidth() - nameInput.getWidth()) / 2, 310);
		
		hostInput = new TextField("localhost", skin);
		hostInput.setSize(300, 50);
		hostInput.setPosition((Gdx.graphics.getWidth() - hostInput.getWidth()) / 2, 240);
		stage.addActor(hostInput);
		
		portInput = new TextField("port", skin);
		portInput.setSize(300, 50);
		portInput.setPosition((Gdx.graphics.getWidth() - portInput.getWidth()) / 2, 170);
		stage.addActor(portInput);
		
		connectBtn = new TextButton("Connect", skin);
		connectBtn.setSize(300, 50);
		connectBtn.setPosition((Gdx.graphics.getWidth() - connectBtn.getWidth()) / 2, 100);
		
		connectBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				connectBtnClicked();
			}
		});
		
		stage.addActor(connectBtn);
		
	}
	
	private void connectBtnClicked() {
		System.out.println(nameInput.getText());
		System.out.println(hostInput.getText());
		System.out.println(portInput.getText());
		game.setScreen(new GameScreen(game));
	}

	@Override
	public void show() {
		
		// Create title font
//		FileHandle fontFile = Gdx.files.internal("LogoCraft.ttf");
//	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
//	    titleFont = generator.generateFont(null);
//	    generator.dispose();
	    
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

}
