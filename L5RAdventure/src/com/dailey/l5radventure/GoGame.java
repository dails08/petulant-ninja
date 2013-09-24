package com.dailey.l5radventure;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;


public class GoGame {
	
	int[][] board; //0 = blank 1 = black 2 = white
	double score;  		//positive if black is ahead, negative if white is ahead
	boolean blacksTurn;
	
	public GoGame() 
	{
		board = new int[19][19];
		blacksTurn = true;
		
		//clear the board
		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board[i].length;j++)
			{
				board[i][j]=0;
			}
		}
	
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
		
		answer[0] = s.charAt(0)-65;
		answer[1] = Integer.valueOf(s.substring(1))-1;
		return answer;			
	}
	
	private String translateFromPair(int i, int j)
	{
		char c = (char)(i+65);
		String p = String.valueOf(j);
		return c+p;
		
	}
	
	public void updateBoard(String blackString, String whiteString)
	{
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
	
	/* update board based on gnugo:showboard
	public void updateBoard(ArrayBlockingQueue<String> messages) throws InterruptedException
	{
		if (messages.poll(2, TimeUnit.SECONDS).contains("A B C D E F G H J K L M N O P Q R S T"))
		{
			for (int i = 0; i<19; i++)
			{
				String row = messages.poll(2, TimeUnit.SECONDS);
				Gdx.app.log("GoGame", "Parsing through: "+row);
				for (int j=4; j<40;j=j+2)
				{
					if (row.charAt(j)=='.' || row.charAt(j)=='+')
					{
						Gdx.app.log("GoGame", "("+i+","+(j-4)/2+") is empty");
						board[i][(j-4)/2] = 0;
					}
					else if (row.charAt(j)=='X')
					{
						Gdx.app.log("GoGame", "("+i+","+(j-4)/2+") is black");
						board[i][(j-4)/2] = 1;
					}
					else
					{
						assert row.charAt(j)=='O';
						Gdx.app.log("GoGame", "("+i+","+(j-4)/2+") is white");
						board[i][(j-4)/2] = 2;
					}
				}
			}
			assert messages.poll(2, TimeUnit.SECONDS).contains("A B C D E F G H J K L M N O P Q R S T");
		}
	}
	*/
	

	
	

}
