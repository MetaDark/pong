package net.metadark.pong.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.metadark.pong.core.ClientInterface;
import net.metadark.pong.core.ServerInterface.ServerEvent;

import com.badlogic.gdx.net.Socket;

public class ClientConnection extends Thread implements ClientInterface {
	
	private BroadcastInterface server;
	
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	
	private String username;
	
	private volatile boolean running;
	
	public ClientConnection(BroadcastInterface server, Socket socket) {
		this.socket = socket;
		setServerInterface(server);
		
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		running = true;
		
		start();
	}
	
	/**
	 * Delegate messages from client to the server interface
	 */
	@Override
	public void run() {
		
		while (running) {
			try {
				ServerEvent type = ServerEvent.values()[input.readInt()];
				switch (type) {
				case LOGIN:
					username = input.readUTF();
					break;
				case MATCH:
					server.match(this);
					break;
				case ACCEPT_GAME:
					server.acceptGame(this);
					break;
				case MOVE_UP:
					server.moveUp(this, input.readFloat(), input.readBoolean());
					break;
				case MOVE_DOWN:
					server.moveDown(this, input.readFloat(), input.readBoolean());
					break;
				case CLOSE:
					server.close(this);
				default:
					System.out.println("Bad message");
					break;
				}
			} catch (IOException e) {
				close();
			}
		}
		
		System.out.println("Client connection closed: " + socket.getRemoteAddress());
	}
	
	/**
	 * Methods to send messages to client
	 */
	
	@Override
	public void close() {
		if (!running) return;
		
		running = false;
		server.close(this);
		
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void requestGame(String username) {
		try {
			output.writeInt(ClientEvent.REQUEST_GAME.ordinal());
			output.writeUTF(username);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void resetBall(float x, float y, float xVelocity, float yVelocity) {
		try {
			output.writeInt(ClientEvent.RESET_BALL.ordinal());
			output.writeFloat(x);
			output.writeFloat(y);
			output.writeFloat(xVelocity);
			output.writeFloat(yVelocity);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void updateScore(int leftScore, int rightScore) {
		try {
			output.writeInt(ClientEvent.UPDATE_SCORE.ordinal());
			output.writeInt(leftScore);
			output.writeInt(rightScore);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void moveUp(float y, boolean toggle) {
		try {
			output.writeInt(ClientEvent.MOVE_UP.ordinal());
			output.writeFloat(y);
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void moveDown(float y, boolean toggle) {
		try {
			output.writeInt(ClientEvent.MOVE_DOWN.ordinal());
			output.writeFloat(y);
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}

	@Override
	public void quitGame() {
		try {
			output.writeInt(ClientEvent.QUIT_GAME.ordinal());
		} catch (IOException e) {
			close();
		}
	}

	/**
	 * Setters and getters
	 */
	
	public void setServerInterface(BroadcastInterface server) {
		this.server = server;
	}
	
	public String getUsername() {
		return username;
	}
	
}
