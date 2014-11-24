package net.metadark.pong.server;

import com.badlogic.gdx.ApplicationAdapter;

public class Pong extends ApplicationAdapter {

	@Override
	public void create () {
		new PongServer();
	}

}
