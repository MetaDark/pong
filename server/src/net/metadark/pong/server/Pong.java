package net.metadark.pong.server;

import com.badlogic.gdx.ApplicationAdapter;

public class Pong extends ApplicationAdapter {

	@Override
	public void create () {
		PongServer server = new PongServer();
		server.start();
	}

}
