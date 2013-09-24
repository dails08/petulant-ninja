package com.dailey.l5radventure;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import com.badlogic.gdx.Gdx;

public class ReaderThread extends Thread {
	
	BufferedReader reader;
	ArrayBlockingQueue<String> mezzanine;
	public ReaderThread(BufferedReader reader, ArrayBlockingQueue<String> m)
	{
		this.reader = reader;
		mezzanine = m;
	}
	
	
	public void run()
	{
		Gdx.app.log("ReaderThread", "run()ing...");
		{
	        String line = null;
	        try {
	            while (true)
	            {
	            	if ((line = reader.readLine()) != null)
	            	{
	            		Gdx.app.log("ReaderThread", line);
	            		mezzanine.offer(line);
	            	}
	            }
	        }
	        catch(IOException exception) {
	            System.out.println("!!Error: " + exception.getMessage());
	        }
	    }
		Gdx.app.log("ReaderThread", "...run.");
	}

}
