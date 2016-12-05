import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageDAOImpl implements MessageDAO {
	private final static String password = "Ax20A]sd[fSds";
	private final static String address = "phantomelite.com";
	private final static String dbName = "BBS";
	private final static String dbUser = "westfield";
	private final static String connectionString = "jdbc:mysql://" + address + ":3306/" + dbName;
	Connection connection = null;
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection == null)
                connection = DriverManager.getConnection(connectionString, dbUser, password);
 
        } catch (ClassNotFoundException e) {
 
            e.printStackTrace();
             
        } catch (SQLException e) {
             
            e.printStackTrace();
             
        }
        return connection;
    }
	@Override
	public void insert(Message msg) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO message (message, topic, author, creation_date, last_edit) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1,  msg.getMessage());
            preparedStatement.setString(2, msg.getTopic());
            preparedStatement.setString(3, msg.getAuthor());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void remove(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Message> selectAll() {
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT id, message, topic, author FROM message");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("ID: " + rs.getInt(1) + " message: " + rs.getString(2) + " topic: " + rs.getString(3) + " author: " + rs.getString(4));
				return null;
			}
		}
		catch (SQLException e) {}
		return null;
	}
    public void closeConnection(){
        try {
              if (connection != null) {
                  connection.close();
              }
            } catch (Exception e) { 
            }
    }
}
