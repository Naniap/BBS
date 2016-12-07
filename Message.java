import java.sql.Timestamp;

public class Message {
	MessageDAOImpl mDAO = new MessageDAOImpl();
	private int id;
	private String author;
	private String message;
	private String topic;
	private Timestamp postedTime;
	private Timestamp lastEdit;
	public Message(int id, String message, String topic, Timestamp postedTime, Timestamp lastEdit, String author) {
		this.id = id;
		this.author = author;
		this.message = message;
		this.topic = topic;
		this.postedTime = postedTime;
		this.lastEdit = lastEdit;
	}
	public int getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.message = message;
		mDAO.update(this, time);
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.topic = topic;
		mDAO.update(this, time);
	}
	public Timestamp getPostedTime() {
		return postedTime;
	}
	public Timestamp getLastEdit() {
		return lastEdit;
	}
	public void setLastEdit() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.lastEdit = time;
		mDAO.update(this, time);
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.author = author;
		mDAO.update(this, time);
	}
	


}
