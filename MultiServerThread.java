package RemoteAccess;

import java.io.IOException;
import java.net.*;

public class MultiServerThread extends Thread {
	
	public int count =0;
	//public String request;
	public Socket socketClient = null;
	public Socket socketDevice = null;
	public MultiServerThread(Socket socketClient, Socket socketDevice) 
	{
		super("MultiServerThread");
		this.socketClient = socketClient;
		this.socketDevice = socketDevice;
	}

	public void run() 
	{

		try 
		{
			InetAddress remote_addr = socketDevice.getInetAddress();
			//System.out.println("The Remote client's IP is" +remote_addr);

			if(socketClient.getInputStream()!=null)

			{
				System.out.println("socket has input");
				(new Bridge(socketClient.getInputStream(), socketDevice.getOutputStream())).start();

			}

			if(socketDevice.getInputStream()!=null)
			{
				System.out.println("socket1 has input");
				(new Bridge(socketDevice.getInputStream(), socketClient.getOutputStream())).start();

			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



}
