import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import javax.activation.MailcapCommandMap;

public class Main {

	public static void main(String[] args) {
		createUser();
	}
	public static void createUser() {
		/*Scanner sc = new Scanner(System.in);
		User user = new User();
		System.out.print("Enter a new username: ");
		user.setUserName(sc.nextLine());
		user.setLastLogin(new Timestamp(System.currentTimeMillis()));	
		System.out.print("Enter a password: ");
		String password = sc.nextLine();
		user.setPassword(SQLConnect.sha512_Encrpyt(password, password.substring(1)));
		
		UserDAOImpl userDAO = new UserDAOImpl();
		userDAO.getConnection();
		
		userDAO.insert(user);
		userDAO.closeConnection();*/
		
		MessageDAOImpl msgDAO = new MessageDAOImpl();
		//msgDAO.getConnection();
		ArrayList<Message> msg = msgDAO.selectAll();
		if (msg == null)
		{
			System.out.println("Array is empty, or failed to retrieve from database.");
		}
		else {
			for (Message m : msg) {
				/*m.setAuthor("Mike");
				m.setMessage("Hi this is a test message!");
				m.setTopic("Hi this is a test topic!");*/
				m.setLastEdit();
				System.out.println("ID: " + m.getId() + " Message: " + m.getMessage() + " Author: " + m.getAuthor() + " Topic: " + m.getTopic() + " Creation Date: " + m.getPostedTime() + " Last Edited: " + m.getLastEdit());
			}
		}
		msgDAO.closeConnection();
		
	}
}
