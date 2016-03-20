package com.example.client00;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Client {

	private String serverMessage;
	public static  String SERVERIP ; // IP adresi
														
	public static final int SERVERPORT = 5000;
	private OnMessageReceived mMessageListener = null;
	private boolean mRun = false;

	public static String name = null;
	//public static PrintWriter out;
	//public static BufferedReader in;
	public ChatActivity c;
	public static String nameKey = null;
	
	public Socket _server ;
	public DataInputStream _in;
    public static DataOutputStream _out;
    public InputStream _in_;
    public OutputStream _out_;

	public Client(OnMessageReceived listener) {
		mMessageListener = listener;
		
	}

	public static void sendMessage(String message) {
		if (_out != null) {
			try {
				_out.writeUTF(message);
				_out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void stopClient() {
		mRun = false;
	}

	public void run() {

		mRun = true;

		try {
			
		
			//------- Ip adresi-----///
			//InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			
			//----- server ile baðlantý için gerekli soket-----///
			//Socket socket = new Socket(serverAddr, SERVERPORT);
			_server = new Socket(SERVERIP,SERVERPORT);
			
			try {

				//----- server'a mesaj göndermek için-----///
				//out = new PrintWriter(new BufferedWriter(
						//new OutputStreamWriter(socket.getOutputStream())), true);

				//----- serverdan cevap alýmý için-----///
				//in = new BufferedReader(new InputStreamReader(
					//	socket.getInputStream()));
				
				
				_out_ = _server.getOutputStream();
			    _out = new DataOutputStream(_out_);
			    _in_ = _server.getInputStream();
			    _in = new DataInputStream(_in_);

				synchronized (this) {
					if(name != null && !name.trim().startsWith("#"))    
						_out.writeUTF(name);
					ChatActivity.nameKey = name;
					 _out.flush();
				   }
				
				
				// ------clientýn serverdan gelecek mesajlarý almasý----//
				while (mRun) {
					synchronized (this) {
					serverMessage = _in.readUTF();
					
					if (serverMessage != null && mMessageListener != null ){
						//if(serverMessage.indexOf("#") != -1){
						// gelen mesaj MyActivity' de dinleyen metoda gönderilir
						mMessageListener.messageReceived(serverMessage);
					
						}
						
						
					 // if(serverMessage.indexOf("#") == -1){
						  
						//   c.Warning(serverMessage);
					//  }		  
					
					}
					
					serverMessage = null;
					
					}
				
		
			} catch (Exception e) {
			} finally {
				
				_server.close();
			}

		} catch (Exception e) {
			
		}
	} 

	public interface OnMessageReceived {
		public void messageReceived(String message);
	}
}
