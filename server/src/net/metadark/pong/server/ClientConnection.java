package net.metadark.pong.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.metadark.pong.core.ClientInterface;
import net.metadark.pong.core.ServerInterface.ServerEvent;

import com.badlogic.gdx.net.Socket;

public class ClientConnection extends Thread implements ClientInterface {
	
	private PongServer server;
	private DataOutputStream output;
	private DataInputStream input;
	
	private volatile boolean running;
	
	public ClientConnection(PongServer server, Socket socket) {
		this.server = server;
		this.output = new DataOutputStream(socket.getOutputStream());
		this.input = new DataInputStream(socket.getInputStream());
		
		start();
	}
	
	@Override
	public void run() {
		running = true;
		
		while (running) {
			try {
				ServerEvent type = ServerEvent.values()[input.readInt()];
				switch (type) {
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
		server.closeConnection(this);
		
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

}
