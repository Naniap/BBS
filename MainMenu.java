
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.Border;


public class MainMenu implements MouseListener,ActionListener
{
	JFrame frame;
	
	ImageIcon homescreen = new ImageIcon("./src/HomeScreen.png");
	ImageIcon LogOut = new ImageIcon("./src/LogOut.png");
	ImageIcon NewMessage = new ImageIcon("./src/new-message.png");
	ImageIcon DisplayAllPosts = new ImageIcon("./src/display-all.png");
	
	JLabel newMessage;
	JLabel displayAllPosts;
	
	JButton logOutButton;
	
	public MainMenu() throws IOException
	{
		frame = new JFrame();
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.setTitle("Bulletin Board Client");
		frame.setContentPane(makePanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private JPanel makePanel() throws IOException
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173,780));
		panel.setLayout(null);
		
		newMessage = new JLabel(NewMessage);
		newMessage.setBounds(60, 75, 264, 237);
		newMessage.addMouseListener(this);
		panel.add(newMessage);
		
		displayAllPosts = new JLabel(DisplayAllPosts);
		displayAllPosts.setBounds(250, 50, 275, 250);
		displayAllPosts.addMouseListener(this);
		panel.add(displayAllPosts);
		
		logOutButton = new JButton(LogOut);
		logOutButton.setBounds(47, 685, 186, 49);
		logOutButton.addActionListener(this);
		panel.add(logOutButton);
		
		JLabel MainMenuBackground = new JLabel(homescreen);
		MainMenuBackground.setBounds(0, 0, 1173, 795);
		panel.add(MainMenuBackground);
		
		return panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() == newMessage)
		{
			new CreatePost();
			frame.setVisible(false);
		}
		else if (e.getSource() == displayAllPosts)
		{
			try 
			{
				new BillBoardClient("127.0.0.1");
			} 
			catch (IOException a) 
			{
				// TODO Auto-generated catch block
				a.printStackTrace();
			}
			frame.setVisible(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent a) 
	{
		// TODO Auto-generated method stub
		if (a.getSource() == logOutButton)
		{
			frame.dispose();
			BillBoardClient.exit();
		}
	}
	public JFrame getFrame() {
		return frame;
	}
}
