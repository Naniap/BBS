import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;



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
    	connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO login (username, password, lastlogin) VALUES (?, ?, ?)");
            preparedStatement.setString(1,  user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
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
	@Override
	public void update(User user, Timestamp time) {
		connection = getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE user SET username = ?, password = ?, lastlogin = ? WHERE id = ?");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setTimestamp(3, user.getLastLogin());
			pstmt.setInt(4, user.getId());
			pstmt.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}
	@Override
	public User login(String user, String password) {
		connection = getConnection();
		try {
			System.out.println(password);
			PreparedStatement pstmt = connection.prepareStatement("SELECT id, username, password, lastlogin FROM login WHERE username = ? AND password = ?");
			pstmt.setString(1, user);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4));
			return null;
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	@Override
	public ArrayList<User> selectAll() {
		connection = getConnection();
		try {
			ArrayList<User> usrArr = new ArrayList<>();
			PreparedStatement pstmt = connection.prepareStatement("SELECT id, username, password, lastlogin FROM login");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				Timestamp lastLogin = rs.getTimestamp(4);
				User usr = new User(id, name, password, lastLogin);
				usrArr.add(usr);
			}
			return usrArr;
		}
		catch (SQLException e) {}
		return null;
	}
 
}