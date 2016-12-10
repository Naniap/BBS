import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddAccount implements ActionListener,MouseListener
{
	private InputStream serverInput;
	private OutputStream serverOutput;
	private Scanner scan;
	private OutputStreamWriter osw;
	
	static Scanner Scan = new Scanner(System.in);

    String userName = "";
    String password = "";
    
    JPanel Panel;
    
    ImageIcon Background = new ImageIcon("AddAccount.jpg");
    ImageIcon Add = new ImageIcon("AddAccount.png");
    ImageIcon back = new ImageIcon("Back.png");
    ImageIcon backpressed = new ImageIcon("Back_Pressed.png");
    
    JButton AddAccount;
    JLabel backButton;
    JLabel backPressed;
    
    JLabel exitButton;
    JLabel InvalidMessage;
    
    JFrame frame;
    
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
	
    public AddAccount()
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

	public JPanel makePanel() 
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173,780));
		panel.setLayout(null);
		
		AddAccount = new JButton(Add);
		AddAccount.setBounds(480, 552, 212, 44);
		panel.add(AddAccount);
		
		
		backButton = new JLabel(back);
		backButton.setBounds(25, 650, 186, 80);
		backButton.addMouseListener(this);
		panel.add(backButton);
	
		backPressed = new JLabel(backpressed);
		backPressed.setBounds(25, 650, 186, 80);
		panel.add(backPressed);
		
		usernameField = new JTextField();
		usernameField.setBounds(387, 263, 402, 44);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(387, 473, 402, 44);
		panel.add(passwordField);
		passwordField.setColumns(10);
		
		confirmPasswordField = new JTextField();
		confirmPasswordField.setBounds(387, 368, 402, 44);
		panel.add(confirmPasswordField);
		confirmPasswordField.setColumns(10);
		
		JLabel label = new JLabel(Background);
		label.setBounds(0, 0, 1173, 780);
		panel.add(label);
		
		// TODO Auto-generated method stub
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == AddAccount)
		{
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent o) 
	{
		// TODO Auto-generated method stub
		if (o.getSource() == backButton)
		{
			try 
			{
				new BillBoardClient("127.0.0.1");
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			backButton.setVisible(false);
			frame.setVisible(false);
				
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
