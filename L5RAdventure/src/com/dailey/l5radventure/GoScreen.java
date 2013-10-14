package com.dailey.l5radventure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.ArrayBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class GoScreen implements Screen, InputProcessor{
	
	TextureAtlas goAtlas, UIObjects;
	BitmapFont font;
	SpriteBatch batch;
	GoBoard board;
	Process gnugo=null;		//gnugo ai, must support GTP
	BufferedReader processOutput;
	BufferedReader processError;
	OutputStreamWriter processInput;
	ReaderThread reader;
	ArrayBlockingQueue<String> messages;
	String scoreSummary;
	double score;
	
	Stage ui;

	
	@Override
	public void render(float delta) {
		//Gdx.app.log(L5RGame.LOG, "Rendering...");
		Gdx.gl.glClearColor(0f,0f,0f,0f);
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
	



		batch.begin();
		
		board.draw(batch);
		font.setColor(Color.WHITE);
		font.draw(batch, scoreSummary, 0, Gdx.graphics.getHeight()-30);

		
		batch.end();
		//Gdx.app.log(L5RGame.LOG, "...rendered.");
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {

		Gdx.app.log(L5RGame.LOG, "Showing...");
		batch = new SpriteBatch();
		goAtlas = new TextureAtlas("data/L5RPack1.pack");
		UIObjects = new TextureAtlas("data/UIObjects.pack");
		font = new BitmapFont(Gdx.files.internal("data/ubuntu.fnt"), UIObjects.findRegion("ubuntu"), false);
		
		
		Gdx.app.log("GoScreen", System.getProperty("os.name").toLowerCase());
		
		//processError = new BufferedReader(new InputStreamReader(gnugo.getErrorStream()));
		
		
		try {
			gnugo = Runtime.getRuntime().exec("gnugo --mode gtp --level 1");
		} catch (IOException e) {
			Gdx.app.log("GoScreen", "Problem starting gnugo!");
			e.printStackTrace();
		}
		
		processOutput = new BufferedReader(new InputStreamReader(gnugo.getInputStream()));
		processInput = new OutputStreamWriter(gnugo.getOutputStream());

		

		messages = new ArrayBlockingQueue<String>(30);
		
		board = new GoBoard(goAtlas, processInput, messages, this);
		board.setX(70);
		board.setY(70);
	
		Gdx.input.setInputProcessor(board);

	
		reader = new ReaderThread(processOutput, messages);
		reader.start();
		
		scoreSummary = "Even game";
		
		ui = new Stage();
		
		
		

	
		
		Gdx.app.log(L5RGame.LOG, "...shown.");
	}
	
	public void updateScore(String scoreRaw)
	{
		String[] scoreSplit = scoreRaw.split(" ");
		if (scoreSplit[0].equals("="))
		{
			String ahead = "Black";
			if (scoreSplit[1].charAt(0)=='W')
				ahead = "White";
			scoreSummary = ahead + " winning by " + scoreSplit[1].substring(2);			
			
		}
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		goAtlas.dispose();
		batch.dispose();
		font.dispose();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
