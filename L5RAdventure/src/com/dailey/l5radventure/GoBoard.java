package com.dailey.l5radventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GoBoard implements InputProcessor{
	
	GoGame game;
	Sprite goban, whitestone, blackstone;
	float x,y, width, height, scale, pX, pY;
	

	public void draw(SpriteBatch batch)
	{
		goban.draw(batch);
		if (goban.getBoundingRectangle().contains(pX, pY))
		{
			if (game.blacksTurn)
			{
				//blackstone.setPosition(pX-blackstone.getWidth()/2, pY-blackstone.getHeight()/2);
				float stoneX = pX-blackstone.getWidth()/2;
				float stoneY = pY-blackstone.getHeight()/2;
				blackstone.setPosition(getX()+(float)(Math.floor(stoneX/getWidth()/19)*(getWidth()/19)+.5*stoneSize()), getY()+(float)(Math.floor(stoneY/getHeight()/19)*(getHeight()/19)+.5*stoneSize()));
				blackstone.draw(batch);
			}
			else
			{
				assert !game.blacksTurn;
				whitestone.setPosition(pX, pY);
				whitestone.draw(batch);
	
			}
		}
		
		
	}
	
	public GoBoard(TextureAtlas goAtlas)
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
