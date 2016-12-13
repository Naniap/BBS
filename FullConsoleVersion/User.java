import java.sql.Timestamp;

public class User {
	UserDAOImpl uDAO = new UserDAOImpl();
	private int id;
	public String name;
	private Timestamp lastLogin;
	private String password;
	public User(int id, String name, String password, Timestamp lastLogin) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.lastLogin = lastLogin;
	}
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
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
		return name;
	}
	public void setUserName(String userName) {
		this.name = userName;
		uDAO.update(this, lastLogin);
	}
	public int getId() {
		return id;
	}
	public boolean nameMatches(String n)
	{
		return (this.name.equalsIgnoreCase(n));
	}
	
	
}
