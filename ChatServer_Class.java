/*
CS4345 
Spring 2016
Program 3
Gennady Evtodiev
Fiifi Smith Quayson
Akshay Patel
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer_Class implements Runnable
{
	Socket SOCK;
	private Scanner input;
	private PrintWriter out;
	String msg = "";

	//constructor which accepts socket object
	public ChatServer_Class(Socket A)
	{
		this.SOCK = A;
	}
	//method checks connecction
	public void Connection() throws IOException
	{
		if(!SOCK.isConnected()) //if socket is not connected
		{
			for(int i = 1; i <= ChatServer.ConnectionArray.size(); i++) //iterating through connection array
			{
				if(ChatServer.ConnectionArray.get(i) == SOCK) //if we finding that socket which is not connected
				{
					ChatServer.ConnectionArray.remove(i); //removing not connected socket from array list
				}
			}
			for(int i = 1; i <= ChatServer.ConnectionArray.size(); i++)
			{
				Socket TEMP_SOCK = (Socket) ChatServer.ConnectionArray.get(i-1);
				PrintWriter tempOut = new PrintWriter(TEMP_SOCK.getOutputStream());
				tempOut.println(TEMP_SOCK.getLocalAddress().getHostName() + "disconnected"); //display disconnected socket for the rest of the users
				tempOut.flush();
				System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + "disconnected"); //display disconnected socket on server
			}
		}
	}
	//method called on Thread start
	public void run()
	{
		try
		{
			try
			{
				input = new Scanner(SOCK.getInputStream()); //incoming data
				out = new PrintWriter(SOCK.getOutputStream()); //outgoing data
				while(true)
				{
					Connection();
					if(!input.hasNext())
					{
						return;
					}
					msg = input.nextLine(); //reading line on Scanner object
					System.out.println("Client said: " + msg);
					for(int i = 1; i <= ChatServer.ConnectionArray.size(); i++) //iterate through connections and display msg on server
					{
						Socket TEMP_SOCK = (Socket) ChatServer.ConnectionArray.get(i-1);
						PrintWriter tempOut = new PrintWriter(TEMP_SOCK.getOutputStream());
						tempOut.println(msg);
						tempOut.flush();
						//System.out.println("Sent: " + TEMP_SOCK.getLocalAddress().getHostName());
					}

				}

			}
			finally
			{
				//TEMP_SOCK.close();
				//SOCK.close();
			}
		}
		catch(Exception A)
		{
			System.out.print(A);
		}
	}
}

