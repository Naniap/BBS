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
import java.io.OutputStreamWriter;
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
	JButton btnSearchByAuthor;
	JButton btnSearchByTopic;
	private JTable table;
    private MainApp ma;
    private String userName;
    private OutputStreamWriter osw;
	public MainMenu(ArrayList<Message> messages, MainApp ma, String userName) throws IOException {
		this.messages = messages;
		this.ma = ma;
		this.userName = userName;
		osw = ma.getOutputStreamWriter();
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
		
		btnSearchByAuthor = new JButton();
		btnSearchByAuthor.setIcon(new ImageIcon("./src/img/Add.png"));
		btnSearchByAuthor.addActionListener(this);
		btnSearchByAuthor.setBounds(149, 545, 156, 39);
		panel.add(btnSearchByAuthor);
		
		btnSearchByTopic = new JButton();
		btnSearchByTopic.setIcon(new ImageIcon("./src/img/Back.png"));
		btnSearchByTopic.addActionListener(this);
		btnSearchByTopic.setBounds(149, 493, 126, 39);
		panel.add(btnSearchByTopic);

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
		table.setModel(updateModel());
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
			//do stuff
		}
		if (e.getSource() == btnSearchByAuthor) {
			System.out.println("Search by author...");
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
		if (a.getSource() == btnSearchByAuthor) {
			System.out.println("Search by author...");
			String author = JOptionPane.showInputDialog("Enter a user you which to search: ");
			try {
				osw.write("searchbyauthor\r\n");
				osw.write(author + "\r\n");
				osw.flush();
				messages = ma.getMessages();
				updateModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (a.getSource() == btnSearchByTopic) {
			
		}
	}
	public DefaultTableModel updateModel() {
		DefaultTableModel model =
				new DefaultTableModel(new Object[][] {},
						new String[] { "Topic", "Title", "Author", "Message", "Posted" }) {
					boolean[] columnEditables = new boolean[] { false, false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
				for (Message m : messages) {
					Object[] data = { m.getTopic(), m.getTitle(), m.getAuthor(), m.getMessage(), m.getPostedTime() };
					model.addRow(data);
				};
				return model;
	}
	public JFrame getFrame() {
		return frame;
	}
}