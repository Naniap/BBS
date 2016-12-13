package DAO;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {
	private static final long serialVersionUID = 4757922245852996182L;
	MessageDAOImpl mDAO = new MessageDAOImpl();
	private int id;
	private String author;
	public String title;
	private String message;
	public String topic;
	private Timestamp postedTime;
	private Timestamp lastEdit;

	public Message(int id, String message, String topic, Timestamp postedTime, Timestamp lastEdit, String author,
			String title) {
		this.id = id;
		this.author = author;
		this.message = message;
		this.title = title;
		this.topic = topic;
		this.postedTime = postedTime;
		this.lastEdit = lastEdit;
	}

	public Message(String message, String topic, String title, User author) {
		this.message = message;
		this.topic = topic;
		this.title = title;
		this.author = author.getUserName();
		this.postedTime = new Timestamp(System.currentTimeMillis());
		this.lastEdit = new Timestamp(System.currentTimeMillis());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		this.title = title;
		lastEdit = time;
		mDAO.update(this, time);
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		lastEdit = time;
		this.message = message;
		mDAO.update(this, time);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		lastEdit = time;
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
		lastEdit = time;
		this.author = author;
		mDAO.update(this, time);
	}

	public boolean titleMatches(String t) {
		return this.title.equals(t);
	}

	public String displayString() {
		String s = "Title: " + title + "\r\nTopic: " + topic + "\r\nAuthor: " + author + "\r\nDate: " + lastEdit
				+ "\r\n------------\r\n" + message + "\r\n";
		return s;

	}

}
