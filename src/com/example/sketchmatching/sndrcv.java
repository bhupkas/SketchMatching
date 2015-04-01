package com.example.sketchmatching;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


import android.os.Environment;
import android.util.Log;


public class sndrcv {
	static final int SocketServerPORT = 8080;
	public void send(String msg,String ip) {
		Log.e("Debug","In sndrcv");
		ClientRxThread clientRxThread = 
	    new ClientRxThread(ip, SocketServerPORT,msg);
	    clientRxThread.start();
	}
	
	private class ClientRxThread extends Thread {
		  String dstAddress;
		  int dstPort;
		  private PrintWriter printwriter;
		  private String messsage;
		  private InputStreamReader inputStreamReader;
		  private BufferedReader bufferedReader;

		  ClientRxThread(String address, int port,String msg) {
		   dstAddress = address;
		   dstPort = port;
		   messsage = msg;
		   Log.e("Debug",dstAddress);
		  }

		  @Override
		  public void run() 
		  {
			  Socket socket = null;
			   
			  try {
				    socket = new Socket(dstAddress, dstPort);

		   try {
				printwriter = new PrintWriter(socket.getOutputStream(), true);
				printwriter.write(messsage); // write the message to output stream
				 
				printwriter.flush();
				inputStreamReader = new InputStreamReader(socket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader); // get the client message
				messsage = bufferedReader.readLine();
				Log.e("Received",messsage);
				printwriter.close();
				inputStreamReader.close();
				socket.close();
				
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