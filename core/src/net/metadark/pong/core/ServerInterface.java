package net.metadark.pong.core;

/**
 * A contract used to send messages to a sever
 * and to handle messages coming from a client
 * @author kurt
 */
public abstract interface ServerInterface {
	
	public static final int DEFAULT_PORT = 5436;
	
	public enum ServerEvent {
		LOGIN,
		MATCH,
		ACCEPT_GAME,
		MOVE_UP,
		MOVE_DOWN,
		CLOSE
	}

	public void login(String username);
	public void match();
	public void acceptGame();
	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
	public void close();
}
