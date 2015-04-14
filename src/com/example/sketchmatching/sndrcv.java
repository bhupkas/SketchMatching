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


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class sndrcv {
	static final int SocketServerPORT = 8080;
	
	private String res;
	
	
	public void send(String msg,String ip) {
		Log.e("Debug","In sndrcv");
		ClientRxThread clientRxThread = 
	    new ClientRxThread(ip, SocketServerPORT,msg);
	    clientRxThread.start();
	}
	
	public String getanswer()
	{
		return res;
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
				res = messsage;
				Log.e("Debug",res);
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