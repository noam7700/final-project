import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.UrlEncodedParser;

public class RequestParser {
	private RequestParser() {}
	private static byte[] getData() {
		System.out.println("Getting Products data..");
		while (!Sync.updateLock.tryLock()); // waiting for update to execute
		
		synchronized (Sync.counter_lock) {
			Sync.DataConsumers++;
		}
		File file = new File(".\\ProductsTextData.txt");

		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			System.err.println("Error converting product's text file to bytes");
		}
		synchronized (Sync.counter_lock) {
			Sync.DataConsumers--;
			Sync.counter_lock.notifyAll(); // releases update-thread if it is waiting
		}
		System.out.println(fileContent.toString());
		return fileContent;
	}
	
	static void parse(BufferedReader br, OutputStream out) {
		System.out.println("Parsing Client's request..");
		Map<String, String> m = new HashMap();
		// TODO: check validity of input data format. what happens if key not exists while executing map.get(key)?
		try {
			UrlEncodedParser.parse(br.readLine(), m); 
		} catch (Exception e) {
			System.err.println("Error parsing request.");
		}
		try {
			Object action = m.get("action");
			switch (action.toString()) {
			case "[register]":
				// addUser(m.get("username"), m.get("password"));
				out.write("user registered".getBytes());
				break;
			case "[getData]":
				out.write(getData());
				break;
			case "[getSavedBaskets]":
				// out.write(getBaskets(m.get("username"), m.get("password")));
				break;
			case "[saveBasket]":
				// saveBasket(m.get("username"), m.get("password"),m.get("basket"));
				out.write("basket saved".getBytes());
				break;
			}
		} catch (IOException e) {
			System.err.println("Error in writing data to client");
			System.err.println(e.getMessage());
		}
/* echo server example */
//		String request;
//		try {
//			while ((request = br.readLine()) != null) {
//				System.out.println("Message received:" + request);
//				request += '\n';
//				out.write(request.getBytes());
//			}
//		} catch (IOException ex) {
//			System.out.println("Unable to read streams from client");
//		}
	}

	
}
