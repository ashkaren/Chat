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
import javax.swing.*;

public class ChatClient implements Runnable
{
	Socket SOCK;
	Scanner input;
	Scanner SEND = new Scanner(System.in);
	PrintWriter out;

	//constractor takes socket
	public ChatClient(Socket A)
	{
		this.SOCK = A;
	}
	//implemented on start method of thread
	public void run()
	{
		try
		{
			try
			{
				input = new Scanner(SOCK.getInputStream()); //takes input/output
				out = new PrintWriter(SOCK.getOutputStream()); 
				out.flush();

				receive();
			}
			finally
			{
				SOCK.close();
			}
		}
		catch(Exception A)
		{
			System.out.print(A);
		}
	}
	//send out to users name of disconnected user
	public void DISCONNECT() throws IOException
	{
		out.println(ChatClientGui.userName + " disconnected");
		out.flush();
		SOCK.close();
		System.exit(0);
	}

	@SuppressWarnings("unchecked")
	public void receive()
	{
		while(true)
		{	
			if(input.hasNext())
			{
				String msg = input.nextLine(); //if there is something we will do it
				if(msg.contains("#")) //stream comes from server, we have to chop unnecessary characters because whole arraylist of users passed in
				{
					String temp1 = msg.substring(1);
					temp1 = temp1.replace("[","");
					temp1 = temp1.replace("]","");
					String currentUsers[] = temp1.split(", "); //split string if between users comma or string
					ChatClientGui.onlineJList.setListData(currentUsers); //add users to list
				}
				else
				{
					ChatClientGui.conversationTextArea.append(msg + "\n"); //if its from client we show msg to clients
				}
			}
		}
	}
	//takes printWriter out object take users name and what user say
	public void SEND(String A)
	{
		out.println(ChatClientGui.userName + ": " + A);
		out.flush();
		ChatClientGui.messageTextField.setText(""); //clear text field of msg and ready for another input
	}
}
