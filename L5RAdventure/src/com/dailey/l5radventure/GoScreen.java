package com.dailey.l5radventure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class GoScreen implements Screen{
	
	TextureAtlas goAtlas;
	SpriteBatch batch;
	GoBoard board;;
	Process gnugo=null;		//gnugo ai, must support GTP
	BufferedReader processOutput;
	BufferedReader processError;
	Writer processInput;
	ReaderThread reader;
	ArrayBlockingQueue<String> messages;

	
	@Override
	public void render(float delta) {
		//Gdx.app.log(L5RGame.LOG, "Rendering...");
		Gdx.gl.glClearColor(0f,0f,0f,0f);
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
	
		while (messages.peek()!=null)
			{
				Gdx.app.log("GoScreen", messages.poll());
				messages.remove(0);
			}


		batch.begin();
		
		board.draw(batch);
		
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
		
		Gdx.app.log("GoScreen", System.getProperty("os.name").toLowerCase());
		
		board = new GoBoard(goAtlas);
		Gdx.input.setInputProcessor(board);
		
		try {
			gnugo = Runtime.getRuntime().exec("gnugo --mode gtp");
		} catch (IOException e) {
			Gdx.app.log("GoScreen", "Problem starting gnugo!");
			e.printStackTrace();
		}
		processOutput = new BufferedReader(new InputStreamReader(gnugo.getInputStream()));
		processInput = new OutputStreamWriter(gnugo.getOutputStream());
		//processError = new BufferedReader(new InputStreamReader(gnugo.getErrorStream()));
		
		messages = new ArrayBlockingQueue<String>(30);
	
		reader = new ReaderThread(processOutput, messages);
		reader.start();
		

		
		try {
			Gdx.app.log("GoScreen", "Writing to gnugo...");
			processInput.write("showboard\n");
			processInput.flush();
			processInput.write("estimate_score\n");
			processInput.flush();
			Gdx.app.log("GoScreen", "...written.");
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Gdx.app.log(L5RGame.LOG, "...shown.");
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
