package net.metadark.pong.server;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

import net.metadark.pong.core.actors.Ball;
import net.metadark.pong.core.actors.Paddle;
import net.metadark.pong.core.actors.Paddle.Side;

public class PongGame extends Thread implements BroadcastInterface {
	
	private PongServer server;
	private boolean leftAccepted;
	private boolean rightAccepted;
	
	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	
	private ClientConnection leftClient;
	private ClientConnection rightClient;
	
	private int leftScore;
	private int rightScore;
	
	private Camera camera;
	
	private volatile boolean running;
	
	public PongGame(PongServer server, ClientConnection leftClient, ClientConnection rightClient) {
		this.server = server;
		this.leftClient = leftClient;
		this.rightClient = rightClient;
		
		// Send a request to the players to start the game
		leftClient.requestGame(rightClient.getUsername());
		rightClient.requestGame(leftClient.getUsername());
		
		// Make this game a server interface for the clients
		leftClient.setServerInterface(this);
		rightClient.setServerInterface(this);
		
		// Setup the camera
		camera = new OrthographicCamera(800, 480);
		
		// Setup the actors
		leftPaddle = new Paddle(camera, Side.LEFT);
		rightPaddle = new Paddle(camera, Side.RIGHT);
		ball = new Ball(camera, leftPaddle, rightPaddle);
	}
	
	/**
	 * Check for collisions and update score boards
	 * TODO: Refactor this method, it's ugly and inefficient
	 */
	@Override
	public void run() {
		running = true;
		
		// Calculate the center ball coordinates
		float x = (camera.viewportWidth - ball.width) / 2;
		float y = (camera.viewportHeight - ball.height) / 2;
		
		// Send the ball to the left
		ball.reset(x, y, -1, 0);
		resetBall(x, y, -1, 0);
		
		while (running) {
			
			// Update actor positions
			leftPaddle.update(0.02f);
			rightPaddle.update(0.02f);
			ball.update(0.02f);
			
			// Handle ball passing through "net"
			switch(ball.getSide()) {
			case LEFT:
				rightScore++;
				updateScore();
				
				// Wait a second before repositioning the ball
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// Send ball to the right
				ball.reset(x, y, 1, 0);
				resetBall(x, y, 1, 0);
				break;
			case RIGHT:
				leftScore++;
				updateScore();
				
				// Wait a second before repositioning the ball
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// Send ball to the left
				ball.reset(x, y, -1, 0);
				resetBall(x, y, -1, 0);
				break;
			case DEFAULT:
				break;
			}
			
			// Wait 20 milliseconds before updating again
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the other client connection
	 * @param client
	 */
	private ClientConnection otherClient(ClientConnection client) {
		return client == leftClient ? rightClient : leftClient;
	}
	
	/**
	 * Get the paddle associated with a client connection
	 */
	private Paddle clientPaddle(ClientConnection client) {
		return client == leftClient ? leftPaddle : rightPaddle;
	}
	
	/**
	 * Handle messages from client
	 */
	
	@Override
	public void match(ClientConnection client) {}
	
	@Override
	public void acceptGame(ClientConnection client) {
		if (client == leftClient) {
			leftAccepted = true;
		} else {
			rightAccepted = true;
		}
		
		// Start the game once both players accept
		if (leftAccepted && rightAccepted) {
			System.out.println(
				"Starting game between " +
				leftClient.getUsername() + " and " +
				rightClient.getUsername()
			);
			
			start();
		}
	}
	
	@Override
	public void resetBall(float x, float y, float xVelocity, float yVelocity) {
		leftClient.resetBall(x, y, xVelocity, yVelocity);
		rightClient.resetBall(x, y, -xVelocity, yVelocity);
	}
	
	@Override
	public void updateScore() {
		leftClient.updateScore(leftScore, rightScore);
		rightClient.updateScore(rightScore, leftScore);
	}
	
	@Override
	public void moveUp(ClientConnection client, float y, boolean toggle) {
		Paddle paddle = clientPaddle(client);
		paddle.y = y;
		paddle.moveUp(toggle);
		otherClient(client).moveUp(y, toggle);
	}

	@Override
	public void moveDown(ClientConnection client, float y, boolean toggle) {
		Paddle paddle = clientPaddle(client);
		paddle.y = y;
		paddle.moveDown(toggle);
		otherClient(client).moveDown(y, toggle);
	}

	@Override
	public void close(ClientConnection client) {
		ClientConnection other = otherClient(client);

		other.setServerInterface(server);
		other.quitGame();
		
		System.out.println(
			"Closing game between " +
			leftClient.getUsername() + " and " +
			rightClient.getUsername()
		);
		
		running = false;
	}
	
}
