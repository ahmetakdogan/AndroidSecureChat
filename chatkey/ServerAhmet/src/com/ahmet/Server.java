package com.ahmet;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


  public class Server {
// server socket.
    private static ServerSocket serverSocket = null;
// client socket.
    private static Socket clientSocket = null;

// Baðlanabilecek max client sayýsý.
   private static final int maxClientsCount = 100;
   private static final ClientThread[] threads = new ClientThread[maxClientsCount];
   public static int no = 0;

   public static void main(String args[]) {

 //  port numarasý.
     int portNumber = 5000;
     if (args.length < 1) {
         System.out.println("Server " + portNumber +" nolu porttan Clientlarý bekliyor: ");
                             
     } else {
            portNumber = Integer.valueOf(args[0]).intValue();
     }

 
 //---- bir port numarasý 1023 ten az olamaz.----//

   try {
      serverSocket = new ServerSocket(portNumber);
     } catch (IOException e) {
      System.out.println("Baðlantý hatasý!");
    }

   //----Her baðlanan client bir threade atýlýp baþlatýlýyor.
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) {
           (threads[i] = new ClientThread(clientSocket, threads)).start();
           ClientThread.id = no;
           no += 1;
            break;
          }
      }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server meþgul");
          os.close();
          clientSocket.close();
       }
     } catch (IOException e) {
       //System.out.println(e);
       System.out.println("hata");
     }
   }
  }  
}
