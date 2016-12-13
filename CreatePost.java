import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import java.awt.Font;


public class CreatePost implements MouseListener,ActionListener
{
	JFrame frame;
	
	ImageIcon newMessage = new ImageIcon("NewMessage.jpg");
	ImageIcon back = new ImageIcon("Back.png");
    ImageIcon backpressed = new ImageIcon("Back_Pressed.png");
    ImageIcon add = new ImageIcon("Add.png");
    ImageIcon addpressed = new ImageIcon("Add_Pressed.png");
	
	JLabel addButton;
	JLabel addPressed;
    
	JLabel backButton;
	JLabel backPressed;
	
	JLabel messageBackground;
	JButton post;
	
	private JTextArea messageTextField;
	private JTextArea titleTextField;
	private JTextArea topicTextField;
	
	public CreatePost()
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
	private JPanel makePanel() 
	{
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173,780));
		panel.setLayout(null);
		
		messageTextField = new JTextArea(10,40);
		messageTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		messageTextField.setBounds(167, 490, 965, 149);
		messageTextField.setLineWrap(true);
		messageTextField.setWrapStyleWord(true);
		messageTextField.setBorder(border);
		panel.add(messageTextField);

		titleTextField = new JTextArea();
		titleTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleTextField.setBounds(321, 349, 812, 29);
		panel.add(titleTextField);
		titleTextField.setColumns(10);
		
		topicTextField = new JTextArea();
		topicTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topicTextField.setBounds(336, 142, 802, 29);
		panel.add(topicTextField);
		topicTextField.setColumns(10);
		
		backButton = new JLabel(back);
		backButton.setBounds(25, 650, 186, 80);
		backButton.addMouseListener(this);
		panel.add(backButton);
	
		backPressed = new JLabel(backpressed);
		backPressed.setBounds(25, 650, 186, 80);
		backPressed.addMouseListener(this);
		panel.add(backPressed);
		
		addButton = new JLabel(add);
		addButton.setBounds(989, 650, 149, 75);
		addButton.addMouseListener(this);
		panel.add(addButton);
	
		addPressed = new JLabel(addpressed);
		addPressed.setBounds(989, 650, 149, 75);
		addPressed.addMouseListener(this);
		panel.add(addPressed);
		
		JLabel CreatePostBackground = new JLabel(newMessage);
		CreatePostBackground.setBounds(0, 0, 1173, 795);
		panel.add(CreatePostBackground);
		
		
		
		// TODO Auto-generated method stub
		return panel;
	}
	@Override
	public void mouseClicked(MouseEvent a) 
	{
		// TODO Auto-generated method stub
		if ((a.getSource() == backPressed ) 
				|| (a.getSource() == backButton))
		{
			try 
			{
				frame.setVisible(false);
				new MainMenu();
				
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if ((a.getSource() == addPressed ) 
				|| (a.getSource() == addButton))
		{
			try 
			{
				frame.setVisible(false);
				new MainMenu();
				
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent b) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent c) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent d) 
	{
		if (d.getSource() == backButton)
		{
			backButton.setVisible(false);
			backPressed.setVisible(true);
		}
		if (d.getSource() == addButton)
		{
			addButton.setVisible(false);
			addPressed.setVisible(true);
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		if (e.getSource() == backButton)
		{
			backButton.setVisible(true);
			backPressed.setVisible(false);
		}
		
		if (e.getSource() == addButton)
		{
			addButton.setVisible(true);
			addPressed.setVisible(false);
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
