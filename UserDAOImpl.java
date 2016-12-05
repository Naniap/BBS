import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class UserDAOImpl implements UserDAO {
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
    public void insert(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO login (username, password, lastlogin) VALUES (?, ?, ?)");
            preparedStatement.setString(1,  user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setTimestamp(3, user.getLastLogin());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
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