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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO message (message, topic, author, creation_date, last_edit, title) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, msg.getMessage());
            preparedStatement.setString(2, msg.getTopic());
            preparedStatement.setString(3, msg.getAuthor());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(6, msg.getTitle());
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
		connection = getConnection();
		try {
			ArrayList<Message> msgArr = new ArrayList<>();
			PreparedStatement pstmt = connection.prepareStatement("SELECT id, message, topic, create_date, last_edit, author, title FROM message");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String message = rs.getString(2);
				String topic = rs.getString(3);
				Timestamp create_date = rs.getTimestamp(4);
				Timestamp last_edit = rs.getTimestamp(5);
				String author = rs.getString(6);
				String title = rs.getString(7);
				Message msg = new Message(id, message, topic, create_date, last_edit, author, title);
				msgArr.add(msg);
			}
			return msgArr;
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
	@Override
	public void update(Message msg, Timestamp time) {
		connection = getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE message SET message = ?, author = ?, topic = ?, last_edit = ?, title = ? WHERE id = ?");
			pstmt.setString(1, msg.getMessage());
			pstmt.setString(2, msg.getAuthor());
			pstmt.setString(3, msg.getTopic());
			pstmt.setTimestamp(4, time);
			pstmt.setString(5, msg.getTitle());
			pstmt.setInt(6, msg.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
