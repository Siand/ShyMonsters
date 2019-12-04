package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import UI.Game;
import misc.Constants;
import util.ParseMove;

public class Reciever implements Runnable {
	
	boolean running = true;
	Socket clientSocket;
	boolean connected = false;
	int port;
	String host;
	
	public Reciever(Socket s) {
		clientSocket = s;
	}
	
	public void run()
	{
		BufferedReader in;
		try
		{
			in = new BufferedReader(
			        new InputStreamReader(clientSocket.getInputStream()));
			while(running) {
				String data = in.readLine();
				if(data == null) continue;
				if(data.contains("Start:")) {
					data = data.substring(6);
					Game game = new Game(data.equals("DM")? Constants.DM : Constants.HERO);
					continue;
				}
				ParseMove pm = new ParseMove(data);
				pm.parse();
			}
		}
		catch (IOException e)
		{
			System.err.println("connection closed");
			running = false;
		}
	}
	
	public void setRunning(boolean r) {
		running = r;
	}
}
