package com.example.sketchmatching;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


import android.os.Environment;
import android.util.Log;


public class Client {
	static final int SocketServerPORT = 8080;
	public void send(String fname,String ip) {
		Log.e("Debug","here");
		ClientRxThread clientRxThread = 
	    new ClientRxThread(ip, SocketServerPORT,fname);
	    clientRxThread.start();
	}
	
	private class ClientRxThread extends Thread {
		  String dstAddress;
		  int dstPort;
		  String file_name;
		  ClientRxThread(String address, int port,String fname) {
		   dstAddress = address;
		   dstPort = port;
		   file_name = fname;
		   
		   Log.e("Debug",dstAddress);
		  }

		  @Override
		  public void run() 
		  {
			  Socket socket = null;
			   
			  try {
				    socket = new Socket(dstAddress, dstPort);
			 
		   File file = new File(
		     Environment.getExternalStorageDirectory() + "/Download/" +  file_name);
		   
		   
		   byte[] bytes = new byte[(int) file.length()];
		   BufferedInputStream bis;
		   try {
		    bis = new BufferedInputStream(new FileInputStream(file));
		    bis.read(bytes, 0, bytes.length);
		    OutputStream os = socket.getOutputStream();
		    os.write(bytes, 0, bytes.length);
		    os.flush();
		    
		   } catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
			  }
			  catch (IOException e) {

				    e.printStackTrace();
				    
				    final String eMsg = "Something wrong: " + e.getMessage();
				   }
		   finally {
		    try {
		     socket.close();
		    } catch (IOException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		   }
			  
		  }
		 }
		 
	}