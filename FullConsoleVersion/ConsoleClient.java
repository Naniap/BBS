package FullConsoleVersion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class ConsoleClient 
{
	public static void main(String[] args) 
    {
        InputStream serverInput = null;
        OutputStream serverOutput = null;
        Scanner scan = null;
        OutputStreamWriter osw = null;
        
        ConsoleDisplay CD = null;
        
        
        String serverAddress = JOptionPane.showInputDialog("Please"
                + " enter a Server to connect (DNS name or IP)");
		
		String serverPort = JOptionPane.showInputDialog("Please"
                + " enter the server's port number");
		
        
        //String serverAddress = "localhost";
        //String serverPort = "666";
		
        try
        {
            Socket socket = new Socket(serverAddress, Integer.parseInt(serverPort));
            serverOutput = socket.getOutputStream();
            serverInput = socket.getInputStream();
            osw = new OutputStreamWriter(serverOutput);
            
            CD = new ConsoleDisplay(serverInput);
            CD.start();
            
            Scanner keyboard = new Scanner(System.in);
            
            while (true)
            {                
                String message = keyboard.nextLine();
                osw.write(message+"\n");
                osw.flush();
            }
            
        }
        catch (IOException e)
        {
            System.out.println("error connecting to Server");
        }
        
    }
}
