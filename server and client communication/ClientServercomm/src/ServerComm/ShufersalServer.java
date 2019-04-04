package ServerComm;

import java.io.IOException;
import java.net.ServerSocket;

import DatabaseComm.DBConnector;

public class ShufersalServer {
	public static final int PORT_NUMBER = 8087;

	public static void main(String[] args) {
		System.out.println("Starting server");
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT_NUMBER);
			DBConnector.connect();
			
			new DataUpdateThread(); // updates products data every 00:00
			while (true) { // always listening to client requests
				new serverThread(server.accept());
				// creating thread to handle client's request (thus able to handle multiple
				// requests)
			}

		} catch (IOException ex) {
			System.out.println("Unable to start server.");
		} finally {
			try {
				DBConnector.closeConnection();
				if (server != null)
					server.close();
			} catch (IOException ex) {
				System.err.println("unable to close server socket");
				System.err.println(ex.getMessage());
			}
		}
	}
}