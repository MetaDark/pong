package net.metadark.pong.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class PongServer implements Runnable, ServerInterface {
	
	/**
	 * The socket used to accept new clients
	 */
	private ServerSocket socket;

	/**
	 * A buffer for unmatched clients
	 */
	private ClientConnection unmatchedClient;

	/**
	 * Create a new Pong server on a specific port
	 * @param port
	 */
	public PongServer(int port) {
		ServerSocketHints socketHint = new ServerSocketHints();
		socketHint.acceptTimeout = 0;
		
		// Create the server socket
		socket = Gdx.net.newServerSocket(Protocol.TCP, port, socketHint);
		System.out.println("Running pong server on port " + port);
		
		// Start listening for new clients
		run();
	}

	/**
	 * Wait for clients to connect to the server
	 */
	@Override
	public void run() {
		while (true) {
			Socket clientSocket = socket.accept(null);
			System.out.println("Client connected: " + clientSocket.getRemoteAddress());
			addClient(new ClientConnection(this, clientSocket));
		}
	}
	
	private void addClient(ClientConnection client) {
		if (unmatchedClient != null) {
			new PongGame(this, unmatchedClient, client);
			unmatchedClient = null;
		} else {
			unmatchedClient = client;
		}
	}

	@Override
	public void moveUp(ClientConnection client, boolean toggle) {}

	@Override
	public void moveDown(ClientConnection client, boolean toggle) {}

	@Override
	public void close(ClientConnection client) {
		if (unmatchedClient == client) {
			unmatchedClient = null;
		}
	}
	
}
