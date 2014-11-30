package net.metadark.pong.server;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class PongServer extends Thread {
	
	/**
	 * The socket used to accept new clients
	 */
	private ServerSocket socket;

	/**
	 * A List of unmatched clients
	 */
	private ArrayList<ClientConnection> clients = new ArrayList<ClientConnection>();

	/**
	 * Create a new Pong server on a specific port
	 * @param port
	 */
	public PongServer(int port) {
		ServerSocketHints socketHint = new ServerSocketHints();
		socketHint.acceptTimeout = 0;
		
		// Create the server socket
		socket = Gdx.net.newServerSocket(Protocol.TCP, port, socketHint);
		
		// Start listening for new clients
		start();
	}

	/**
	 * Wait to clients to connect to the server
	 */
	@Override
	public void run() {
		while (true) {
			Socket clientSocket = socket.accept(null);
			System.out.println("Client connected: " + clientSocket.getRemoteAddress());

			ClientConnection clientConnection = new ClientConnection(this, clientSocket);
			clients.add(clientConnection);
		}
	}

	/**
	 * Broadcast a paddle down message to all players
	 * @param origin The client that moved
	 * @param toggle Start/stop movement
	 */
	public void moveUp(ClientConnection origin, boolean toggle) {
		for (ClientConnection client : clients) {
			if (client != origin) {
				client.moveUp(toggle);
			}
		}
	}

	/**
	 * Broadcast a paddle up message to all players
	 * @param origin The client that moved
	 * @param toggle Start/stop movement
	 */
	public void moveDown(ClientConnection origin, boolean toggle) {
		for (ClientConnection client : clients) {
			if (client != origin) {
				client.moveDown(toggle);
			}
		}
	}

	/**
	 * Remove a client from the connection list
	 * @param clientConnection client to remove
	 */
	public void closeConnection(ClientConnection clientConnection) {
		clients.remove(clientConnection);
	}
}
