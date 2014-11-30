package net.metadark.pong.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.metadark.pong.core.ClientInterface;
import net.metadark.pong.server.ServerInterface.ServerEvent;

import com.badlogic.gdx.net.Socket;

public class ClientConnection extends Thread implements ClientInterface {
	
	private ServerInterface server;
	private DataOutputStream output;
	private DataInputStream input;
	
	private String username;
	
	private volatile boolean running;
	
	public ClientConnection(ServerInterface server, Socket socket) {
		setServerInterface(server);
		
		this.output = new DataOutputStream(socket.getOutputStream());
		this.input = new DataInputStream(socket.getInputStream());
		
		start();
	}
	
	public void setServerInterface(ServerInterface server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		running = true;
		
		while (running) {
			try {
				ServerEvent type = ServerEvent.values()[input.readInt()];
				switch (type) {
				case LOGIN:
					username = input.readUTF();
					break;
				case MOVE_UP:
					server.moveUp(this, input.readBoolean());
					break;
				case MOVE_DOWN:
					server.moveDown(this, input.readBoolean());
					break;
				default:
					System.out.println("Bad message");
					break;
				}
			} catch (IOException e) {
				close();
			}
		}
		
		System.out.println("Client connection closed");
	}
	
	public void close() {
		running = false;
		server.close(this);
		
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void moveUp(boolean toggle) {
		try {
			output.writeInt(ClientEvent.MOVE_UP.ordinal());
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void moveDown(boolean toggle) {
		try {
			output.writeInt(ClientEvent.MOVE_DOWN.ordinal());
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}
	
	@Override
	public void requestGame(String username) {
		try {
			output.writeInt(ClientEvent.MOVE_DOWN.ordinal());
			output.writeUTF(username);
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

}
