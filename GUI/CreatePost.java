package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import java.awt.Font;

public class CreatePost implements MouseListener, ActionListener {
	JFrame frame;

	ImageIcon newMessage = new ImageIcon("./src/img/NewMessage.jpg");
	ImageIcon back = new ImageIcon("./src/img/Back.png");
	ImageIcon backpressed = new ImageIcon("./src/img/Back_Pressed.png");
	ImageIcon add = new ImageIcon("./src/img/Add.png");
	ImageIcon addpressed = new ImageIcon("./src/img/Add_Pressed.png");

	JLabel addButton;
	JLabel addPressed;

	JLabel backButton;
	JLabel backPressed;

	JLabel messageBackground;
	JButton post;

	private JTextArea messageTextField;
	private JTextArea titleTextField;
	private JTextArea topicTextField;
	private MainApp ma;
	private JFrame prevJFrame;
	private String author;
	public CreatePost(MainApp ma, JFrame prevJFrame, String author) {
		this.ma = ma;
		this.prevJFrame = prevJFrame;
		this.author = author;
		frame = new JFrame();
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.setTitle("Bulletin Board Client");
		frame.setContentPane(makePanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private JPanel makePanel() {
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173, 780));
		panel.setLayout(null);
		
		JTextArea authorTextArea = new JTextArea();
		authorTextArea.setEditable(false);
		authorTextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authorTextArea.setColumns(10);
		authorTextArea.setBounds(376, 250, 812, 29);
		authorTextArea.setText(author);
		panel.add(authorTextArea);

		messageTextField = new JTextArea(10, 40);
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
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					prevJFrame.setVisible(true);
			}
		});

		// TODO Auto-generated method stub
		return panel;
	}

	@Override
	public void mouseClicked(MouseEvent a) {
		// TODO Auto-generated method stub
		if ((a.getSource() == backPressed) || (a.getSource() == backButton)) {
			frame.setVisible(false);
			prevJFrame.setVisible(true);
		}

		if ((a.getSource() == addPressed) || (a.getSource() == addButton)) {
			OutputStreamWriter osw = ma.getOutputStreamWriter();
			try {
				osw.write("postmessage\r\n");
				osw.write(titleTextField.getText() + "\r\n");
				osw.write(topicTextField.getText() + "\r\n");
				osw.write(messageTextField.getText() + "\r\n");
				osw.flush();
				JOptionPane.showMessageDialog(frame, "Your message has been successfully posted.");
				frame.setVisible(false);
				prevJFrame.setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent d) {
		if (d.getSource() == backButton) {
			backButton.setVisible(false);
			backPressed.setVisible(true);
		}
		if (d.getSource() == addButton) {
			addButton.setVisible(false);
			addPressed.setVisible(true);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == backButton) {
			backButton.setVisible(true);
			backPressed.setVisible(false);
		}

		if (e.getSource() == addButton) {
			addButton.setVisible(true);
			addPressed.setVisible(false);
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
