package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class HostClient implements Client
{

	public static int portNO = 1237;
	public static boolean hasConnection = false;
	public static Socket connection = null;
	public static Reciever reciever = null;
	public static PrintWriter out = null;
	ConnectionReciever cr;
	
	public HostClient(int port) {
		portNO = port;
		cr = new ConnectionReciever();
		(new Thread(cr)).start();
	}
	@Override
	public void send(String data)
	{
		out.println(data);
	}

	@Override
	public void close()
	{
		if(reciever != null) reciever.setRunning(false);
		if(connection != null)
			try {
				connection.close();
			} catch (IOException e) {
				//Nothing
			}
		if(cr.running) {
			cr.kill();
		}
	}

	

}

class ConnectionReciever implements Runnable {
	public boolean running = true;
	public ConnectionReciever() {
	}

	ServerSocket listening;
	public void kill() {
		try {
			listening.close();
      	  	running = false;
		} catch (IOException e) {
			// nothing
		}
	}
	public void reset() {
		try {
			listening.close();
			listening = new ServerSocket(HostClient.portNO);
		} catch (IOException e) {
			System.out.println("Reciever died."); 
		}
	}
	@Override
	public void run()
	{
	    try {
	    	listening = new ServerSocket(HostClient.portNO);
			while (!HostClient.hasConnection) {
				Socket connection = listening.accept();
				HostClient.hasConnection = true;
				Reciever reciever = new Reciever(connection);
			    HostClient.connection = connection;
			    HostClient.out = new PrintWriter(connection.getOutputStream(), true);
			    (new Thread(reciever)).start();
			    HostClient.reciever = reciever;
			    
			}
			running = false;
	    } catch (Exception e) {
	    	System.out.println("Reciever died."); 
	    }
	    
	}
	
}