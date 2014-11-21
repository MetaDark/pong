package net.metadark.pong.server;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class PongServer extends Thread {
	
	private static int DEFAULT_PORT = 5436;
	private int port;
	
	private ArrayList<Socket> clients = new ArrayList<Socket>();

	public PongServer() {
		this(DEFAULT_PORT);
	}

	public PongServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 1000;
		
		ServerSocket server = Gdx.net.newServerSocket(Protocol.TCP, port, serverSocketHint);
		
		while (true) {
			Socket client = server.accept(null);
			System.out.println("Client connected: " + client.getRemoteAddress());
		}
	}
}
