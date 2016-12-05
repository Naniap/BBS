import java.sql.Timestamp;

public class Message {
	private int id;
	private String author;
	private String message;
	private String topic;
	private Timestamp postedTime;
	private Timestamp lastEdit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Timestamp getPostedTime() {
		return postedTime;
	}
	public void setPostedTime(Timestamp postedTime) {
		this.postedTime = postedTime;
	}
	public Timestamp getLastEdit() {
		return lastEdit;
	}
	public void setLastEdit(Timestamp lastEdit) {
		this.lastEdit = lastEdit;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	


}
