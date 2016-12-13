import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CreatePost implements MouseListener,ActionListener
{
	JFrame frame;
	
	ImageIcon newMessage = new ImageIcon("NewMessage.jpg");
	ImageIcon back = new ImageIcon("Back.png");
    ImageIcon backpressed = new ImageIcon("Back_Pressed.png");
	
	JLabel backButton;
	JLabel backPressed;
	
	JButton post;
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
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173,780));
		panel.setLayout(null);
		
		backButton = new JLabel(back);
		backButton.setBounds(25, 650, 186, 80);
		backButton.addMouseListener(this);
		panel.add(backButton);
	
		backPressed = new JLabel(backpressed);
		backPressed.setBounds(25, 650, 186, 80);
		panel.add(backPressed);
		
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
			return;
		
	}
	@Override
	public void mouseEntered(MouseEvent b) 
	{
		// TODO Auto-generated method stub
		if (b.getSource() == backButton)
		{
			backButton.setVisible(false);
			backPressed.setVisible(true);
		}
	}
	@Override
	public void mouseExited(MouseEvent c) 
	{
		// TODO Auto-generated method stub
		if (c.getSource() == backButton)
		{
			backButton.setVisible(true);
			backPressed.setVisible(false);
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
