package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Server extends Thread
{
	private static final int PORT = 30480;
	private static final int ROOM_THROTTLE = 200;
	private ServerSocket serverSocket;
	private InetAddress hostAddress;
	private Socket socket;
	private ArrayList<User> users = new ArrayList<User>();
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	//Create new server
	public Server()
	{
		try 
		{
			hostAddress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			System.out.println("Could not get Host Address");
			return;
		}
		System.out.println("Server host address is " + hostAddress);
		
		try
		{
			serverSocket = new ServerSocket(PORT,0,hostAddress);
		}
		catch (IOException e)
		{
			System.out.println("Socket "+serverSocket+" created");
		}
		
	}
	
	public void run()
	{
		System.out.println("Room has been started");
		while (true)
		{
			for (int i = 0; i <users.size();i++)
			{
				if (!users.get(i).isConnected())
				{
					System.out.println(users.get(i)+ " removed due to lack of connection");
					users.remove(i);
				}
			}
			
			try
			{
				socket = serverSocket.accept();
			}
			catch (IOException e)
			{
				System.out.println("Could not get a client");
			}
			
			System.out.println("Client " + socket + " has connected");
			
			users.add(new User(socket));
			
			try
			{
				Thread.sleep(ROOM_THROTTLE);
				
			}
			catch (InterruptedException e)
			{
				System.out.println("Room has been interrupted");
			}
			
		}
	}
}
