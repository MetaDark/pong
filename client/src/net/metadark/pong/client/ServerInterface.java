package net.metadark.pong.client;

public abstract interface ServerInterface {
	
	public static final int DEFAULT_PORT = 5436;
	
	public enum ServerEvent {
		LOGIN,
		MOVE_UP,
		MOVE_DOWN
	}

	public void login(String username);
	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
	public void close();
}
