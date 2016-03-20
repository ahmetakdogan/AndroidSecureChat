package com.ahmet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;


public class ClientThread extends Thread{
	
  private String clientName = null;
  //private DataInputStream in = null;
  //private PrintStream out = null;
  private Socket clientSocket = null;
  private final ClientThread[] clients;
  private int maxClientsCount;
  public static int id = 0;
  
  public DataInputStream _in;
  public DataOutputStream _out;
  public InputStream _in_;
  public OutputStream _out_;

   public ClientThread(Socket clientSocket, ClientThread[] clients) {
	 
      this.clientSocket = clientSocket;
      this.clients = clients;
      maxClientsCount = clients.length;
  }

 public void run() {
	
     int maxClientsCount = this.maxClientsCount;
     ClientThread[] clients = this.clients;

      try {
   
    	  // --- input ve output stream ----// 
       // in = new DataInputStream(clientSocket.getInputStream());
       // out = new PrintStream(clientSocket.getOutputStream());
        
        _in_ = clientSocket.getInputStream();
        _in = new DataInputStream(_in_);

        _out_ = clientSocket.getOutputStream();
        _out = new DataOutputStream(_out_);
        
        String name; // client tarafýndan gelen  
        
        while (true) {
            name = _in.readUTF().trim();   // kullanýcý ismi
           if (name.indexOf("#") == -1) {
               break;
            } else {
              _out.writeUTF("Kullanýcý ismi '#' ile baþlayamaz!!!.");
          }
        }
    
   System.out.println( + id + name + " baðlandý..");
  
   //-------kullanýcý ismi kiþiye atandý ve diðer clientlara gönderilir-----/////
  
   synchronized (this) {
     for (int i = 0; i < maxClientsCount; i++) {
       if (clients[i] != null && clients[i] == this) {
         clientName = "#" + name;
         break;
       }
     }
     for (int i = 0; i < maxClientsCount; i++) {
       if (clients[i] != null && clients[i] != this) {
    	   clients[i]._out.writeUTF("#" + name);
       }
     }
   }
   
   ///------ilk baðlanan birine ondan önce baðlananlarýn listesi gönderilir-----///
   synchronized (this) {
 	   for (int i = 0; i < maxClientsCount; i++) {
 		  for(int j=0; j<maxClientsCount; j++){
 	       if (clients[i] != null && clients[i] == this && clients[j] != null && clients[i].clientName != clients[j].clientName) {
 	    	  clients[i]._out.writeUTF(clients[j].clientName);
 	       }
 	     }
       }
 	 }
   
  //------mesaþlaþmanýn baþladýðý yer----////
  
 try {
	 
	  while (true) {
		     String line = _in.readUTF();
		     if (line.startsWith("/quit")) {
		       break;
		     }
		       synchronized (this) {
		         for (int i = 0; i < maxClientsCount; i++) {
		           if (clients[i] != null && clients[i].clientName != null && line.startsWith(clients[i].clientName.substring(1))) {
		        	   clients[i]._out.writeUTF("<" + name + "> " + line.substring(clients[i].clientName.length()-1,line.length()));
		        	   System.out.println("<" + name + ">" + line.substring(clients[i].clientName.length()-1,line.length()));
		           }
		         }
		       }
		   }
	
} catch (Exception e) {

	  //// ----ayrýlan diziden atýlýr------/////
	   synchronized (this) {
	     for (int i = 0; i < maxClientsCount; i++) {
	       if (clients[i] == this) {
	    	  clients[i] = null;
	       }
	     }
	 }
	   
	   synchronized (this) {
		     for (int i = 0; i < maxClientsCount; i++) {
		       if (clients[i] != null && clients[i] != this
		           && clients[i].clientName != null) {
		    	   clients[i]._out.writeUTF("~" + name);
		    	   System.out.println(name + " ayrýldý!!!");
		       }
		     }
		   }
}
   
     _in.close();
     _out.close();
     clientSocket.close();
    } catch (IOException e) { 
   	 System.out.println("hhhhhhhhhh");
   }
  }
}
