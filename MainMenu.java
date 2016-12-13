
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.Border;
<<<<<<< HEAD
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
=======
import javax.swing.*;
>>>>>>> d86ee28cf9ac84e2a2af66296a59bc612ab7d38e


public class MainMenu implements MouseListener,ActionListener
{
	JFrame frame;
	
	ImageIcon homescreen = new ImageIcon("HomeScreen.png");
	ImageIcon LogOut = new ImageIcon("LogOut.png");
	ImageIcon NewMessage = new ImageIcon("new-message.png");
	ImageIcon DisplayAllPosts = new ImageIcon("display-all.png");
	
	JLabel newMessage;
	JLabel displayAllPosts;

	JButton logOutButton;
<<<<<<< HEAD
	private JTable table;
=======
	private JLabel RecentPosts;
	private JScrollBar scrollBar;
>>>>>>> d86ee28cf9ac84e2a2af66296a59bc612ab7d38e
	
	String Author = "";
	String Topic = "";
	private JTextField typeTopic;
	private JTextField typeAuthor;
	
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
<<<<<<< HEAD
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(655, 219, 412, 437);
		panel.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setRowSelectionAllowed(false);
		table.setBackground(Color.WHITE);
		table.setBorder(null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableModel tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Topic", "Title", "Author", "Message", "Posted"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		for (Message m : messages) {
			Object[] data = { m.getTopic(), m.getTitle(), m.getAuthor(), m.getMessage(), m.getPostedTime() };
			tableModel.addRow(data);
		}
		table.setModel(tableModel);

=======
>>>>>>> d86ee28cf9ac84e2a2af66296a59bc612ab7d38e
		panel.add(newMessage);
		
		/*JLabel lblMessagesAreDisplayed = new JLabel("Messages Are Displayed Below");
		lblMessagesAreDisplayed.setBounds(768, 179, 224, 14);
		panel.add(lblMessagesAreDisplayed);
		*/
		
		RecentPosts = new JLabel("sjks");
		RecentPosts.setVerticalAlignment(SwingConstants.TOP);
		RecentPosts.setHorizontalAlignment(SwingConstants.LEFT);
		RecentPosts.setFont(new Font("Tahoma", Font.PLAIN, 20));
		RecentPosts.setBounds(677, 204, 392, 511);
		RecentPosts.setOpaque(false);
		panel.add(RecentPosts);	
		scrollBar = new JScrollBar();
		scrollBar.setBounds(1004, 143, 17, 48);
		RecentPosts.add(scrollBar);
		
		displayAllPosts = new JLabel(DisplayAllPosts);
		displayAllPosts.setBounds(250, 50, 275, 250);
		displayAllPosts.addMouseListener(this);
		panel.add(displayAllPosts);
		
		logOutButton = new JButton(LogOut);
		logOutButton.setBounds(47, 685, 186, 49);
		logOutButton.addActionListener(this);
		panel.add(logOutButton);
		
		typeTopic = new JTextField();
		typeTopic.setText("Type Topic");
		typeTopic.setBounds(317, 489, 146, 30);
		panel.add(typeTopic);
		typeTopic.setColumns(10);
		
		typeAuthor = new JTextField();
		typeAuthor.setText("Type Author");
		typeAuthor.setBounds(317, 553, 146, 30);
		panel.add(typeAuthor);
		typeAuthor.setColumns(10);
		
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
			frame.setVisible(false);
			new CreatePost();
			
		}
		else if (e.getSource() == displayAllPosts)
		{
			Topic = typeTopic.getText();
			Author = typeAuthor.getText();
			frame.setVisible(false);
			new DisplayAll(Topic, Author);
			
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
			try 
			{
				new BillBoardClient("127.0.0.1");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.setVisible(false);
			
		}
	}
}
