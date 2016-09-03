package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class User {

	
	private static final int USER_THROTTLE = 200;
	private Socket socket;
	private boolean connected;
	private Inport inport;
	
	private class Inport extends Thread
	{
		private ObjectInputStream in;
		public void run()
		{
			try {
				in = new ObjectInputStream(socket.getInputStream());
			}
			catch (IOException e)
			{
				System.out.println("Could not get input stream form " + toString());
				return;
			}
			System.out.println(socket + " has connected input");
			while (true)
			{
				try 
				{
					Thread.sleep(USER_THROTTLE);
				}
				catch (Exception e)
				{
					System.out.println(toString()+ " has input interrupted");
				}
			}
		}
	}
	
	public User(Socket newSocket)
	{
		socket = newSocket;
		connected =true;
		
		inport = new Inport();
		inport.start();
		
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	public void purge()
	{
		try 
		{
			connected = false; 
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Could not pruge" + socket);
		}
	}
	
	public String toString()
	{
		return new String(socket.toString());
	}
}
