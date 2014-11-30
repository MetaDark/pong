package net.metadark.pong.client;

import net.metadark.pong.client.screens.MainScreen;

import com.badlogic.gdx.Game;

public class Pong extends Game {

	@Override
	public void create () {
		setScreen(new MainScreen(this));
	}

}
