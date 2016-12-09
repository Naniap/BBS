import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.Font;


public class BillBoardClient implements ActionListener
{
	private InputStream serverInput;
	private OutputStream serverOutput;
	private Scanner scan;
	private OutputStreamWriter osw;
	
	static Scanner Scan = new Scanner(System.in);


    String userName = "";
    String password = "";
    
    JPanel Panel;
    ImageIcon Main = new ImageIcon("./src/Main.jpg");
    ImageIcon LogIn = new ImageIcon("./src/LogIn.png");
    ImageIcon LogInPressed = new ImageIcon("./src/LogIn_Pressed.png");
    ImageIcon Add = new ImageIcon("./src/AddAccount.png");
    ImageIcon AddPressed = new ImageIcon("./src/AddAccount_Pressed.png");
    
    JButton LogInButton;
    JButton AddAccButton;
    JLabel exitButton;
    
    JFrame frame;
    private JTextField usernameField;
    private JTextField passwordField;
	
	public BillBoardClient(String serverAddress) throws IOException
	{	 	
		boolean cont = true;
		//try
		//{
		/*
			Socket socket = new Socket(serverAddress, 76);
			serverOutput = socket.getOutputStream();
			serverInput = socket.getInputStream();
			scan = new Scanner(serverInput);
			osw = new OutputStreamWriter(serverOutput);
	     */       
			//String message = scan.nextLine();
			//String message = "cool";
			//System.out.println(message);
			frame = new JFrame();
			frame.setLocation(0, 0);
			frame.setResizable(false);
			frame.setTitle("Bulletin Board Client");
			frame.setContentPane(makePanel());
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			
			//Scanner keyboard = new Scanner(System.in);
	        /*    
			while (cont == true)
			{
				
				message = keyboard.next();
				osw.write(message + "\r\n");
				osw.flush();
				if (message.equalsIgnoreCase("Exit\n") || message == null)
					cont = false;
				if (!(scan.hasNext()))
					break;
				message = scan.nextLine();
				System.out.println(message);
			}
	         
			socket.close();
			keyboard.close();
		}
		catch (IOException e)
		{
			System.out.println("error connecting to Server");
		}*/   
	}
	
	public JPanel makePanel() throws IOException
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173,780));
		panel.setLayout(null);
		
		
		JLabel InvalidMessage = new JLabel("");
		InvalidMessage.setBounds(511, 484, 183, 14);
		panel.add(InvalidMessage);
		
		LogInButton = new JButton(LogIn);
		LogInButton.setBounds(477, 521, 217, 47);
		LogInButton.addActionListener(this);
		panel.add(LogInButton);
		
		AddAccButton = new JButton(Add);
		AddAccButton.setBounds(477, 579, 217, 47);
		AddAccButton.addActionListener(this);
		panel.add(AddAccButton);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameField.setBounds(387, 319, 397, 47);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setBounds(387, 426, 397, 47);
		panel.add(passwordField);
		passwordField.setColumns(10);
		
		JLabel background = new JLabel(Main);
		background.setBounds(0, 0, 1173, 780);
		panel.add(background);
	
		
		return panel;
	}
	
    public static void main(String[] args) throws IOException 
    {
    	 //String serverAddress = JOptionPane.showInputDialog("Please"
			//	 + " enter a Server to connect (DNS name or IP)");
    	 new BillBoardClient("127.0.0.1");
    }


	@Override
	public void actionPerformed(ActionEvent e) 
	{  
	     if(e.getSource() == LogInButton)
	     {
	    	 String uName = usernameField.getText();
	    	 String pWord = passwordField.getText();
	     }
		 
	     else if(e.getSource() == AddAccButton)
	     {
	    	 
	     }
	}
}

