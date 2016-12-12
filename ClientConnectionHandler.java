import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// TODO this class is a stub; Dan said he had a cch written so this is the placeholder.
// for my BBServer to work we need these 3 methods (including the constructor)


public class ClientConnectionHandler extends Thread {
	
	private Message newMessage;
	private User newUser;
	
	
	
	
	public boolean hasMessage;
	public boolean hasNewUser;
	public boolean hasLogout;
	
	
	private User currentUser;
	private User tempUser;
	private User userToLogOut;
	private boolean isLoggedIn;
	
	private ArrayList<User> userList;
	private ArrayList<Message> messageList;
	
	private ArrayList<User> loggedInUserList;
	
	private Socket connection;
    private InputStream clientInput;
    private OutputStream clientOutput;
    private Scanner scanner;
    private OutputStreamWriter osw;
    private ObjectOutputStream oos;
    private String option = "";
    
    private String uName = "";
    private String uPass = "";
    
    private String mTitle = "";
    private String mTopic = "";
    private String mContent = "";
    private User mAuthor = null;
    private Date mDate = null;
    
    private int displaySize = 0;
    private boolean validChoice = false;

	public ClientConnectionHandler(Socket clientConnection, ArrayList<User> uL, ArrayList<Message> mL, ArrayList<User> lIU) 
	{
		connection = clientConnection;
		this.userList = uL;
		this.loggedInUserList = lIU;
		this.messageList = mL;
		
		this.newMessage = null;
		this.currentUser = null;
		
		this.hasMessage = false;
		this.isLoggedIn = false;
		this.hasNewUser = false;
        
        try
        {
            clientInput = connection.getInputStream();
            clientOutput = connection.getOutputStream();
            scanner = new Scanner(clientInput);
            osw = new OutputStreamWriter(clientOutput);
            oos = new ObjectOutputStream(clientOutput);
        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client");
        }
        
		
	}

	@Override
	public void run() 
	{
		try
        {
            osw.write("Welcome to BBServer Console.\r\n");
            osw.write("This is the development, pre-GUI configuration for testing\r\n");
            
            osw.flush();
                               
            //loop to continue asking options
            while(!option.equalsIgnoreCase("exit"))
            {
            	displayOptionMenu();
            	if (!scanner.hasNextLine())
            		return;
                option = scanner.nextLine();
            	
            	processOption(option);
            }

        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client IN RUN");
            e.printStackTrace();
        }

	}

	public boolean isConnected()  throws IOException
    {
        boolean stillConnected = true;
        stillConnected = isAlive();
        return stillConnected;
    }
	
	public boolean hasMessage()
	{
		return this.hasMessage;
	}
	
	public Message getMessage()
	{
		return this.newMessage;
	}
	
	public void setMessage(String content, String topic, String title, User author, Date date)
	{
		//this.newMessage = new Message(content, topic, title, author, date );
		//this.hasMessage = true;
		messageList.add(newMessage);
	}
	
	public void clearMessage()
	{
		this.newMessage = null;
		this.hasMessage = false;
	}
	
	public User getNewUser()
	{
		return this.newUser;
	}
	
	public void setNewUser(User u)
	{
		this.newUser = u;
		this.hasNewUser = true;
		userList.add(tempUser);
	}
	
	public void clearNewUser()
	{
		this.newUser = null;
		this.hasNewUser = false;
	}
	
	public boolean hasNewUser()
	{
		return this.hasNewUser;
	}
	
	public boolean containsUser(User u)
	{
		for(int x = 0; x < userList.size(); x++)
		{
			if(u.nameMatches(userList.get(x).name))
				return true;
		}
		return false;
	}
	
	public boolean containsMessage( Message m)
	{
		for(int x = 0; x < messageList.size(); x++)
		{
			if(m.titleMatches(messageList.get(x).topic))
				return true;
		}
		return false;
	}
	
	public boolean containsLoggedInUser(User u)
	{
		
		System.out.println(loggedInUserList.size());
		
		
		for(int x = 0; x < loggedInUserList.size(); x++)
		{
			if(u.nameMatches(loggedInUserList.get(x).name))
				return true;
		}
		return false;
	}
	
	public int getUserListSize()
	{
		return userList.size();
	}
	
	public int getMessageListSize()
	{
		return messageList.size();
	}
	
	public void setUserList(ArrayList<User> uL)
	{
		this.userList = uL;
	}
	
