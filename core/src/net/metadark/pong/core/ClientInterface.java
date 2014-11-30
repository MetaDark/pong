package net.metadark.pong.core;

public abstract interface ClientInterface {
	public enum ClientEvent {
		REQUEST_GAME,
		MOVE_UP,
		MOVE_DOWN,
		QUIT_GAME,
		CLOSE
	}

	public void requestGame(String username);
	public void moveUp(boolean toggle);
	public void moveDown(boolean toggle);
	public void quitGame();
	public void close();
}
