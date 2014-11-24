package net.metadark.pong.client;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class Input extends InputAdapter {
	
	private Pong pong;
	
	public Input(Pong pong) {
		this.pong = pong;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			pong.leftPaddle.moveUp(true);
			break;
		case Keys.DOWN:
			pong.leftPaddle.moveDown(true);
			break;
		}
		
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			pong.leftPaddle.moveUp(false);
			break;
		case Keys.DOWN:
			pong.leftPaddle.moveDown(false);
			break;
		}
		
		return true;
	}
	
}