	public void setMessageList(ArrayList<Message> mL)
	{
		this.messageList = mL;
	}
	
	
	//menu options...
	public void signInOption() throws IOException
	{
		osw.write("Log in:\r\n");
		osw.flush();
		
		if(!isLoggedIn)
		{
			osw.write("User name: ");
    		osw.flush();
    		uName = scanner.nextLine();
    		
    		osw.write("\r\nPassword: ");
    		osw.flush();
    		uPass = scanner.nextLine();
    		UserDAOImpl uDAO = new UserDAOImpl();
    		if (uName.length() <= 0) {
    			osw.write("Invalid username.\r\n");
    			osw.flush();
    		}
    		User tempUser = uDAO.login(uName,SQLConnect.sha512_Encrpyt(uPass, uName.substring(1)));
    		//tempUser = new User (uName, uPass);

    		if(containsLoggedInUser(tempUser))
    		{
    			osw.write("\r\nSorry, "+tempUser.name+" is already logged in.\r\n");
        		osw.flush();
    		}
    		else if(tempUser != null)
    		{
    			currentUser = tempUser;
    			isLoggedIn = true;
    			loggedInUserList.add(currentUser);
    			osw.write("\r\nLogged in as " + currentUser.name + ".\r\n");
        		osw.flush();
        		//oos.writeObject(currentUser);
        		//oos.flush();
        		
    		}
    		else
    		{
    			osw.write("\r\nSorry, user name or password was incorrect. \r\n");
        		osw.flush();
    		}
		}
		else
		{
			osw.write("\r\nYou are already logged in as "+currentUser.name+ ".\r\n");
    		osw.flush();
		}
	}
	
	public void signUpOption() throws IOException
	{
		if (isLoggedIn) {
			osw.write("\r\nYou're already logged in. \r\n");
			osw.flush();
			return;
		}
		osw.write("Sign up:\r\n");
		osw.write("User name: ");
		osw.flush();
		uName = scanner.nextLine();
		osw.write("\r\nPassword: ");
		osw.flush();
		uPass = scanner.nextLine();
		
		tempUser = new User (uName, SQLConnect.sha512_Encrpyt(uPass, uName.substring(1)));
		//Create a new username here on database.
		UserDAOImpl uDAO = new UserDAOImpl();
		uDAO.insert(tempUser);
		if(containsUser(tempUser))
		{
    		osw.write("\r\nSorry, this user already exists. \r\n");
    		osw.flush();
		}
		else
		{
			osw.write("\r\nUser "+tempUser.name+" added.\r\n");
    		osw.flush();
    		
    		setNewUser(tempUser);
		}
	}
	
	public void signOutOption() throws IOException
	{
		osw.write("Log out:\r\n");
		osw.flush();
		
		if(isLoggedIn)
		{
			logUserOut();
			
			osw.write("\r\nSigned out.\r\n");
    		osw.flush();
		}
		else
		{
			osw.write("\r\nSorry, you are not signed in.\r\n");
    		osw.flush();
		}
	}
	
	public void postMessageOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Post message:\r\n");
			osw.flush();
			
			osw.write("\r\nMessage title: ");
			osw.flush();
			mTitle = scanner.nextLine();
			
			osw.write("\r\nMessage topic: ");
			osw.flush();
			mTopic = scanner.nextLine();
			
			osw.write("\r\nMessage content: ");
			osw.flush();
			mContent = scanner.nextLine();
			
			mDate = new Date();
			mAuthor = this.currentUser;
			
