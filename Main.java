import java.sql.Timestamp;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		createUser();
	}
	public static void createUser() {
		Scanner sc = new Scanner(System.in);
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
		userDAO.closeConnection();
	}
}
