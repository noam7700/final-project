package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.google.api.client.util.Charsets;

public class Client {
	static final String serverHostname = "127.0.0.1"; // local host for now
	static final int port = 8085;
	static String clientPass = null;
	static String clientUsername = null;
	static final String error_msg = "Error";

	public static void main(String args[]) {
		String un = "user1", ps = "pass1", bskt = "חלב, ביצים, שישיית מים";
		StringBuilder msgReceived = new StringBuilder();
		System.out.println(send("bla", msgReceived));
		System.out.println("data from server: " + msgReceived);
		System.out.println();

//		System.out.println("query-register succeeded:" + register(un, ps, msgReceived));
//		System.out.println("data from server: " + msgReceived);
//		System.out.println();

		System.out.println("query-register succeeded:" + verifyUser(un, ps, msgReceived));
		System.out.println("data from server: " + msgReceived);
		System.out.println();

//		System.out.println("\nquery-saveBasket succeeded:" + saveBasket(bskt, msgReceived));
//		System.out.println("data from server: " + msgReceived);
//		System.out.println();

		System.out.println("query-getSavedBaskets succeeded:" + getSavedBaskets(msgReceived));
		System.out.println("data from server: " + msgReceived);
		System.out.println();

	}

	public static boolean isSigned() {
		return clientUsername != null && clientPass != null;
	}

	/**
	 * ----Explanation---- the functions returns true on success, false on failure.
	 * message sent by server written in {@msgReceived}
	 */
	public static boolean register(String un, String ps, StringBuilder msgReceived) {
		setDetails(un, ps);
		String dataToSend = "action=register&username=" + un + "&password=" + ps;
		return send(dataToSend, msgReceived);
	}

	public static boolean verifyUser(String un, String ps, StringBuilder msgReceived) {
		setDetails(un, ps);
		String dataToSend = "action=verifyUser&username=" + un + "&password=" + ps;
		return send(dataToSend, msgReceived);
	}

	public static boolean getSavedBaskets(StringBuilder msgReceived) {
		if (!isSigned())
			return false;
		String dataToSend = "action=getSavedBaskets&username=" + clientUsername + "&password=" + clientPass;
		return send(dataToSend, msgReceived);
	}

	public static boolean saveBasket(String basket, StringBuilder msgReceived) {
		if (!isSigned())
			return false;
		String dataToSend = "action=saveBasket&username=" + clientUsername + "&password=" + clientPass + "&basket="
				+ basket;
		return send(dataToSend, msgReceived);
	}

	public static boolean removeBasket(String basket, StringBuilder msgReceived) {
		if (!isSigned())
			return false;
		String dataToSend = "action=removeBasket&username=" + clientUsername + "&password=" + clientPass + "&basket="
				+ basket;
		return send(dataToSend, msgReceived);
	}

	public static boolean removeAllBaskets(String basket, StringBuilder msgReceived) {
		if (!isSigned())
			return false;
		String dataToSend = "action=removeAllBaskets&username=" + clientUsername + "&password=" + clientPass;
		return send(dataToSend, msgReceived);
	}

	public static boolean getData(StringBuilder msgReceived) {
		String dataToSend = "action=getData";
		return send(dataToSend, msgReceived);
	}

	private static void setDetails(String un, String ps) {
		clientUsername = un;
		clientPass = ps;
	}

	private static boolean send(String dataToSend, StringBuilder msgReceived) { // false for error, true otherwise
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//		SSLSocketFactory factory =
//                (SSLSocketFactory)SSLSocketFactory.getDefault();
//            SSLSocket serverSocket = null;
		Socket serverSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		if (msgReceived == null) {
			System.err.println("Error! msg StringBuilder sent is null. StringBuilder has to be created.");
			return false;
		} else
			msgReceived.setLength(0); // cleaning msg initial data
		boolean state = true;
		try {
			serverSocket = new Socket(serverHostname, port);
//			serverSocket = (SSLSocket)factory.createSocket(serverHostname, port);
//			serverSocket.startHandshake();
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), Charsets.UTF_8));

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
		out.println(dataToSend);
		System.out.println("data sent.");
		try {
			String serveroutput = in.readLine();
			if (serveroutput == null || serveroutput.equals(error_msg))
				state = false;
			while (serveroutput != null) {
				// System.out.println('\t'+serveroutput);
				msgReceived.append(serveroutput + '\n');
				serveroutput = in.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error reading data from server");
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
}
