package net.metadark.pong.server;

import com.badlogic.gdx.ApplicationAdapter;

public class Pong extends ApplicationAdapter {
	
	private int port;
	
	public Pong(int port) {
		this.port = port;
	}

	@Override
	public void create() {
		new PongServer(port);
	}
	
}
