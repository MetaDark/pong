package net.metadark.pong.client;

import net.metadark.pong.shared.Paddle;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class Input extends InputAdapter {
	
	private Paddle playerPaddle;
	private Paddle opponentPaddle;
	
	public Input(Paddle playerPaddle, Paddle opponentPaddle) {
		this.playerPaddle = playerPaddle;
		this.opponentPaddle = opponentPaddle;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			playerPaddle.moveUp(true);
			break;
		case Keys.DOWN:
			playerPaddle.moveDown(true);
			break;
		case Keys.LEFT:
			opponentPaddle.moveUp(true);
			break;
		case Keys.RIGHT:
			opponentPaddle.moveDown(true);
			break;
		}
		
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			playerPaddle.moveUp(false);
			break;
		case Keys.DOWN:
			playerPaddle.moveDown(false);
			break;
		case Keys.LEFT:
			opponentPaddle.moveUp(false);
			break;
		case Keys.RIGHT:
			opponentPaddle.moveDown(false);
			break;
		}
		
		return true;
	}
	
}
