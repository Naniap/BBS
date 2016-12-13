import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


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
            osw.write("Welcome to BBServer Console.\n");
            osw.write("This is the development, pre-GUI configuration for testing\n");
            
            osw.flush();
                               
            //loop to continue asking options
            while(!option.equalsIgnoreCase("exit"))
            {
            	displayOptionMenu();
            	
                option = scanner.nextLine();
            	
            	processOption(option);
            }

        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client");
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
		
		
		for(int x = 0; x < loggedInUserList.size()-1; x++)
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
		osw.write("Log in:\n");
		osw.flush();
		
		if(!isLoggedIn)
		{
			osw.write("User name: \n");
    		osw.flush();
    		uName = scanner.nextLine();
    		
    		osw.write("Password: \n");
    		osw.flush();
    		uPass = scanner.nextLine();
    		UserDAOImpl uDAO = new UserDAOImpl();
    		User tempUser = uDAO.login(uName,SQLConnect.sha512_Encrpyt(uPass, uName.substring(1)));
    		//tempUser = new User (uName, uPass);

    		if(containsLoggedInUser(tempUser))
    		{
    			osw.write("Sorry, "+tempUser.name+" is already logged in.\n");
        		osw.flush();
    		}
    		else if(tempUser != null)
    		{
    			currentUser = tempUser;
    			isLoggedIn = true;
    			loggedInUserList.add(currentUser);
    			osw.write("Logged in as "+currentUser.name+".\n");
        		osw.flush();
    		}
    		else
    		{
    			osw.write("Sorry, user name or password was incorrect. \n");
        		osw.flush();
    		}
		}
		else
		{
			osw.write("You are already logged in as "+currentUser.name+ ".\n");
    		osw.flush();
		}
	}
	
	public void signUpOption() throws IOException
	{
		if (isLoggedIn) {
			osw.write("\nYou're already logged in. \n");
			osw.flush();
			return;
		}
		osw.write("Sign up:\n");
		osw.write("User name: \n");
		osw.flush();
		uName = scanner.nextLine();
		osw.write("Password: \n");
		osw.flush();
		uPass = scanner.nextLine();
		
		tempUser = new User (uName, SQLConnect.sha512_Encrpyt(uPass, uName.substring(1)));
		//Create a new username here on database.
		UserDAOImpl uDAO = new UserDAOImpl();
		uDAO.insert(tempUser);
		if(containsUser(tempUser))
		{
    		osw.write("Sorry, this user already exists. \n");
    		osw.flush();
		}
		else
		{
			osw.write("User "+tempUser.name+" added.\n");
    		osw.flush();
    		
    		setNewUser(tempUser);
		}
	}
	
	public void signOutOption() throws IOException
	{
		osw.write("Log out:\n");
		osw.flush();
		
		if(isLoggedIn)
		{
			logUserOut();
			
			osw.write("Signed out.\n");
    		osw.flush();
		}
		else
		{
			osw.write("\nSorry, you are not signed in.\n");
    		osw.flush();
		}
	}
	
	public void postMessageOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Post message:\n");
			osw.flush();
			
			osw.write("Message title: \n");
			osw.flush();
			mTitle = scanner.nextLine();
			
			osw.write("Message topic: \n");
			osw.flush();
			mTopic = scanner.nextLine();
			
			osw.write("Message content: \n");
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
			osw.write("Message \""+ mTitle + "\" posted.\n");
			osw.flush();
		}
		else
		{
			osw.write("Please Sign in to post messages.\n");
			osw.flush();
		}
		
		
		
	}
	
	public void viewMessagesOption() throws IOException//DEPRECATED
	{
		
		if(isLoggedIn)
		{
			osw.write("Display messages:\n");
			osw.flush();
			
			if(messageList.size() < 10)
				displaySize = messageList.size();
			else
				displaySize = 10;
			
			if(displaySize == 0)
			{
				osw.write("No messages to display.\n");
				osw.flush();
			}
			else
			{
				for(int x = 0; x < displaySize; x++)
				{
					//this will probably show more details.
					osw.write((x+1) + " " + messageList.get(x).title + "\n");
				}
				osw.write("Please choose a message, 1 - "+ displaySize + "\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < displaySize; x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\n"+messageList.get(x).displayString()+"\n");
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
			osw.write("Please Sign in to display messages.\n");
			osw.flush();
		}
	}
	
	public void viewRecentMessagesOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Display recent messages:\n");
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
				osw.write("No messages to display.\n");
				osw.flush();
			}
			else
			{
				osw.write("Most recent messages by date\n");
				osw.flush();
				for(int x = 0; x < recentMessages.size(); x++)
				{
					//this will probably show more details. TODO
					osw.write((x+1) + " " + recentMessages.get(x).getPostedTime() + " : "+ recentMessages.get(x).title +"\n");
				}
				osw.write("Please choose a message, 1 - "+ recentMessages.size() + "\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < recentMessages.size(); x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\n"+recentMessages.get(x).displayString()+"\n");
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
			osw.write("Please Sign in to display messages.\n");
			osw.flush();
		}
	}

	public void viewAllMessagesOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Display all messages:\n");
			osw.flush();
			
			displaySize = messageList.size();
			
			if(displaySize == 0)
			{
				osw.write("No messages to display.\n");
				osw.flush();
			}
			else
			{
				for(int x = 0; x < displaySize; x++)
				{
					//this will probably show more details.
					osw.write((x+1) + " " + messageList.get(x).title + "\n");
				}
				osw.write("\n Please choose a message, 1 - "+ displaySize + "\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < displaySize; x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\n"+messageList.get(x).displayString()+"\n");
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
			osw.write("Please Sign in to display messages.\n");
			osw.flush();
		}
	}
	
	public void searchByAuthorOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Enter author:\n");
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
				osw.write("No messages to display.\n");
				osw.flush();
			}
			else
			{
				osw.write("\n Messages with author" + searchAuthor + "\n");
				osw.flush();
				for(int x = 0; x < matches.size(); x++)
				{
					//this will probably show more details. TODO
					osw.write((x+1) + " " + matches.get(x).title + "\n");
				}
				osw.write("\n Please choose a message, 1 - "+ matches.size() + "\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < matches.size(); x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\n"+matches.get(x).displayString()+"\n");
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
			osw.write("Please Sign in to search messages.\n");
			osw.flush();
		}
	}
	
	public void searchByTopicOption() throws IOException
	{
		if(isLoggedIn)
		{
			osw.write("Enter topic:\n");
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
				osw.write("No messages to display.\n");
				osw.flush();
			}
			else
			{
				osw.write("\n Messages with topic" + searchTopic + "\n");
				osw.flush();
				for(int x = 0; x < matches.size(); x++)
				{
					//this will probably show more details. TODO
					osw.write((x+1) + " " + matches.get(x).title + "\n");
				}
				osw.write("\n Please choose a message, 1 - "+ matches.size() + "\n");
				osw.flush();
				
				option = scanner.nextLine();
				
				validChoice = false;
				for(int x = 0; x < matches.size(); x++)
					if(Integer.parseInt(option) == x+1)
					{
						osw.write("\n"+matches.get(x).displayString()+"\n");
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
			osw.write("Please Sign in to search messages.\n");
			osw.flush();
		}
	}
	
	public void viewAllUsersOption() throws IOException
	{
		osw.write("USERS:\n");
		osw.flush();
		for(int x = 0; x < userList.size(); x++)
		{
			osw.write(userList.get(x).name + "\n");
			//osw.write(userList.get(x).password + "\n\n");

		}
	}
	
	public void viewActiveUsersOption() throws IOException
	{
		osw.write("LOGGED IN USERS:\n");
		osw.flush();
		for(int x = 0; x < loggedInUserList.size(); x++)
		{
			osw.write(loggedInUserList.get(x).name + "\n");
			//osw.write(loggedInUserList.get(x).password + "\n\n");

		}
	}
	
	public void exitOption() throws IOException
	{
		logUserOut();
		osw.write("Goodbye.\n");
		osw.flush();
	}
	//...end menu options
	
	
	public void invalidOption() throws IOException
	{
		osw.write("\n Invalid entry.\n");
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
		osw.write("\n\nPlease enter an option:\n");
	    osw.write("1. Sign in\n");
	    osw.write("2. Sign up\n");
	    osw.write("3. Sign out\n");
	    osw.write("4. Post Message\n");
	    osw.write("5. View recent messages\n");
	    osw.write("6. View all messages\n");
	    osw.write("7. Search messages by author\n");
	    osw.write("8. Search messages by topic\n");

	    
	    //test options
	    //osw.write("9. VIEW ALL USERS\n");
	    //osw.write("0. VIEW ACTIVE USERS\n");
	    
	    osw.write("...Or type exit to quit.\n");
	    

	    
	    osw.flush();
	}
	
	public void processOption(String o) throws IOException
	{
		o = eliminateSpaces(o);
		
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
