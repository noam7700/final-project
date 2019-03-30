import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_test {
	static final String serverHostname = "127.0.0.1"; // local host for now
	static final int port = 8080;

	public static void main(String args[]) {
		String urlEncodedData = "action=getData";// "action=register&username=a&password=b";
		String dataReceived = send(urlEncodedData);
		/** dataToSend options are:
		 * "action=register&username=${username}&password=${pass}" - to register new user 
		 * "action=getData" - to receive products data
		 * "action=getSavedBaskets&username=${username}&password=${pass}" - returns
		  		saved baskets if user details are valid, error otherwise
		 * "action=saveBasket&username=${username}&password=${pass}&basketData=${basket}"
		  		- adds the basket to user's saved baskets if details are valid, error
		 * otherwise else - data error.
		 */
		
		System.out.println("\nData received from server:");
		System.out.println(dataReceived);
	}

	public static String send(String dataToSend) {
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");

		Socket serverSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String dataReceived = null;
		try {
			serverSocket = new Socket(serverHostname, port);
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), "UTF-8"));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + serverHostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to get streams from server");
			System.exit(1);
		}

		System.out.println("Sending data to server..");
		out.println(dataToSend);
		System.out.println("Reading data from server:");
		try {
			String serveroutput;
			while ((serveroutput = in.readLine()) != null) {
				dataReceived += serveroutput + "\n";
			}
		} catch (IOException e) {
			System.err.println("Error reading data from server");
			System.err.println(e.getMessage());
		}
		System.out.println("Data from server finished.");

		/** Closing all the resources */
		try {
			out.close();
			in.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing resources");
			System.err.println(e.getMessage());
		}
		return dataReceived;
	}
}
