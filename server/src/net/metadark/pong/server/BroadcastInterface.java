package net.metadark.pong.server;

/**
 * Similar to the ServerInterface but specifically
 * for broadcasting/relaying messages
 * @author kurt
 */
public abstract interface BroadcastInterface {
	public void match(ClientConnection client);
	public void resetBall(float x, float y, float xVelocity, float yVelocity);
	public void updateScore();
	public void acceptGame(ClientConnection client);
	public void moveUp(ClientConnection client, boolean toggle);
	public void moveDown(ClientConnection client, boolean toggle);
	public void close(ClientConnection client);
}
