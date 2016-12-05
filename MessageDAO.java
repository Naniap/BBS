import java.util.ArrayList;

public interface MessageDAO {
	public void insert( Message msg );
	public void remove( Message msg );
	public ArrayList<Message> selectAll();
}
