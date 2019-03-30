import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_test {

	public static void main(String args[]) {
		String host = "127.0.0.1";
		int port = 8085;
		String urlEncodedData = "action=getData";// &username=a&password=b";
		new Client_test(host, port, urlEncodedData);
		/*
		 * dataToSend options are:
		 * "action=register&username=${username}&password=${pass}" - to register new
		 * user "action=getData" - to receive products data
		 * "action=getSavedBaskets&username=${username}&password=${pass}" - returns
		 * saved baskets if user details are valid, error otherwise
		 * "action=saveBasket&username=${username}&password=${pass}" - adds the basket
		 * to user's saved baskets if details are valid, error otherwise else - data
		 * error.
		 */
	}

	public Client_test(String host, int port, String dataToSend) {
		try {
			String serverHostname = new String("127.0.0.1");

			System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");

			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;

			try {
				echoSocket = new Socket(serverHostname, port);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			} catch (UnknownHostException e) {
				System.err.println("Unknown host: " + serverHostname);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Unable to get streams from server");
				System.exit(1);
			}

			/** {@link UnknownHost} object used to read from console */
			// BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader stdIn = new BufferedReader(new StringReader(dataToSend));
//			
//				System.out.print("client: ");
//				String userInput = stdIn.readLine();
//				out.println(userInput);
			System.out.println("Sending data to server..");
			out.println(dataToSend);
			System.out.println("Reading data from server:");
			while (true) {
				String serveroutput = in.readLine();
				/** Exit on "quit" string sent */

				if (serveroutput == null || serveroutput.equals("quit")) {
					break;
				}
				System.out.println("from server: " + serveroutput);
			}
			System.out.println("Data from server finished.");

			/** Closing all the resources */
			out.close();
			in.close();
			// stdIn.close();
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
