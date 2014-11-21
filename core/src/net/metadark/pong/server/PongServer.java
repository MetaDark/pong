package net.metadark.pong.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class PongServer implements Runnable {

	private static final int DEFAULT_PORT = 5436;

	@Override
	public void run() {
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;

		ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, DEFAULT_PORT,
				serverSocketHint);

		while (true) {
			Socket socket = serverSocket.accept(null);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// try {
			// labelMessage.setText(buffer.readLine());
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}
	}
}
