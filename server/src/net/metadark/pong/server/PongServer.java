package net.metadark.pong.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class PongServer implements Runnable, BroadcastInterface {
	
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
			new ClientConnection(this, clientSocket);
		}
	}
	
	public void addUnmatchedClient(ClientConnection client) {
		if (unmatchedClient != null) {
			new PongGame(this, unmatchedClient, client);
			unmatchedClient = null;
		} else {
			unmatchedClient = client;
		}
	}

	/**
	 * Handle messages from client
	 */

	@Override
	public void match(ClientConnection client) {
		addUnmatchedClient(client);
	}
	
	@Override
	public void acceptGame(ClientConnection client) {}
	
	@Override
	public void resetBall(float x, float y, float xVelocity, float yVelocity) {}
	
	@Override
	public void updateScore() {}
	
	@Override
	public void moveUp(ClientConnection client, float y, boolean toggle) {}

	@Override
	public void moveDown(ClientConnection client, float y, boolean toggle) {}

	@Override
	public void close(ClientConnection client) {
		if (unmatchedClient == client) {
			unmatchedClient = null;
		}
	}
	
}
