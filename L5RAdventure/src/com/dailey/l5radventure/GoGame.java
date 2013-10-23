package com.dailey.l5radventure;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;


public class GoGame {
	
	int[][] board; //0 = blank 1 = black 2 = white
	double score;  		//positive if black is ahead, negative if white is ahead
	boolean blacksTurn, whiteResign, whitePass, blackPass, over;
	
	public GoGame() 
	{
		board = new int[19][19];
		blacksTurn = true;
		whiteResign = false;
		whitePass = false;
		blackPass = false;
		over = false;
		
		//clear the board
		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board[i].length;j++)
			{
				board[i][j]=0;
			}
		}
	
	}
	
	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public boolean isBlacksTurn() {
		return blacksTurn;
	}

	public void setBlacksTurn(boolean blacksTurn) {
		this.blacksTurn = blacksTurn;
	}

	public boolean isWhiteResign() {
		return whiteResign;
	}

	public void setWhiteResign(boolean whiteResign) {
		this.whiteResign = whiteResign;
	}

	public boolean isWhitePass() {
		return whitePass;
	}

	public void setWhitePass(boolean whitePass) {
		this.whitePass = whitePass;
	}

	public boolean isBlackPass() {
		return blackPass;
	}

	public void setBlackPass(boolean blackPass) {
		this.blackPass = blackPass;
	}
	


	public int getCoord(int i, int j)
	{
		return board[i][j];
	}
	
	public void setCoord(int i, int j, int k)
	{
		board[i][j] = k;
	}
	
	private void clearBoard()
	{
		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board[i].length;j++)
			{
				board[i][j]=0;
			}
		}
	}
	
	private int[] translateFromString(String s)
	{
		int[] answer = new int[2];
		
		String master = "ABCDEFGHJKLMNOPQRST";

		answer[0] = master.indexOf(s.charAt(0));
		answer[1] = Integer.valueOf(s.substring(1))-1;
		return answer;			
	}
	
	private String translateFromPair(int i, int j)
	{
		
		char c = (char)(i+65);
		String p = String.valueOf(j);
		if (j>=9)
			p = String.valueOf(j+1);
		return c+p;
		
	}
	
	public void updateBoard(String blackString, String whiteString)
	{
		Gdx.app.log("GoGame", "Clearing board");
		clearBoard();
		Gdx.app.log("GoGame", "Setting black stones...");
		String[] blackstones = blackString.split(" ");
		Gdx.app.log("GoGame", blackstones.length+" black stones:");
		for (String s : blackstones)
			Gdx.app.log("GoGame", s);
		if (blackstones[0].equals("="))
		{
			for (int i = 1; i < blackstones.length; i++)
			{
				Gdx.app.log("GoGame", "Translating "+blackstones[i]);
				int[] temp = translateFromString(blackstones[i]);
				Gdx.app.log("GoGame", "Results are "+temp[0]+","+temp[1]);
				setCoord(temp[0], temp[1], 1);
			}
		}
		
		//set white stones
		Gdx.app.log("GoGame", "Setting white stones...");
		String[] whitestones = whiteString.split(" ");
		Gdx.app.log("GoGame", whitestones.length+" white stones");
		for (String s : whitestones)
			Gdx.app.log("GoGame", s);
		if (whitestones[0].equals("="))
		{
			for (int i = 1; i < whitestones.length; i++)
			{
				Gdx.app.log("GoGame", "Translating "+whitestones[i]);
				int[] temp = translateFromString(whitestones[i]);
				Gdx.app.log("GoGame", "Results are "+temp[0]+","+temp[1]);
				setCoord(temp[0], temp[1], 2);
			}
		}
		Gdx.app.log("GoGame", "...board updated");
	}
	
	public void updateBoard(ArrayBlockingQueue<String> messages) throws InterruptedException
	{
		Gdx.app.log("GoGame", "Updating board...");
		clearBoard();
		String got = messages.poll(2, TimeUnit.SECONDS);
		
		//set black stones
		Gdx.app.log("GoGame", "Setting black stones...");
		String[] blackstones = got.split(" ");
		Gdx.app.log("GoGame", blackstones.length+" black stones:");
		for (String s : blackstones)
			Gdx.app.log("GoGame", s);
		if (blackstones[0].equals("="))
		{
			for (int i = 1; i < blackstones.length; i++)
			{
				Gdx.app.log("GoGame", "Translating "+blackstones[i]);
				int[] temp = translateFromString(blackstones[i]);
				Gdx.app.log("GoGame", "Results are "+temp[0]+","+temp[1]);
				setCoord(temp[0], temp[1], 1);
			}
		}
		
		//set white stones
		Gdx.app.log("GoGame", "Setting white stones...");
		got = messages.poll(2, TimeUnit.SECONDS);
		String[] whitestones = got.split(" ");
		Gdx.app.log("GoGame", whitestones.length+" white stones");
		for (String s : whitestones)
			Gdx.app.log("GoGame", s);
		if (whitestones[0].equals("="))
		{
			for (int i = 1; i < whitestones.length; i++)
			{
				Gdx.app.log("GoGame", "Translating "+whitestones[i]);
				int[] temp = translateFromString(whitestones[i]);
				Gdx.app.log("GoGame", "Results are "+temp[0]+","+temp[1]);
				setCoord(temp[0], temp[1], 2);
			}
		}
		Gdx.app.log("GoGame", "...board updated");
	}
	
	
	

	
	

}
