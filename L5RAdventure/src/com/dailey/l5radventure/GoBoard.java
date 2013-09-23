package com.dailey.l5radventure;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GoBoard implements InputProcessor{
	
	GoGame game;
	Sprite goban, whitestone, blackstone;
	OutputStreamWriter pInput;
	ArrayBlockingQueue<String> messages;
	float x,y, width, height, scale, pX, pY;
	

	public void draw(SpriteBatch batch)
	{
		goban.draw(batch);
		float divisionWidth = getWidth()/19;
		float divisionHeight = getHeight()/19;
		for (int i = 0; i < 19;i++)
		{
			for (int j = 0; j < 19; j++)
			{
				if (game.getCoord(i, j)==1)
				{
					blackstone.setPosition(i*divisionHeight+divisionHeight/2, j*divisionWidth+divisionWidth/2);
					blackstone.draw(batch);
				}
				else if (game.getCoord(i, j)==2)
				{
					whitestone.setPosition(i*divisionHeight+divisionHeight/2, j*divisionWidth+divisionWidth/2);
					whitestone.draw(batch);
				}
				else
					assert game.getCoord(i, j)==0;
					
			}
		}
		if (goban.getBoundingRectangle().contains(pX, pY))
		{
			blackstone.setPosition((float)(Math.floor(pX/divisionWidth)*divisionWidth), (float)(Math.floor(pY/divisionHeight)*divisionHeight));	

			if (game.blacksTurn)
				blackstone.draw(batch);
			else
			{
				assert !game.blacksTurn;
				whitestone.draw(batch);
			}
		}

		
		
	}
	
	public GoBoard(TextureAtlas goAtlas, OutputStreamWriter pInput, ArrayBlockingQueue<String> messages)
	{
		x=0;
		y=0;
		pX=-1;
		pY=-1;
		width = 700;
		height = 700;
		scale = 1.0f;
		goban = goAtlas.createSprite("goban");
		goban.setSize(getWidth(),getHeight());
		this.pInput = pInput;
		this.messages = messages;
		blackstone = goAtlas.createSprite("blackstone");
		blackstone.setSize(stoneSize(), stoneSize());
		blackstone.setOrigin(blackstone.getWidth()/2, blackstone.getHeight()/2);
		whitestone = goAtlas.createSprite("whitestone");
		whitestone.setSize(stoneSize(), stoneSize());
		setX(Gdx.graphics.getWidth()/2-getWidth()/2);
		setY((Gdx.graphics.getHeight()-getHeight())/2);
		game = new GoGame();
	}
	
	
	
	
	
	public float stoneSize()
	{
		return getWidth()/19;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public GoGame getGame() {
		return game;
	}
	public void setGame(GoGame game) {
		this.game = game;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		if (game.blacksTurn)
		{
			int tempX = (int)(blackstone.getX()/(getWidth()/19));
			char transX = (char) (tempX+65);
			int tempY = (int)(blackstone.getY()/(getHeight()/19)) + 1;
			try {
				String command = "play black "+transX+""+tempY+"\n";
				Gdx.app.log("GoBoard", "Sending to goEngine: "+command);
				pInput.write(command);
				pInput.flush();
				if (getNext().contains("="))
				{
					Gdx.app.log("GoBoard", "Command accepted");
					Gdx.app.log("GoBoard", "Sending to goEngine: genmove white");
					pInput.write("genmove white\n");
					pInput.flush();
					if (getNext().contains("="))
					{
						Gdx.app.log("GoBoard", "Sending to goEngine: showboard");
						pInput.write("showboard\n");
						pInput.flush();
						if (getNext().equals("= "))
						{
							Gdx.app.log("GoBoard", "Updating board");
							game.updateBoard(messages);
						}
					}


				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private String getNext() throws InterruptedException
	{
		String got = messages.poll(2, TimeUnit.SECONDS);
		while (got.equals("\n") || got.equals(""))
		{
			got = messages.poll(2, TimeUnit.SECONDS);
		}
		Gdx.app.log("GetNext", "Returning \""+got+"\"");
		return got;
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
	public boolean mouseMoved(int screenX, int screenY) 
	{
		pX=screenX;
		pY=Gdx.graphics.getHeight()-screenY;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
