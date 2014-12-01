package net.metadark.pong.core;

public abstract interface ServerInterface {
	
	public static final int DEFAULT_PORT = 5436;
	
	public enum ServerEvent {
		LOGIN,
		MATCH,
		MOVE_UP,
		MOVE_DOWN,
		CLOSE
	}

	public void login(String username);
	public void match();
	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
	public void close();
}
