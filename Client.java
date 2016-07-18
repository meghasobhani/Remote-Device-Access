package RemoteAccess;

import java.io.*;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception
	{
		try {
			if((args[0]!=(null)) && (args[1]!=(null)) && (args[2]!=(null)))//checks if the server ip port and command is provided
			{
				//port number and IP of server to which the connection has to be made--given as arguments
				int portNo;  
				String ip;
				portNo=Integer.parseInt(args[1]);				
				ip=args[0];
				Socket client = new Socket(ip,portNo);//creates socket
				PrintStream obj= new PrintStream(client.getOutputStream());
				//Display command-displays all the files present in given path
				if(args[2].contains("Display"))
				{
					if(args[3]!=null)//checks if the path is provided
					{
						obj.println(args[2]+";"+ args[3]+";"+args[4]);//sends Display command to server
						//gets files names from server
						InputStreamReader isr=new InputStreamReader(client.getInputStream());
						BufferedReader br= new BufferedReader(isr);
						String request=br.readLine();//stores the result from server in string variable
						String[] filenames=request.split(":");
						for (String string : filenames) {
							System.out.println(string);//prints each file name on new line
						}
					}
					else{
						System.out.println("Plese specify the folder path");
					}
				}
				//location command-displays the location of the remote device
				else if (args[2].contains("Location"))
				{
					obj.println(args[2]);//sends location command to server
					//gets remote device location from server
					InputStreamReader isr=new InputStreamReader(client.getInputStream());
					BufferedReader br= new BufferedReader(isr);
					String request=br.readLine();//stores the result from server in string variable
					System.out.println(request);//prints location
				}
				//transfer command-gets the file from the remote device
				else if(args[2].contains("Copy"))
				{
					if((args[3]!=null&&args[4]!=null))//checks if the file name and path is provided
					{
						String requestedfile=args[2]+";"+ args[3];
						obj.println(requestedfile);//sends transfer command to server
						//get info from server
						InputStream is=client.getInputStream();
						if(is!=null)
						{
							String filenamewithextension=args[3];//file name with extension
							String fileNamewithoutext;//file name without extension
							String extension;//file extension
							int extenPos=filenamewithextension.lastIndexOf(".");//file extension position
							if(extenPos>0)
							{
								fileNamewithoutext=filenamewithextension.substring(0,extenPos);
								extension=filenamewithextension.substring(extenPos+1, filenamewithextension.length());
								//destination path is "files/" + the filename suffixed with _transferred
								FileOutputStream fos=new FileOutputStream("files/"+fileNamewithoutext+"_Copy."+extension);
								//write byte by byte data of the file
								BufferedOutputStream buffOP=new BufferedOutputStream(fos);
								byte[] bytearray=new byte[1];
								int procByte= is.read(bytearray, 0, bytearray.length);
								ByteArrayOutputStream byteOP=new ByteArrayOutputStream();
								do {
									byteOP.write(bytearray);
									procByte=is.read(bytearray);
								} while (procByte != -1);
								buffOP.write(byteOP.toByteArray());
								buffOP.close();
								System.out.println("File recieved successfully");
							}
						}
						
						
						else
						{
							System.out.println("Error reading file from server");
						}
					}
				}
				else if (args[2].contains("Delete"))
				{
					if((args[3]!=null&&args[4]!=null))//checks if the file name and path is provided
					{	
						// args[0]=ip ; args[1]=port ; args[2]=delete ; args[3]= filename ; args[4]= path ; args[5]=password 
						String reqfile=args[2]+";"+ args[3]+";"+args[4]+";"+args[5];
						obj.println(reqfile);//sends delete command to server
						
						//notification that the delete operation is successful
						InputStreamReader isr=new InputStreamReader(client.getInputStream());
						BufferedReader br= new BufferedReader(isr);
						String flag=br.readLine();//stores the result from server in string variable
						if(flag.equals("true")){
								System.out.println("File deleted successfully");
						}
						else 
							System.out.println("File deletion is unsuccessfull");
					}
						
				}
				else{
					System.out.println("Invalid request");
				}
				client.close();
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
			System.out.println("Got an IOException: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
