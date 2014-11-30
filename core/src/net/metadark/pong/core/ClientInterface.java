package net.metadark.pong.core;

public abstract interface ClientInterface {
	public enum ClientEvent {
		MOVE_UP,
		MOVE_DOWN
	}

	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
}
