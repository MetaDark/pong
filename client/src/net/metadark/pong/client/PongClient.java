package net.metadark.pong.client;

import java.io.DataInputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class PongClient extends Thread {

	private static int DEFAULT_PORT = 5436;
	private String host;
	private int port;

	public PongClient() {
		this("localhost", DEFAULT_PORT);
	}
	
	public PongClient(String host) {
		this(host, DEFAULT_PORT);
	}

	public PongClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		SocketHints socketHint = new SocketHints();
		socketHint.connectTimeout = 1000;
		
		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, socketHint);

		while (true) {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			
			try {
				System.out.println(input.readDouble());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
