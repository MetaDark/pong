package net.metadark.pong.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.net.Socket;

public class ClientConnection extends Thread {
	
	private static final int MOVE_UP = 0;
	private static final int MOVE_DOWN = 1;
	private static final int UPDATE_BALL = 2;
	
	PongServer server;
	DataOutputStream output;
	DataInputStream input;
	
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
				int type = input.readInt();
				switch (type) {
				case MOVE_UP:
					server.moveUp(this, input.readBoolean());
					break;
				case MOVE_DOWN:
					server.moveDown(this, input.readBoolean());
					break;
				case UPDATE_BALL:
					System.out.println("Do the ball thing");
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
	
	public void moveUp(boolean toggle) {
		try {
			output.writeInt(MOVE_UP);
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}
	
	public void moveDown(boolean toggle) {
		try {
			output.writeInt(MOVE_DOWN);
			output.writeBoolean(toggle);
		} catch (IOException e) {
			close();
		}
	}
	
//	public void updateBall() {
//		try {
//			output.writeInt(MOVE_DOWN);
//			output.writeBoolean(toggle);
//		} catch (IOException e) {
//			close();
//		}
//	}

}
