package net.metadark.pong.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class PongClient extends Thread {

	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 5436;

	private static final int MOVE_UP = 0;
	private static final int MOVE_DOWN = 1;
	private static final int BALL = 2;
	
	private Pong pong;
	private DataOutputStream output;
	private DataInputStream input;

	private volatile boolean running;

	public PongClient(Pong pong) {
		this(pong, DEFAULT_HOST, DEFAULT_PORT);
	}

	public PongClient(Pong pong, String host) {
		this(pong, host, DEFAULT_PORT);
	}

	public PongClient(Pong pong, String host, int port) {
		this.pong = pong;

		SocketHints socketHint = new SocketHints();
		socketHint.connectTimeout = 1000;

		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, socketHint);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());

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
					pong.getRightPaddle().moveUpPure(input.readBoolean());
					break;
				case MOVE_DOWN:
					pong.getRightPaddle().moveDownPure(input.readBoolean());
					break;
				case BALL:
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
	}

	public void close() {
		running = false;

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

}
