package net.metadark.pong.server;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class PongServer extends Thread {
	
	private static int DEFAULT_PORT = 5436;
	private int port;
	
	private ArrayList<ClientConnection> clients = new ArrayList<ClientConnection>();

	public PongServer() {
		this(DEFAULT_PORT);
	}

	public PongServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocketHints socketHint = new ServerSocketHints();
		socketHint.acceptTimeout = 0;
		
		ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, port, socketHint);
		
		while (true) {
			Socket clientSocket = serverSocket.accept(null);
			System.out.println("Client connected: " + clientSocket.getRemoteAddress());
			
			ClientConnection clientConnection = new ClientConnection(clientSocket);
			clientConnection.start();
			
			clients.add(clientConnection);
		}
	}
}
