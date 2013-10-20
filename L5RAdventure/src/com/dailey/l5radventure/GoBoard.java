package com.dailey.l5radventure;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ArrayBlockingQueue;

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
	float x,y, width, height, scale, pX, pY, rX, rY;
	GoScreen screen;
	String prosMove;
	

	public void draw(SpriteBatch batch)
	{
		goban.setPosition(x, y);
		goban.setSize(width, height);
		goban.draw(batch);
		float divisionWidth = getWidth()/19f;
		float divisionHeight = getHeight()/19f;
		for (int i = 0; i < 19;i++)
		{
			for (int j = 0; j < 19; j++)
			{
				if (game.getCoord(i, j)==1)
				{
					blackstone.setPosition((i)*divisionHeight+getX(), (j)*divisionWidth+getY());
					blackstone.draw(batch);
				}
				else if (game.getCoord(i, j)==2)
				{
					whitestone.setPosition((i)*divisionHeight+getX(), (j)*divisionWidth+getY());
					whitestone.draw(batch);
				}
				else
					assert game.getCoord(i, j)==0;
					
			}
		}
		if (goban.getBoundingRectangle().contains(pX, pY) && !game.whiteResign)
		{
				
			if (game.blacksTurn)
			{
				blackstone.setPosition((float)(Math.floor(getrX()/divisionWidth)*divisionWidth+getX()), (float)(Math.floor(getrY()/divisionHeight)*divisionHeight+getY()));
				blackstone.draw(batch);
			}
			else
			{
				assert !game.blacksTurn;
				whitestone.draw(batch);
			}
		}

		
		
	}
	
	public GoBoard(TextureAtlas goAtlas, OutputStreamWriter pInput, ArrayBlockingQueue<String> messages, GoScreen screen)
	{
		x=0;
		y=0;
		pX=-1;
		pY=-1;
		rX = 1;
		rY = -1;
		prosMove = "";
		width = 700;
		height = 700;
		scale = 1.0f;
		goban = goAtlas.createSprite("goban");
		goban.setSize(getWidth(),getHeight());
		goban.setPosition(x, y);
		this.pInput = pInput;
		this.messages = messages;
		this.screen = screen;
		blackstone = goAtlas.createSprite("blackstone");
		blackstone.setSize(stoneSize(), stoneSize());
		blackstone.setOrigin(blackstone.getWidth()/2, blackstone.getHeight()/2);
		whitestone = goAtlas.createSprite("whitestone");
		whitestone.setSize(stoneSize(), stoneSize());
		setX(Gdx.graphics.getWidth()/2-getWidth()/2);
		setY((Gdx.graphics.getHeight()-getHeight())/2);
		game = new GoGame();
	}
	
	
	
	public String getProsMove() {
		return prosMove;
	}

	public void setProsMove(String prosMove) {
		this.prosMove = prosMove;
	}

	private int[] translateMouseToCoords()
	{
		int[] coords = new int[2];
		
		float divisionWidth = getWidth()/19;
		float divisionHeight = getHeight()/19;
		coords[0]=(int)(Math.floor(pX/divisionWidth));
		coords[1]=(int)(Math.floor(pY/divisionHeight));	

		
		return coords;
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
		if (!(pX>=getX() && pX<=getX()+getWidth()) || !(pY>=getY() && pY<=getY()+getHeight()))
			return false;
		Gdx.app.log("GoBoard", "Width = "+getWidth());
		Gdx.app.log("GoBoard", "Height = "+getHeight());
		Gdx.app.log("GoBoard", "X = "+getX());
		Gdx.app.log("GoBoard", "Y = "+getY());
		float divisionWidth = getWidth()/19f;
		float divisionHeight = getHeight()/19f;
		Gdx.app.log("GoBoard", "divisionWidth = "+divisionWidth);
		Gdx.app.log("GoBoard", "divisionHeight = "+divisionHeight);
		
		if (game.blacksTurn && !game.whiteResign)
		{
			
			
			
			//float bsSpotX = blackstone.getX()+blackstone.getWidth()/2;
			//Gdx.app.log("GoBoard", "blackstone spotx = "+bsSpotX);
			//Gdx.app.log("GoBoard", "Accounting for posit: "+(bsSpotX-getX()));
			//int tempX = (int)Math.round((bsSpotX-getX())/divisionWidth);
			
			int tempX = (int)Math.round((getrX()+divisionWidth/2)/divisionWidth);
			
			Gdx.app.log("GoBoard", "tempX = "+tempX);
			String master = "ABCDEFGHJKLMNOPQRST";
			char transX = master.charAt(tempX-1);
			Gdx.app.log("GoBoard", "transX = "+transX);
			
			//float bsSpotY = blackstone.getY()+blackstone.getHeight()/2;
			//Gdx.app.log("GoBoard", "blackstone spoty = "+bsSpotY);
			//Gdx.app.log("GoBoard", "Accounting for posit: "+(bsSpotY-getY()));
			//int tempY = (int)Math.round((bsSpotY-getY())/divisionHeight);
			
			int tempY = (int)Math.round((getrY()+divisionHeight/2)/divisionHeight);
			
			
			
			Gdx.app.log("GoBoard", "tempY = "+tempY);
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
					Gdx.app.log("GoBoard", "getNexting");
					String next = getNext();
					Gdx.app.log("GoBoard", "getNext() complete.");
					if (next.contains("="))
					{
						Gdx.app.log("GoBoard", "next contains =");
						if (next.contains("resign"))
						{
							game.whiteResign = true;
						}
						else
						{
							Gdx.app.log("GoBoard", "Updating board");
							Gdx.app.log("GoBoard", "Sending command: list_stones black");
							pInput.write("list_stones black\n");
							pInput.flush();
							Gdx.app.log("GoBoard", "Sending command: list_stones white");
							pInput.write("list_stones white\n");
							pInput.flush();					
							game.updateBoard(getNext(),getNext());
						}

					}
					else
					{
						Gdx.app.log("GoBoard", "getNext() doesn't contain =");
					}


				}
				pInput.write("estimate_score\n");
				pInput.flush();
				
				screen.updateScore(getNext());
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
		//String got = messages.poll(5, TimeUnit.MINUTES);
		String got = null; //you shouldn't have to do this, that's the whole point of a blocking queue, but take() isn't waiting before returning null, so here we are.
		while (got == null)
			got = messages.take();
		while (got.equals("\n") || got.equals(""))
		{
			got = messages.take();
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
		rX = pX - getX();
		rY = pY - getY();
		float divisionWidth = getWidth()/19f;
		float divisionHeight = getHeight()/19f;

		
		
		
		
		int tempX = (int)Math.round((getrX()+divisionWidth/2)/divisionWidth);
		String master = "ABCDEFGHJKLMNOPQRST";
		if (tempX>19)
			tempX=19;
		else if (tempX<1)
			tempX = 1;
		char transX = master.charAt(tempX-1);

		int tempY = (int)Math.round((getrY()+divisionHeight/2)/divisionHeight);

		
		prosMove = transX+""+tempY;
		
		return false;
	}

	public Sprite getGoban() {
		return goban;
	}

	public void setGoban(Sprite goban) {
		this.goban = goban;
	}

	public Sprite getWhitestone() {
		return whitestone;
	}

	public void setWhitestone(Sprite whitestone) {
		this.whitestone = whitestone;
	}

	public Sprite getBlackstone() {
		return blackstone;
	}

	public void setBlackstone(Sprite blackstone) {
		this.blackstone = blackstone;
	}

	public OutputStreamWriter getpInput() {
		return pInput;
	}

	public void setpInput(OutputStreamWriter pInput) {
		this.pInput = pInput;
	}

	public ArrayBlockingQueue<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayBlockingQueue<String> messages) {
		this.messages = messages;
	}

	public float getpX() {
		return pX;
	}

	public void setpX(float pX) {
		this.pX = pX;
	}

	public float getpY() {
		return pY;
	}

	public void setpY(float pY) {
		this.pY = pY;
	}

	public float getrX() {
		return rX;
	}

	public void setrX(float rX) {
		this.rX = rX;
	}

	public float getrY() {
		return rY;
	}

	public void setrY(float rY) {
		this.rY = rY;
	}

	public GoScreen getScreen() {
		return screen;
	}

	public void setScreen(GoScreen screen) {
		this.screen = screen;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
