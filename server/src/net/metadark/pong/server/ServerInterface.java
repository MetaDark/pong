package net.metadark.pong.server;

public abstract interface ServerInterface {
	
	public static final int DEFAULT_PORT = 5436;
	
	public enum ServerEvent {
		LOGIN,
		MOVE_UP,
		MOVE_DOWN
	}
	
	public void moveUp(ClientConnection client, boolean toggle);
	public void moveDown(ClientConnection client, boolean toggle);
	public void close(ClientConnection client);
}
