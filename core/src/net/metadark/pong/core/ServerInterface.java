package net.metadark.pong.core;

public abstract interface ServerInterface {
	
	public static final int DEFAULT_PORT = 5436;
	
	public enum ServerEvent {
		MOVE_UP,
		MOVE_DOWN
	}

	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
}
