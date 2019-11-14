package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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

		while(running) {
			BufferedReader in;
			try
			{
				in = new BufferedReader(
				        new InputStreamReader(clientSocket.getInputStream()));
				String data = in.readLine();
				ParseMove pm = new ParseMove(data);
				pm.parse();
			}
			catch (IOException e)
			{
				System.err.println("connection closed");
			}
			
		}
	}
	
	public void setRunning(boolean r) {
		running = r;
	}
}
