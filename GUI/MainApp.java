package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DAO.Message;

import java.awt.Font;

public class MainApp implements ActionListener {
	private Socket sock = new Socket("localhost", 666);
	private InputStream serverInput = sock.getInputStream();
	private OutputStream serverOutput = sock.getOutputStream();
	private Scanner scan = new Scanner(serverInput);
	private OutputStreamWriter osw = new OutputStreamWriter(serverOutput);
	private ObjectOutputStream oos = new ObjectOutputStream(serverOutput);
	private ObjectInputStream ois = new ObjectInputStream(serverInput);
	ArrayList<Message> messages;
	public boolean loggedIn = false;
	static Scanner Scan = new Scanner(System.in);

	String userName = "";
	String password = "";

	JPanel Panel;
	ImageIcon Main = new ImageIcon("./src/img/Main.jpg");
	ImageIcon LogIn = new ImageIcon("./src/img/LogIn.png");
	ImageIcon LogInPressed = new ImageIcon("./src/img/LogIn_Pressed.png");
	ImageIcon Add = new ImageIcon("./src/img/AddAccount.png");
	ImageIcon AddPressed = new ImageIcon("./src/img/AddAccount_Pressed.png");

	JButton LogInButton;
	JButton AddAccButton;
	JLabel exitButton;

	static JFrame frame;
	private JTextField usernameField;
	private JTextField passwordField;

	public MainApp(String serverAddress) throws IOException {
		boolean cont = true;
		frame = new JFrame();
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.setTitle("Bulletin Board Client");
		frame.setContentPane(makePanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, "Are you sure to close this window?", "Really Closing?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		while (true) {
			String message = scan.nextLine();
			System.out.println(message);
			if (message.contains("Logged in as")) {
				loggedIn = true;
				osw.write("getarray\r\n");
				osw.flush();
				try {
					scan.nextLine();
					messages = (ArrayList<Message>) ois.readObject();
					System.out.println(messages.size());
					MainMenu mm = new MainMenu(messages);
					mm.getFrame().setVisible(true);
					frame.setVisible(false);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public JPanel makePanel() throws IOException {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1173, 780));
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

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setBounds(387, 426, 397, 47);
		panel.add(passwordField);
		passwordField.setColumns(10);

		JLabel background = new JLabel(Main);
		background.setBounds(0, 0, 1173, 780);
		panel.add(background);

		return panel;
	}

	public static void main(String[] args) throws IOException {
		// String serverAddress = JOptionPane.showInputDialog("Please"
		// + " enter a Server to connect (DNS name or IP)");
		new MainApp("127.0.0.1");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == LogInButton) {
			String uName = usernameField.getText();
			String pWord = passwordField.getText();
			String option = "signin";
			try {
				osw.write(option + "\r\n");
				osw.write(option + "\r\n");
				osw.write(uName + "\r\n");
				osw.write(pWord + "\r\n");
				osw.flush();
				// Object o = ois.readObject();
			} catch (IOException ex /* | ClassNotFoundException ex */) {
				ex.printStackTrace();
			}
		}

		else if (e.getSource() == AddAccButton) {

		}
	}

	public static void exit() {
		frame.dispose();
	}
}