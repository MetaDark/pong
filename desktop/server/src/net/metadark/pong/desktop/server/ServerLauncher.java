package net.metadark.pong.desktop.server;

import net.metadark.pong.server.Pong;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

class ServerLauncher {

	public static void main(String[] args) {
		new HeadlessApplication(new Pong());
	}

}
