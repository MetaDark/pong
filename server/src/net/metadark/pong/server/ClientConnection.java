package net.metadark.pong.server;

import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.net.Socket;

public class ClientConnection extends Thread {
	
	Socket client;
	
	public ClientConnection(Socket client) {
		this.client = client;
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
