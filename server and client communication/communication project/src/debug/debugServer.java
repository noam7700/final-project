package debug;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import DatabaseComm.DBConnector;
import ServerComm.DataUpdateThread;
import ServerComm.serverThread;

public class debugServer {
	public static final int PORT_NUMBER = 8084;

	public static void main(String[] args) {
		System.out.println("Starting server");
//			SSLServerSocket server = null;
		ServerSocket server = null;
		try {
//				ServerSocketFactory SSLfactory = SSLServerSocketFactory.getDefault();
//				server = (SSLServerSocket)SSLfactory.createServerSocket(PORT_NUMBER);
			server = new ServerSocket(PORT_NUMBER);
			while (true) {
				new debugserverThread(server.accept());
			}

		} catch (IOException ex) {
			System.out.println("Unable to start server.");
			System.err.println(ex.getMessage());
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException ex) {
				System.err.println("unable to close server socket");
				System.err.println(ex.getMessage());
			}
		}
	}

}
//	try {
//		server = new ServerSocket(PORT_NUMBER);
//	} catch (IOException e) {
//		System.err.println("cannot create server");
//		System.err.println(e.getMessage());
//		e.printStackTrace();
//	}
//	Socket socket = null;
//	try {
//		while (true) {
//			new serverThread(server.accept());
//		}
//
//	} catch (IOException ex) {
//		System.out.println("Unable to start server.");
//	} finally {
//		try {
//			DBConnector.closeConnection();
//			if (server != null)
//				server.close();
//		} catch (IOException ex) {
//			System.err.println("unable to close server socket");
//			System.err.println(ex.getMessage());
//		}
//	}
//
//	// TODO Auto-generated catch block
//
//	ObjectInputStream in = null;
////	try {
////		in = new ObjectInputStream(socket.getInputStream());
////	} catch (IOException e) {
////		System.err.println(e.getMessage());
////		e.printStackTrace();
////	}
////	ObjectOutputStream out = null;
////	try {
////		out = new ObjectOutputStream(socket.getOutputStream());
////	} catch (IOException e) {
////		System.err.println(e.getMessage());
////		e.printStackTrace();
////	}
////	System.out.println("received from client: " + in.toString());
////	try {
////		out.writeObject("glbjndl");
////	} catch (IOException e) {
////		System.err.println(e.getMessage());
////		e.printStackTrace();
////	}
////
////} catch (Exception ex) {
////	System.out.println("Unable to start server.");
////} finally {
////	try {
////		DBConnector.closeConnection();
////		if (server != null)
////			server.close();
////	} catch (IOException ex) {
////		System.err.println("unable to close server socket");
////		System.err.println(ex.getMessage());
////	}
////}
//} catch (Exception ex) {
//	System.err.println("unable to close server socket");
//	System.err.println(ex.getMessage());
//}
