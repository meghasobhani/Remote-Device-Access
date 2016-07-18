package RemoteAccess;

import java.io.*;
import java.net.*;
//import com.memorynotfound.geoip.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class RemoteDeviceConcurrent {

	public static void main(String[] args) throws Exception
	{
		try {
			if((args[0]!=(null)) && (args[1]!=(null)))
			{
				{
					
				while(true){
				int portNo,portNo1;
				portNo=Integer.parseInt(args[1]);
				portNo1=Integer.parseInt(args[2]);
				String ip;
				ip=args[0];
				Socket remote = new Socket(ip,portNo);
				Socket retain_remote = new Socket(ip,portNo1);
				PrintStream obj1= new PrintStream(retain_remote.getOutputStream());
				obj1.println("retain connection");



				//ps.println("I'm the remote client");
				InputStreamReader isr=new InputStreamReader(remote.getInputStream());
				BufferedReader br= new BufferedReader(isr);
				String request=br.readLine();
				System.out.println(request);

				if(request.contains("Display"))
				{
					String[] listcmd=request.split(";");
					String foldername=listcmd[1];
					PrintStream obj= new PrintStream(remote.getOutputStream());

					File filesfolder = new File(foldername);
					File[] fileslist = filesfolder.listFiles();
					String filenames="Files";
					String password=listcmd[2];
					String flag = "false";
					
					//declaring the internal password
					String init_password="Device";
					if(init_password.equals(password)){
						try
						
						{
					
					for (int i = 0; i < fileslist.length; i++) {
						if (fileslist[i].isFile())
						{
							filenames=filenames+":"+fileslist[i].getName();
							System.out.println("File " + fileslist[i].getName());
						} 
					}
					flag="true";
					obj.println(filenames);
					}
						catch (SecurityException e) {
				            System.err.println(e);
							}
					}
				}

					
					//System.out.println(filenames);

				
					
				else if(request.contains("Copy"))
				{


					String[] tarnsferreq=request.split(";");
					String fileName=tarnsferreq[0];
					String foldername1=tarnsferreq[1];
					System.out.println(foldername1+fileName);
					File requestedFile=new File(foldername1);
					byte[] bytearray=new byte[(int) requestedFile.length()];

					FileInputStream file =  new FileInputStream(requestedFile); 
					BufferedInputStream buffIP=new BufferedInputStream(file);
					BufferedOutputStream buffOP=new BufferedOutputStream(remote.getOutputStream());

					buffIP.read(bytearray, 0,bytearray.length);
					buffOP.write(bytearray, 0, bytearray.length);
					//buffOP.flush(); no need of flush as close calls the flush method
					buffOP.close();
					buffIP.close();
				}
				else if(request.contains("Location")){

					/*URL myIP=new URL("http://checkip.amazonaws.com");
					BufferedReader buff=new BufferedReader(new InputStreamReader(myIP.openStream()));
					String newIP=buff.readLine();	*/	
					String location= "LookUpProgram.main(newIP)";

					System.out.println("location new:"+location);
					PrintStream obj= new PrintStream(remote.getOutputStream());
					obj.println(location);
				}
				
				else if(request.contains("Delete"))
				{
					PrintStream ps= new PrintStream(remote.getOutputStream());
					
					String[] listcmd=request.split(";");
					String filename=listcmd[1];
					String pathname=listcmd[2];
					String password=listcmd[3];
					String flag = "false";
					
					//declaring the internal password
					String init_password="Device";
					
					//password authentication
					if(init_password.equals(password)){
						
						//extracting path and file names
						Path path = FileSystems.getDefault().getPath(pathname, filename);
						try {
				            
				            		Files.deleteIfExists(path);
				    	            flag="true";		//flag is used as an ack for file delete success
				    	            ps.println(flag);
				        	}

						catch (IOException | SecurityException e) {
				            System.err.println(e);
							}

						
					}
					
					else
					{
						System.out.println("\n\tIncorrect Password- failed to authenticate file deletion\n\tprogram aborted \n");
						ps.println(flag);
					}

					}
				
				else
				{
					System.out.println("Error reading file from server");
				}
				remote.close();

			}
		}}}
		catch (Exception e) 
		{
			System.out.println(e);
			System.out.println("Got an IOException: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
