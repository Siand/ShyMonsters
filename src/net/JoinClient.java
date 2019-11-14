package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class JoinClient implements Client
{

	Socket clientSocket = null;
	boolean connected = false;
	PrintWriter out = null;
	Reciever reciever = null;
	public JoinClient(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Failed to connect to server");
		}
		connected = true;
		reciever = new Reciever(clientSocket);
		(new Thread(reciever)).start();
	}

	@Override
	public void send(String data)
	{
		out.println(data);
	}
	
	public void close() {
		if(!connected)
			return;
		reciever.setRunning(false);
		try
		{
			clientSocket.close();
			connected = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
}
