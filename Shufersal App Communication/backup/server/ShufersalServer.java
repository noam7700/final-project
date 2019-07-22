package server;

import java.io.IOException;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import DatabaseComm.DBConnector;
import debug.debugserverThread;

public class ShufersalServer {
	public static final int PORT_NUMBER = 8085;

	public static void main(String[] args) {
		System.out.println("Starting server");
//		SSLServerSocket server = null;
		ServerSocket server = null;
		try {
//			ServerSocketFactory SSLfactory = SSLServerSocketFactory.getDefault();
//			server = (SSLServerSocket)SSLfactory.createServerSocket(PORT_NUMBER);
			server = new ServerSocket(PORT_NUMBER);
			DBConnector.connect();

			new DataUpdateThread(); // updates products data every 00:00
			while (true) { 
				new serverThread(server.accept());
			}

		} catch (IOException ex) {
			System.out.println("Unable to start server.");
			System.err.println(ex.getMessage());
		} finally {
			try {
				//DBConnector.closeConnection();
				if (server != null)
					server.close();
			} catch (IOException ex) {
				System.err.println("unable to close server socket");
				System.err.println(ex.getMessage());
			}
		}
	}
}