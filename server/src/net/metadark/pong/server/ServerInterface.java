package net.metadark.pong.server;

public abstract interface ServerInterface {
	public void match(ClientConnection client);
	public void moveUp(ClientConnection client, boolean toggle);
	public void moveDown(ClientConnection client, boolean toggle);
	public void close(ClientConnection client);
}
