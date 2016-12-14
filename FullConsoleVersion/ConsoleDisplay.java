package FullConsoleVersion;

import java.io.InputStream;
import java.util.Scanner;


public class ConsoleDisplay extends Thread
{
	private InputStream inS;
	private Scanner scan;
	
	public ConsoleDisplay(InputStream in)
	{
		this.inS = in;
		scan = new Scanner(this.inS);
	}
	
	public void run()
	{
		while(true)
		{
			System.out.println(scan.nextLine());
		}
	}
}
