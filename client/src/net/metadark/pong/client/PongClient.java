package net.metadark.pong.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.metadark.pong.core.ClientInterface;
import net.metadark.pong.core.ServerInterface;
import net.metadark.pong.core.ClientInterface.ClientEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class PongClient extends Thread implements ServerInterface {
	
	private ClientInterface client;
	private DataOutputStream output;
	private DataInputStream input;
	private String username;
	
	private volatile boolean running;

	public PongClient(ClientInterface client, String host, int port, String username) {
		this.username = username;
		setClientInterface(client);
		
		SocketHints socketHint = new SocketHints();
		socketHint.connectTimeout = 1000;

		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, socketHint);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		running = true;
		
		login(username);
		start();
	}

	/**
	 * Parses and delegates server messages to the client interface
	 */
	@Override
	public void run() {
		while (running) {
			try {
				int val = input.readInt();
				ClientEvent type = ClientEvent.values()[val];
				switch (type) {
				case REQUEST_GAME:
					client.requestGame(input.readUTF());
					break;
				case RESET_BALL:
					client.resetBall(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat());
					break;
				case UPDATE_SCORE:
					client.updateScore(input.readInt(), input.readInt());
					break;
				case MOVE_UP:
					client.moveUp(input.readBoolean());
					break;
				case MOVE_DOWN:
					client.moveDown(input.readBoolean());
					break;
				case QUIT_GAME:
					client.quitGame();
					break;
				case CLOSE:
					client.close();
					break;
				default:
					System.out.println("Bad message");
					break;
				}
			} catch (IOException e) {
				close();
			}
		}
	}

	/**
	 * Methods to send messages to the server
	 */
	
	@Override
	public void login(String username) {
		try {
			output.writeInt(ServerEvent.LOGIN.ordinal());
			output.writeUTF(username);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void match() {
		try {
			output.writeInt(ServerEvent.MATCH.ordinal());
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void acceptGame() {
		try {
			output.writeInt(ServerEvent.ACCEPT_GAME.ordinal());
		} catch (IOException e) {
			close();
		}
	}

	@Override
	public void moveUp(boolean toggle) {
		try {
			output.writeInt(ServerEvent.MOVE_UP.ordinal());
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}

	@Override
	public void moveDown(boolean toggle) {
		try {
			output.writeInt(ServerEvent.MOVE_DOWN.ordinal());
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void close() {
		running = false;

		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setters and getters
	 */
	public String getUsername() {
		return username;
	}
	
	public void setClientInterface(ClientInterface client) {
		this.client = client;
	}
	
}
