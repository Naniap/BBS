package FullConsoleVersion;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface MessageDAO {
	public void insert( Message msg );
	public void remove( Message msg );
	public void update( Message msg, Timestamp time );
	public ArrayList<Message> selectAll();
}
