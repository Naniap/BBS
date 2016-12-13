import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SQLConnect {
	private final static String password = "Ax20A]sd[fSds";
	private final static String address = "phantomelite.com";
	private final static String dbName = "BBS";
	private final static String dbUser = "westfield";
	private final static String connectionString = "jdbc:mysql://" + address + ":3306/" + dbName;

	public static void main(String[] args) {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		 } catch (ClassNotFoundException e) {

		System.out.println("Missing MySQL Drive.");
		    e.printStackTrace();
		    return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(connectionString, dbUser, password);	

		} catch (SQLException e) {
		    System.out.println("Connection Failed!");
		    e.printStackTrace();
		    return;
		}

		if (connection != null) {
		    System.out.println("Successful connection to database.");
		    String query = "INSERT into login (username, password, lastlogin) values (?, ?, ?)";
		    try {
				PreparedStatement prepStmt = connection.prepareStatement(query);
				prepStmt.setString(1, "test");
				prepStmt.setString(2, sha512_Encrpyt("testpass", "t")); //encrpyt password sha512
				prepStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				prepStmt.execute();
				System.out.println("Prepared Statement Execution Successful!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    
		} else {
		    System.out.println("Failed to make connection!");
		}

	}
	public static String sha512_Encrpyt(String passwordToHash, String salt){
		String generatedPassword = null;
		    try {
		         MessageDigest md = MessageDigest.getInstance("SHA-512");
		         md.update(salt.getBytes("UTF-8"));
		         byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++){
		            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }
		         generatedPassword = sb.toString();
		        } 
		       catch (NoSuchAlgorithmException e){
		        e.printStackTrace();
		       }
		     	catch (UnsupportedEncodingException e) {
		     		e.printStackTrace();
		     	}
		    return generatedPassword;
	}

}
