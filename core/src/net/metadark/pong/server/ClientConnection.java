package net.metadark.pong.server;

import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.net.Socket;


public class ClientConnection extends Thread {
	
	PongServer server;
	Socket client;
	
	public ClientConnection(PongServer server, Socket client) {
		this.server = server;
		this.client = client;
		this.start();
	}
	
	@Override
	public void run() {
		DataOutputStream output = new DataOutputStream(client.getOutputStream());
		try {
			output.writeDouble(Math.PI);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
