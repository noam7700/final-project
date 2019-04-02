import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.api.client.util.Charsets;

public class Client {
	static final String serverHostname = "127.0.0.1"; // local host for now
	static final int port = 8087;

	public static void main(String args[]) {
		String urlEncodedData = "action=register&username=1234&password=12345678";
		BufferedReader dataReceived = send(urlEncodedData);
		/**
		 * dataToSend options are:
		 * 
		 * "action=getData" - to receive products data
		 * "action=register&username=${username}&password=${pass}" - to register new user 
		 * "action=getSavedBaskets&username=${username}&password=${pass}" - returns
		  		saved baskets if user details are valid, error otherwise
		 * "action=saveBasket&username=${username}&password=${pass}&basketData=${basket}"
		  		- adds the basket to user's saved baskets if details are valid, error otherwise 
		 *  else - data error.
		 */

		System.out.println("\nData received from server:");
		try {
			String fromServer;
			while ((fromServer = dataReceived.readLine()) != null) {
				System.out.println(fromServer);
			}
		} catch (IOException e) {
			System.err.println("Error reading data from server");
			System.err.println(e.getMessage());
		}
	}

	public static BufferedReader send(String dataToSend) {
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");

		Socket serverSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String dataReceived = "";
		try {
			serverSocket = new Socket(serverHostname, port);
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), Charsets.UTF_8));

		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + serverHostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to get streams from server");
			System.exit(1);
		}

		System.out.println("Sending data to server..");
		out.println(dataToSend);
		System.out.println("data sent.");
		try {
			String serveroutput;
			while ((serveroutput = in.readLine()) != null) {
				dataReceived += serveroutput + "\n";
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
		return new BufferedReader(new StringReader(dataReceived));
	}
}
