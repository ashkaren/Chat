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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class ChatClientGui
{
	//GUI globals for main window
	private static ChatClient client;
	public static String userName = "Unknown";
	public static JFrame MainWindow = new JFrame();
	private static JButton buttonConnect = new JButton();
	private static JButton buttonDisc = new JButton();
	private static JButton buttonSend = new JButton();
	private static JLabel messageLabel = new JLabel("Message: ");
	public static JTextField messageTextField = new JTextField(20);
	private static JLabel conversationLabel = new JLabel();
	public static JTextArea conversationTextArea = new JTextArea();
	private static JScrollPane conversationScrollPane = new JScrollPane();
	private static JLabel onlineJLabel = new JLabel();
	public static JList onlineJList = new JList();
	private static JScrollPane onlineScrollPane = new JScrollPane();
	private static JLabel loggedinLabel = new JLabel();
	private static JLabel loggedinBoxLabel = new JLabel();

	//GUI globals for login window
	public static JFrame loginWindow = new JFrame();
	public static JTextField userNameTextField = new JTextField(20);
	private static JButton buttonEnter = new JButton("Enter");
	private static JLabel userNameLabel = new JLabel("User Name: ");
	private static JPanel loginPanel = new JPanel(); 

	public static void main(String args[])
	{
		makeMainWindow();
		initializeButtons();
	}

	public static void Connect()
	{
		try
		{
			final int PORT = 7000;
			final String HOST = "127.0.0.1";
			Socket SOCK = new Socket (HOST, PORT); //connecting to server on port and host
			System.out.println("You connected to: " + HOST);

			client = new ChatClient(SOCK); //building instanse of ChatClient class, pass SOCK as argument
			PrintWriter out = new PrintWriter(SOCK.getOutputStream());
			out.println(userName); //send users name so they can be added to array list
			out.flush();
			Thread A = new Thread(client); //starting a new thread
			A.start();
		}
		catch(Exception A)
		{
			System.out.print(A);
			System.exit(0);
		}
	}
	//makes only connect button enabled while we are not connected yet
	public static void initializeButtons()
	{
		buttonSend.setEnabled(false);
		buttonDisc.setEnabled(false);
		buttonConnect.setEnabled(true);
	}
	public static void BuildloginWindow()
	{
		loginWindow.setTitle("Please Enter User Name");
		loginWindow.setSize(300, 100);
		loginWindow.setLocation(350, 200);
		loginWindow.setResizable(false);

		loginPanel = new JPanel();
		loginPanel.add(userNameLabel);
		loginPanel.add(userNameTextField);
		loginPanel.add(buttonEnter);
		loginWindow.add(loginPanel);

		LoginAction();
		loginWindow.setVisible(true);

	}
	public static void makeMainWindow()
	{
		MainWindow.setTitle(userName + "'s Chat Box"); //takes user name and makes title
		MainWindow.setSize(450, 500);
		MainWindow.setLocation(220, 180);
		MainWindow.setResizable(false);
		ConfigureMainWindow();
		MainWindow_Action();
		MainWindow.setVisible(true);
	}
	//configure main window, setting buttons, scroll panes, msg box, etc.
	public static void ConfigureMainWindow()
	{

		MainWindow.setSize(500, 320);
		MainWindow.getContentPane().setLayout(null);

		buttonSend.setBackground(new java.awt.Color(128, 128, 128));
		buttonSend.setForeground(new java.awt.Color(255, 255, 255));
		buttonSend.setText("SEND");
		MainWindow.getContentPane().add(buttonSend);
		buttonSend.setBounds(305, 290, 125, 25);

		buttonDisc.setBackground(new java.awt.Color(128, 128, 128));
		buttonDisc.setForeground(new java.awt.Color(255, 255, 255));
		buttonDisc.setText("DISCONNECT");
		MainWindow.getContentPane().add(buttonDisc);
		buttonDisc.setBounds(70, 290, 125, 25);

		buttonConnect.setBackground(new java.awt.Color(128, 128, 128));
		buttonConnect.setForeground(new java.awt.Color(255, 255, 255));
		buttonConnect.setText("CONNECT");
		MainWindow.getContentPane().add(buttonConnect);
		buttonConnect.setBounds(200, 290, 100, 25);

		messageLabel.setText("Message: ");
		MainWindow.getContentPane().add(messageLabel);
		messageLabel.setBounds(10, 260, 90, 20);

		messageTextField.setForeground(new java.awt.Color(0, 0, 255));
		messageTextField.requestFocus();
		MainWindow.getContentPane().add(messageTextField);
		messageTextField.setBounds(80, 255, 400, 30);

		conversationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		conversationLabel.setText("Conversation");
		MainWindow.getContentPane().add(conversationLabel);
		conversationLabel.setBounds(100, 40, 140, 16);

		conversationTextArea.setColumns(20);
		conversationTextArea.setFont(new java.awt.Font("Tahoma", 0, 12));
		conversationTextArea.setForeground(new java.awt.Color(0, 0, 255));
		conversationTextArea.setLineWrap(true);
		conversationTextArea.setRows(5);
		conversationTextArea.setEditable(false);

		conversationScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		conversationScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		conversationScrollPane.setViewportView(conversationTextArea);
		MainWindow.getContentPane().add(conversationScrollPane);
		conversationScrollPane.setBounds(10, 60, 330, 180);

		onlineJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		onlineJLabel.setText("Online");
		onlineJLabel.setToolTipText("");
		MainWindow.getContentPane().add(onlineJLabel);
		onlineJLabel.setBounds(350, 40, 130, 16);

		onlineJList.setForeground(new java.awt.Color(0, 0, 255));

		onlineScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		onlineScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		onlineScrollPane.setViewportView(onlineJList);
		MainWindow.getContentPane().add(onlineScrollPane);
		onlineScrollPane.setBounds(350, 60, 130, 180);

		loggedinLabel.setFont(new java.awt.Font("Times Roman", 0, 12));
		loggedinLabel.setText("Welcome: ");
		MainWindow.getContentPane().add(loggedinLabel);
		loggedinLabel.setBounds(150, 10, 140, 15);

		loggedinBoxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loggedinBoxLabel.setFont(new java.awt.Font("Times Roman", 0, 12));
		loggedinBoxLabel.setForeground(new java.awt.Color(0, 0, 255));
		loggedinBoxLabel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		MainWindow.getContentPane().add(loggedinBoxLabel);
		loggedinBoxLabel.setBounds(220, 10, 150, 20);

		MainWindow.getRootPane().setDefaultButton(buttonSend);//you can use keyboard Enter to send msg
	}
	//function adds action listener to Enter button
	public static void LoginAction()
	{
		buttonEnter.addActionListener(
			new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					ENTER();
				}
			}
		);
	}
	//adds name to Jlist object, changing title to user name, make visible send and disc. buttons, disable connect button
	public static void ENTER()
	{
		if(!userNameTextField.getText().equals(""))
		{
			userName = userNameTextField.getText().trim();
			loggedinBoxLabel.setText(userName);
			ChatServer.CurrentUsersArray.add(userName);
			MainWindow.setTitle(userName + "'s Chat Box");
			loginWindow.setVisible(false);
			buttonSend.setEnabled(true);
			buttonDisc.setEnabled(true);
			buttonConnect.setEnabled(false);
			Connect();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter the name ");
		}

	}


	//adding action listeners for buttons

	public static void MainWindow_Action()
	{
		buttonSend.addActionListener(
			new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					ACTION_buttonSend();
				}

			}
		);

		buttonDisc.addActionListener(
			new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					ACTION_buttonDisc();

				}
			}
		);
		buttonConnect.addActionListener(
			new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					BuildloginWindow();
				}
			}
		);

	}	

	//if message is not empty we are calling instance of ChatClient
	public static void ACTION_buttonSend()
	{
		if(!messageTextField.getText().equals(""))
		{
			client.SEND(messageTextField.getText()); //getting text from the box, passing it as argument to server and copied to the clients
			messageTextField.requestFocus(); //cursor back in msg area
			
		}
	}

	public static void ACTION_buttonDisc()
	{
		try
		{
			client.DISCONNECT(); //calls DISCONNECT method with instance of ChatClient
		}
		catch(Exception B)
		{
			B.printStackTrace();
		}
	}

}