			//setMessage(mContent, mTopic, mTitle, mAuthor, mDate);
			//we want to create a new message in here that is posted to the databases as well as added to the array list
			MessageDAOImpl mDAO = new MessageDAOImpl();
			Message msg = new Message(mContent, mTopic, mTitle, mAuthor);
			mDAO.insert(msg);
			messageList.add(msg);
			osw.write("\r\nMessage \""+ mTitle + "\" posted.\r\n");
			osw.flush();
		}
		else
		{
			osw.write("Please Sign in to post messages.\r\n");
			osw.flush();
		}
		
		
		
	}
	
	public void viewMessagesOption() throws IOException//DEPRECATED
	{
		
		if(isLoggedIn)
		{
			osw.write("Display messages:\r\n");
			osw.flush();
			
			if(messageList.size() < 10)
				displaySize = messageList.size();
			else
				displaySize = 10;
			
			if(displaySize == 0)
			{
				osw.write("No messages to display.\r\n");
				osw.flush();
			}
			else
			{
				for(int x = 0; x < displaySize; x++)
				{
					//this will probably show more details.
					osw.write((x+1) + " " + messageList.get(x).title + "\r\n");
				}
				osw.write("\r\n Please choose a message, 1 - "+ displaySize + "\r\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < displaySize; x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\r\n"+messageList.get(x).displayString()+"\r\n");
						osw.flush();
						validChoice = true;
					}
				if(!validChoice)
				{
					invalidOption();
				}
			}
		}
		else
		{
			osw.write("Please Sign in to display messages.\r\n");
			osw.flush();
		}
	}
	
	public void viewRecentMessagesOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Display recent messages:\r\n");
			osw.flush();
			
			ArrayList<Message> recentMessages = new ArrayList<Message>();
			for(int x = 0; x < 10; x++)
			{
				//TODO I want to rewrite this to incorporate the timeStamp.
				// right now it just takes the messages from the "top" of the arraylist.
				try{
					recentMessages.add(messageList.get(messageList.size() - (1 + x)));
				}
				catch( IndexOutOfBoundsException e)
				{}
			}
			
			if(recentMessages.size() == 0)
			{
				osw.write("No messages to display.\r\n");
				osw.flush();
			}
			else
			{
				osw.write("\r\n Most recent messages by date\r\n");
				osw.flush();
				for(int x = 0; x < recentMessages.size(); x++)
				{
					//this will probably show more details. TODO
					osw.write((x+1) + " " + recentMessages.get(x).getPostedTime() + " : "+ recentMessages.get(x).title +"\r\n");
				}
				osw.write("\r\n Please choose a message, 1 - "+ recentMessages.size() + "\r\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < recentMessages.size(); x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\r\n"+recentMessages.get(x).displayString()+"\r\n");
						osw.flush();
						validChoice = true;
					}
				if(!validChoice)
				{
					invalidOption();
				}
			}
			
			
		}
		else
		{
			osw.write("Please Sign in to display messages.\r\n");
			osw.flush();
		}
	}

	public void viewAllMessagesOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Display all messages:\r\n");
			osw.flush();
			
			displaySize = messageList.size();
			
			if(displaySize == 0)
			{
				osw.write("No messages to display.\r\n");
				osw.flush();
			}
			else
			{
				for(int x = 0; x < displaySize; x++)
				{
					//this will probably show more details.
					osw.write((x+1) + " " + messageList.get(x).title + "\r\n");
				}
				osw.write("\r\n Please choose a message, 1 - "+ displaySize + "\r\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < displaySize; x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\r\n"+messageList.get(x).displayString()+"\r\n");
						osw.flush();
						validChoice = true;
					}
				if(!validChoice)
				{
					invalidOption();
				}
			}
		}
		else
		{
			osw.write("Please Sign in to display messages.\r\n");
			osw.flush();
		}
	}
	
	public void searchByAuthorOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Enter author:\r\n");
			osw.flush();
			
			
			ArrayList<Message> matches = new ArrayList<Message>();
			String searchAuthor = scanner.nextLine();
			
			
			for(int x = 0; x < messageList.size(); x++)
			{
				if(messageList.get(x).getAuthor().equalsIgnoreCase(searchAuthor))
					matches.add(messageList.get(x));
			}
			
			if(matches.size() == 0)
			{
				osw.write("No messages to display.\r\n");
				osw.flush();
			}
			else
			{
				osw.write("\r\n Messages with author" + searchAuthor + "\r\n");
				osw.flush();
				for(int x = 0; x < matches.size(); x++)
				{
					//this will probably show more details. TODO
					osw.write((x+1) + " " + matches.get(x).title + "\r\n");
				}
				osw.write("\r\n Please choose a message, 1 - "+ matches.size() + "\r\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < matches.size(); x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\r\n"+matches.get(x).displayString()+"\r\n");
						osw.flush();
						validChoice = true;
					}
				if(!validChoice)
				{
					invalidOption();
				}
			}
			
		}
		else
		{
			osw.write("Please Sign in to search messages.\r\n");
			osw.flush();
		}
	}
	
	public void searchByTopicOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Enter topic:\r\n");
			osw.flush();
			
			
			ArrayList<Message> matches = new ArrayList<Message>();
			String searchTopic = scanner.nextLine();
			
			
			for(int x = 0; x < messageList.size(); x++)
			{
				if(messageList.get(x).getTopic().equalsIgnoreCase(searchTopic))
					matches.add(messageList.get(x));
			}
			
			if(matches.size() == 0)
			{
				osw.write("No messages to display.\r\n");
				osw.flush();
			}
			else
			{
				osw.write("\r\n Messages with topic" + searchTopic + "\r\n");
				osw.flush();
				for(int x = 0; x < matches.size(); x++)
				{
					//this will probably show more details. TODO
					osw.write((x+1) + " " + matches.get(x).title + "\r\n");
				}
				osw.write("\r\n Please choose a message, 1 - "+ matches.size() + "\r\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < matches.size(); x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\r\n"+matches.get(x).displayString()+"\r\n");
						osw.flush();
						validChoice = true;
					}
				if(!validChoice)
				{
					invalidOption();
				}
			}
			
		}
		else
		{
			osw.write("Please Sign in to search messages.\r\n");
			osw.flush();
		}
	}
	
	public void viewAllUsersOption() throws IOException
	{
		osw.write("USERS:\r\n");
		osw.flush();
		for(int x = 0; x < userList.size(); x++)
		{
			osw.write(userList.get(x).name + "\r\n");
			//osw.write(userList.get(x).password + "\r\n\r\n");

		}
	}
	
	public void viewActiveUsersOption() throws IOException
	{
		osw.write("LOGGED IN USERS:\r\n");
		osw.flush();
		for(int x = 0; x < loggedInUserList.size(); x++)
		{
			osw.write(loggedInUserList.get(x).name + "\r\n");
			//osw.write(loggedInUserList.get(x).password + "\r\n\r\n");

		}
	}
	
	public void exitOption() throws IOException
	{
		logUserOut();
		osw.write("Goodbye.\r\n");
		osw.flush();
	}
	//...end menu options
	
	
	public void invalidOption() throws IOException
	{
		osw.write("\r\n Invalid entry.\r\n");
		osw.flush();
	}
	
	public int getLoggedInUserListSize()
	{
		return loggedInUserList.size();
	}
	
	public void setLoggedInUserList(ArrayList<User> list)
	{
		this.loggedInUserList = list;
	}
	
	public User getCurrentUser()
	{
		return this.currentUser;
	}
	
	public boolean hasLogout()
	{
		return hasLogout;
	}
	
	public void clearLogout()
	{
		hasLogout = false;
		userToLogOut = null;
	}
	
	public User getUserToLogOut()
	{
		return userToLogOut;
	}
	
	public void logUserOut() throws IOException
	{
		userToLogOut = currentUser;
		hasLogout = true;
		isLoggedIn = false;
		loggedInUserList.remove(loggedInUserList.indexOf(currentUser));
		
		currentUser = null;
	}
	
	public void displayOptionMenu() throws IOException
	{
		osw.write("\r\n\r\nPlease enter an option:\r\n");
	    osw.write("1. Sign in\r\n");
	    osw.write("2. Sign up\r\n");
	    osw.write("3. Sign out\r\n");
	    osw.write("4. Post Message\r\n");
	    osw.write("5. View recent messages\r\n");
	    osw.write("6. View all messages\r\n");
	    osw.write("7. Search messages by author\r\n");
	    osw.write("8. Search messages by topic\r\n");

	    
	    //test options
	    osw.write("9. VIEW ALL USERS\r\n");
	    osw.write("0. VIEW ACTIVE USERS\r\n");
	    
	    osw.write("...Or type exit to quit.\r\n");
	    

	    
	    osw.flush();
	}
	
	public void processOption(String o) throws IOException
	{
		o = eliminateSpaces(o);
		System.out.println("Server received: " + o);
		if(o.equals("1") || o.equalsIgnoreCase("signin") )
    	{signInOption();}
    	else if(o.equals("2") || o.equalsIgnoreCase("signup") )
    	{signUpOption();}
    	else if(o.equals("3") || o.equalsIgnoreCase("signout") )
    	{signOutOption();}
    	else if(o.equals("4") || o.equalsIgnoreCase("postmessage") || o.equalsIgnoreCase("post"))
    	{postMessageOption();}
    	else if(o.equals("5") || o.equalsIgnoreCase("viewrecentmessages") || o.equalsIgnoreCase("viewrecent"))
    	{viewRecentMessagesOption();}
    	else if(o.equals("6") || o.equalsIgnoreCase("viewallmessages") || o.equalsIgnoreCase("viewall"))
    	{viewAllMessagesOption();}
    	else if(o.equals("7") || o.equalsIgnoreCase("searchbyauthor") || o.equalsIgnoreCase("searchauthor"))
    	{searchByAuthorOption();}
    	else if(o.equals("8") || o.equalsIgnoreCase("searchbytopic") || o.equalsIgnoreCase("searchtopic"))
    	{searchByTopicOption();}
    	else if (o.equals("getarray")) {
    		oos.writeObject(messageList);
    	}
		
		
		
		//test options
    	else if(o.equals("9"))
    	{viewAllUsersOption();}
    	else if(o.equals("0"))
    	{viewActiveUsersOption();}
		
		//exit option
    	else if(o.equalsIgnoreCase("exit"))
    	{exitOption();}
    	else
    	{invalidOption();}
	}
	
	public String eliminateSpaces(String s)
	{
		return s.replaceAll(" ", "");
	}
	
	
	

}
