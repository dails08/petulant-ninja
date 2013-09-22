package com.dailey.l5radventure;

import com.badlogic.gdx.Game;

public class L5RGame extends Game{
	
	public static final String LOG = "L5RGame";
	
	@Override
	public void create() {		

	setScreen(new GoScreen());
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
