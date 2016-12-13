package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.Message;

import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class MainMenu implements MouseListener, ActionListener {
	ArrayList<Message> messages;
	JFrame frame;

	ImageIcon homescreen = new ImageIcon("./src/img/HomeScreen.png");
	ImageIcon LogOut = new ImageIcon("./src/img/LogOut.png");
	ImageIcon NewMessage = new ImageIcon("./src/img/new-message.png");
	ImageIcon DisplayAllPosts = new ImageIcon("./src/img/display-all.png");

	JLabel newMessage;
	JLabel displayAllPosts;

	JButton logOutButton;
	private JTable table;
    private MainApp ma;
    private String userName;
	public MainMenu(ArrayList<Message> messages, MainApp ma, String userName) throws IOException {
		this.messages = messages;
		this.ma = ma;
		this.userName = userName;
		frame = new JFrame();
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.setTitle("Bulletin Board Client");
		frame.setContentPane(makePanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private JPanel makePanel() throws IOException {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173, 780));
		panel.setLayout(null);

		newMessage = new JLabel(NewMessage);
		newMessage.setBounds(60, 75, 264, 237);
		newMessage.addMouseListener(this);

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
		DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "Topic", "Title", "Author", "Message", "Posted" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		for (Message m : messages) {
			Object[] data = { m.getTopic(), m.getTitle(), m.getAuthor(), m.getMessage(), m.getPostedTime() };
			tableModel.addRow(data);
		}
		table.setModel(tableModel);
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		            //open new window
		        }
		    }
		});

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
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == newMessage) {
			new CreatePost(ma, this.frame, userName);
			frame.setVisible(false);
		} else if (e.getSource() == displayAllPosts) {
			try {
				new MainApp("127.0.0.1");
			} catch (IOException a) {
				// TODO Auto-generated catch block
				a.printStackTrace();
			}
			frame.setVisible(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		if (a.getSource() == logOutButton) {
			frame.dispose();
			MainApp.exit();
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}