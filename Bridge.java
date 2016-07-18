package RemoteAccess;

import java.io.*;
//flag ACC_SUPER is set

public class Bridge extends Thread
{
	// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
		// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
		// Decompiler options: packimports(3) braces deadcode fieldsfirst 
		// Source File Name:   Relay.java
		// Class Version:      45.3



		
		    // Constants:          66
		    // Interfaces:         0
		    // Fields:             5
		    // Methods:            2
		    // Class Attributes:   1


		    static final int BUFSIZE = 1000;
		    InputStream input;
		    OutputStream output;
		    byte buffer[];

		    // Decompiling method: <init>  Signature: (Ljava/io/InputStream;Ljava/io/OutputStream;Ljava/awt/TextArea;)V
		    // Max stack: 2, #locals: 4, #params: 4
		    // Code length: 29 bytes, Code offset: 740
		    // Line Number Table found: 6 entries
		    // Parameter  0 added: Name this Type Lorg/apache/soap/util/net/Relay; At 0 29 Range 0 28 Init 0 fixed
		    // Parameter  1 added: Name inputstream Type Ljava/io/InputStream; At 0 29 Range 0 28 Init 0
		    // Parameter  2 added: Name outputstream Type Ljava/io/OutputStream; At 0 29 Range 0 28 Init 0
		    // Parameter  3 added: Name textarea Type Ljava/awt/TextArea; At 0 29 Range 0 28 Init 0
		    // RetValue   4 added: Name <returnValue> Type V At 0 29 Range 0 28 Init 0 fixed
		    public Bridge(InputStream inputstream, OutputStream outputstream)
		    {
		       // super();
		        buffer = new byte[1000];
		        input = inputstream;
		        output = outputstream;
		        return;
		    }

		    // Decompiling method: run  Signature: ()V
		    // Max stack: 6, #locals: 4, #params: 1
		    // Code length: 107 bytes, Code offset: 827
		    // Exception table: 3 entries
		    //           start  0 end 66 handler 69 type IOException
		    //           start  0 end 73 handler 79 type any
		    //           start  86 end 100 handler 103 type IOException
		    // Line Number Table found: 17 entries
		    // Parameter  0 added: Name this Type Lorg/apache/soap/util/net/Relay; At 0 107 Range 0 106 Init 0 fixed
		    // RetValue   4 added: Name <returnValue> Type V At 0 107 Range 0 106 Init 0 fixed
		    // LocalVar   1 added: Name i Type I At 12 51 Range 12 62 Init 62
		    // LocalVar   3 added: Name local Type @ At 85 21 Range 85 105 Init 85
		    // LocalVar   2 added: Name exception Type Ljava.lang.Exception; At 79 5 Range 79 83 Init 79
		    public void run()
		    {
		        int i;
		        try
		        {
		            while((i = input.read(buffer)) > 0) 
		            {
		                output.write(buffer, 0, i);
		                output.flush();
		            }
		        }
		        catch(IOException _ex) { }
		        finally
		        {
		            try
		            {
		                input.close();
		                output.close();
		            }
		            catch(Exception _ex) { }
		        }
		        return;
		    }


}
