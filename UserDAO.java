import java.sql.Timestamp;

public interface UserDAO {
	public void insert(User user);
	public void update(User user, Timestamp time);
	public User login(String user, String password);
}
