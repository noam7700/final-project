package debug;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Client_side.Client;
import debug.debug_class.Data;

public class debugClient {
	static final String serverHostname = "127.0.0.1"; // local host for now
	static final int port = 8084;
	static String clientPass = null;
	static String clientUsername = null;
	static final String error_msg = "Error";

	public static void main(String[] args) {
		System.out.println("starting client");
		debug_class.Data d = new debug_class.Data();
		Object o = new debug_class();
		send(o, d);
	}

	private static boolean send(Object dataToSend, Data msgReceived) { // false for error, true otherwise
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//			SSLSocketFactory factory =
//	                (SSLSocketFactory)SSLSocketFactory.getDefault();
//	            SSLSocket serverSocket = null;
		Socket serverSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		if (msgReceived == null) {
			System.err.println("Error! msg StringBuilder sent is null. StringBuilder has to be created.");
			return false;
		}
//			else
//				msgReceived.setLength(0); // cleaning msg initial data
		boolean state = true;
		try {
			serverSocket = new Socket(serverHostname, port);
//				serverSocket = (SSLSocket)factory.createSocket(serverHostname, port);
//				serverSocket.startHandshake();
			out = new ObjectOutputStream(serverSocket.getOutputStream());
			in = new ObjectInputStream(serverSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + serverHostname);
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to get streams from server");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("Sending data to server..");
		try {
			out.writeObject(dataToSend);
		} catch (IOException e1) {
			System.err.println("error in sending data to server");
			System.err.println(e1.getMessage());
		}
		System.out.println("data sent.");
		try {
			Object rec = in.readObject();
			System.out.println("Object from server toString(): " + rec.toString());
		} catch (ClassNotFoundException e) {
			System.err.println("Error receiving data from server");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println("Error receiving data from server");
			System.err.println(e.getMessage());
		}
		/** Closing all the resources */
		try {
			out.close();
			in.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing resources");
			System.err.println(e.getMessage());
		}

		return state;
	}

	private static boolean checkError(Object rec) {
		// Auto-generated method stub
		return true;
	}
}
// private static boolean send(String dataToSend, debug_class.data
// objectReceived) { // false for error, true otherwise
// System.out.println("Connecting to host " + serverHostname + " on port " +
// port + ".");
//// SSLSocketFactory factory =
////	        (SSLSocketFactory)SSLSocketFactory.getDefault();
////	    SSLSocket serverSocket = null;
// Socket serverSocket = null;
// ObjectOutputStream out = null;
// ObjectInputStream in = null;
// if (objectReceived == null) {
//		System.err.println("Error! client's object to receive var is null. it has to be created.");
//		return false;
// }
// boolean state = true;
// try {
//		serverSocket = new Socket(serverHostname, port);
////		serverSocket = (SSLSocket)factory.createSocket(serverHostname, port);
////		serverSocket.startHandshake();
//		out = new ObjectOutputStream(serverSocket.getOutputStream());
//		in = new ObjectInputStream(serverSocket.getInputStream());
//
// } catch (UnknownHostException e) {
//		System.err.println("Unknown host: " + serverHostname);
//		System.err.println(e.getMessage());
//		System.exit(1);
// } catch (IOException e) {
//		System.err.println("Unable to get streams from server");
//		System.err.println(e.getMessage());
//		System.exit(1);
// }
// System.out.println("Sending data to server..");
// try {
//		out.writeObject(dataToSend);
// } catch (IOException e1) {
//		System.err.println("error writing object to server");
//		e1.printStackTrace();
// }
// System.out.println("data sent.");
// try {
//		Object rec = in.readObject();
//		if (checkError(rec))
//			state = false;
//		objectReceived.setData(rec);
//		System.out.println("object toString() from server: " + rec.toString());
//
// } catch (ClassNotFoundException e1) {
//		System.err.println("error reading object to server");
//		e1.printStackTrace();
// } catch (IOException e1) {
//		System.err.println("error reading object to server");
//		e1.printStackTrace();
// }
//
/// ** Closing all the resources */
// try {
//		out.close();
//		in.close();
//		serverSocket.close();
// } catch (IOException e) {
//		System.err.println("Error closing resources");
//		System.err.println(e.getMessage());
// }
// return state;
// }
