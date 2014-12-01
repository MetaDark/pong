package net.metadark.pong.desktop.server;

import net.metadark.pong.server.Pong;
import net.metadark.pong.core.ServerInterface;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

class ServerLauncher {

	public static void main(String[] args) {
		int port = ServerInterface.DEFAULT_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		
		new HeadlessApplication(new Pong(port));
	}

}
