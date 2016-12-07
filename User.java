import java.sql.Timestamp;

public class User {
	UserDAOImpl uDAO = new UserDAOImpl();
	private int id;
	private String userName;
	private Timestamp lastLogin;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		uDAO.update(this, lastLogin);
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
		uDAO.update(this, lastLogin);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
		uDAO.update(this, lastLogin);
	}
	public int getId() {
		return id;
	}
	
}
