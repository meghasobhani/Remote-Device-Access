package RemoteAccess;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConcurrent {
	
	public static void main(String[] args) throws IOException 
	{
		boolean listen = true;
		if(args.length != 3)
		{
			System.err.println("Usage: Enter proper command line arguments");
			System.exit(1);
		}
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		int c = Integer.parseInt(args[2]);
		/* Server socket that connects to remote device */
		ServerSocket retain_connection = new ServerSocket(c);
		ServerSocket Client = new ServerSocket(a);
		ServerSocket Device = new ServerSocket(b);

		try {


			while(true)
			{
				Socket retain = retain_connection.accept();
				retain.getInputStream();
				while (listen)     //Server waiting for requests from Client and Device to be accessed
				{
					new MultiServerThread(Client.accept(),Device.accept()).start();
				}
			}

		} 
		catch (IOException e) 
		{
			System.exit(-1);
		}
		retain_connection.close();
		Device.close();
		Client.close();

	}


}
