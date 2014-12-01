package net.metadark.pong.core;

/**
 * A contract used to send messages to a client
 * and to handle messages coming from a server
 * @author kurt
 */
public abstract interface ClientInterface {
	public enum ClientEvent {
		REQUEST_GAME,
		RESET_BALL,
		UPDATE_SCORE,
		MOVE_UP,
		MOVE_DOWN,
		QUIT_GAME,
		CLOSE
	}

	public void requestGame(String username);
	public void resetBall(float x, float y, float xVelocity, float yVelocity);
	public void updateScore(int leftScore, int rightScore);
	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
	public void quitGame();
	public void close();
}
