import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;


public class DisplayAll implements MouseListener
{
	JFrame frame;
	
	ImageIcon DisplayAllMessages = new ImageIcon("Messages.jpg");
	ImageIcon back = new ImageIcon("Back.png");
    ImageIcon backpressed = new ImageIcon("Back_Pressed.png");
	
    JLabel backButton;
	JLabel backPressed;
	
	String Topic = "", Author = ""; 
	
	private JTextField filterField;
	
	public DisplayAll(String topic, String author)
	{
		if( !topic.equalsIgnoreCase("type topic"))
			Topic = topic;
		if( !author.equalsIgnoreCase("type author"))
			Author = author;
		
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
		System.out.println(Topic);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173,780));
		panel.setLayout(null);
		
		JLabel Topics = new JLabel("aaaaaaaa");
		Topics.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Topics.setVerticalAlignment(SwingConstants.TOP);
		Topics.setBounds(179, 182, 215, 548);
		panel.add(Topics);
			
		JLabel Authors = new JLabel("New label");
		Authors.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Authors.setVerticalAlignment(SwingConstants.TOP);
		Authors.setBounds(462, 182, 215, 548);
		panel.add(Authors);
		
		JLabel lblSup = new JLabel("sup");
		lblSup.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSup.setHorizontalAlignment(SwingConstants.LEFT);
		lblSup.setVerticalAlignment(SwingConstants.TOP);
		lblSup.setBounds(797, 182, 215, 548);
		panel.add(lblSup);
	
		
		backButton = new JLabel(back);
		backButton.setBounds(25, 650, 186, 80);
		backButton.addMouseListener(this);
		panel.add(backButton);
	
		backPressed = new JLabel(backpressed);
		backPressed.setBounds(25, 650, 186, 80);
		backPressed.addMouseListener(this);
		panel.add(backPressed);
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(326, 91, 80, 23);
		panel.add(btnFilter);
		
		filterField = new JTextField();
		filterField.setText("Type Author/Topic");
		filterField.setBounds(436, 92, 241, 20);
		panel.add(filterField);
		filterField.setColumns(10);
		
		JLabel CreatePostBackground = new JLabel(DisplayAllMessages);
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
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent d) 
	{
		// TODO Auto-generated method stub
		if (d.getSource() == backButton)
		{
			backButton.setVisible(false);
			backPressed.setVisible(true);
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
		
	}
}
