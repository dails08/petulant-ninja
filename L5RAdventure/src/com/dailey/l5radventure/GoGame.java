package com.dailey.l5radventure;


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

}
