package net.metadark.pong.server;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

import net.metadark.pong.core.actors.Ball;
import net.metadark.pong.core.actors.Paddle;
import net.metadark.pong.core.actors.Paddle.Side;

public class PongGame extends Thread implements ServerInterface {
	
	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	
	private ClientConnection leftClient;
	private ClientConnection rightClient;
	
	private volatile boolean running;
	
	public PongGame(PongServer server, ClientConnection leftClient, ClientConnection rightClient) {
		this.leftClient = leftClient;
		this.rightClient = rightClient;
		
		// Make this game a server interface for the clients
		leftClient.setServerInterface(this);
		rightClient.setServerInterface(this);
		
		// Setup the camera
		Camera camera = new OrthographicCamera(800, 480);
		
		// Setup the actors
		leftPaddle = new Paddle(camera, Side.LEFT);
		rightPaddle = new Paddle(camera, Side.RIGHT);
		ball = new Ball(camera, leftPaddle, rightPaddle);
		
		start();
	}
	
	@Override
	public void run() {
		running = true;
		
		while (running) {
			ball.update();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private ClientConnection otherClient(ClientConnection client) {
		return client == leftClient ? rightClient : leftClient;
	}
	
	private Paddle clientPaddle(ClientConnection client) {
		return client == leftClient ? leftPaddle : rightPaddle;
	}
	
	/**
	 * Broadcast a paddle down message to all players
	 * @param origin The client that moved
	 * @param toggle Start/stop movement
	 */
	public void moveUp(ClientConnection client, boolean toggle) {
		clientPaddle(client).moveUp(toggle);
		otherClient(client).moveUp(toggle);
	}

	/**
	 * Broadcast a paddle up message to all players
	 * @param origin The client that moved
	 * @param toggle Start/stop movement
	 */
	public void moveDown(ClientConnection client, boolean toggle) {
		clientPaddle(client).moveDown(toggle);
		otherClient(client).moveDown(toggle);
	}

	@Override
	public void close(ClientConnection client) {
		otherClient(client).quitGame();
		running = false;
	}
}
