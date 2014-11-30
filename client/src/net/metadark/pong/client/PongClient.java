package net.metadark.pong.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.metadark.pong.core.ClientInterface;
import net.metadark.pong.core.ClientInterface.ClientEvent;
import net.metadark.pong.core.ServerInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class PongClient extends Thread implements ServerInterface {
	
	private ClientInterface client;
	private DataOutputStream output;
	private DataInputStream input;

	private volatile boolean running;

	public PongClient(ClientInterface client, String host, int port, String username) {
		setClientInterface(client);
		
		SocketHints socketHint = new SocketHints();
		socketHint.connectTimeout = 1000;

		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, socketHint);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());

		start();
	}
	
	public void setClientInterface(ClientInterface client) {
		this.client = client;
	}

	@Override
	public void run() {
		running = true;

		while (running) {
			try {
				ClientEvent type = ClientEvent.values()[input.readInt()];
				switch (type) {
				case MOVE_UP:
					client.moveUp(input.readBoolean());
					break;
				case MOVE_DOWN:
					client.moveDown(input.readBoolean());
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

}
