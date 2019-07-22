package shufersalServerMain;

import java.io.IOException;
import java.net.ServerSocket;


import connection.DBConnector;
import handleMessageFromClient.ServerThread;
import updateContentFromWeb.DataUpdateThread;

public class ShufersalServer {
	 public static final int PORT_NUMBER = 8080;

	 public static void main(String[] args) {
		  System.out.println("Starting server");
//		SSLServerSocket server = null;
		  ServerSocket serverSocket = null;
		  try {
//			ServerSocketFactory SSLfactory = SSLServerSocketFactory.getDefault();
//			server = (SSLServerSocket)SSLfactory.createServerSocket(PORT_NUMBER);
			   serverSocket = new ServerSocket(PORT_NUMBER);
			   DBConnector.connect();

			   new DataUpdateThread(); // updates products data every 00:00
			   while (true) {
					new ServerThread(serverSocket.accept());
			   }

		  } catch (IOException ex) {
			   System.out.println("Unable to start server.");
			   System.err.println(ex.getMessage());
			   ex.printStackTrace();

		  } finally {
			   try {
					DBConnector.closeConnection();
					if (serverSocket != null)
						 serverSocket.close();
			   } catch (IOException ex) {
					System.err.println("unable to close server socket");
					System.err.println(ex.getMessage());
					ex.printStackTrace();
			   }
		  }
	 }
}