package ServerComm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.util.Charsets;

import DatabaseComm.DBExecutor;

public class RequestParser {
	private RequestParser() {
	}

	// private static final String[] convetinalQueriesFormat = {...}
	private static byte[] getData() {
		System.out.println("Getting Products data..");
		System.out.println("update lock is locked: " + Sync.updateLock.isLocked());
		while (Sync.updateLock.isLocked())
			; // waiting for update to execute

		synchronized (Sync.counter_lock) {
			Sync.DataConsumers++;
		}
		File file = new File(".\\ProductsTextData.txt");
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (Exception e) {
			System.err.println("Error converting product's text file to bytes");
			System.err.println(e.getMessage());
		}
		synchronized (Sync.counter_lock) {
			Sync.DataConsumers--;
			Sync.counter_lock.notifyAll(); // releases update-thread if it is waiting
		}
		System.out.println(fileContent.toString());
		return fileContent;
	}

	static boolean verifyUser(String username, String password) {
		return true;
	}

	static void parse(BufferedReader input, OutputStream out) {
		System.out.println("Parsing Client's request..");
		Map<String, String> m = new HashMap<String, String>();
		// TODO: check validity of input data format. what happens if key not exists
		// while executing map.get(key)?
		try {
			if(!ValidateFormat(m)) {
				out.write("unknown query".getBytes()); 
				return;
			}
			UrlEncodedParser.parse(input.readLine(), m);
		} catch (IOException e) {
			System.err.println("Error parsing request. -- fromat not valid");
		}
		try {
			byte[] msg;
			Object action = m.get("action");
			Object un, ps, bskt;
			switch (action.toString()) {
			case "[register]":
				un = m.get("username");
				ps = m.get("password");
				msg = register(un.toString(), ps.toString()).getBytes();
				break;
			case "[getData]":
				msg = getData();
				break;
			case "[getSavedBaskets]":
				un = m.get("username");
				ps = m.get("password");
				msg = getBaskets(un.toString(), ps.toString());
				break;
			case "[saveBasket]":
				un = m.get("username");
				ps = m.get("password");
				bskt = m.get("basket");
				msg = saveBasket(un.toString(), ps.toString(), bskt.toString()).getBytes();
				break;
			default:
				msg = "unknown action".getBytes();
			}
			out.write(msg);
		} catch (IOException e) {
			System.err.println("Error in writing data to client");
			System.err.println(e.getMessage());
		}
	}

	private static boolean ValidateFormat(Map<String, String> m) {
		// TODO Auto-generated method stub
		
		return true;
	}

	private static String saveBasket(String username, String password, String basket) {
		if (!DBExecutor.verifyUser(username, password)) // no such user
			return "Error, username or paswword are incorrect";
		DBExecutor.addBasket(username, basket);
		return "basket added successfuly";
	}

	private static byte[] getBaskets(String username, String password) {
		if (!DBExecutor.verifyUser(username, password))
			return "Error, username or paswword are incorrect".getBytes();
		ResultSet basketsRset = DBExecutor.retreiveBaskets(username);
		try {				
			return bsktRsetToString(basketsRset).getBytes(Charsets.UTF_8);
		} catch (Exception e) {
			System.err.println("unable to get baskets from DB");
			System.err.println(e.getMessage());
		}
		return null;
	}

	private static String bsktRsetToString(ResultSet baseketsRset) {
		String baskets = "";
		try {
		while (baseketsRset.next()) {
			baskets += baseketsRset.getString("basket");
		}
		}catch(SQLException e) {
			System.err.println("Error in converting baskets to string");
			System.err.println(e.getMessage());
		}
		return baskets;
	}

	private static String register(String username, String password) {
		StringBuilder msg = new StringBuilder();
		if (!verifyDetailsConstrains(username, password, msg))
			return "username or password are not valid. " + msg;
		if (DBExecutor.userExists(username)) {
			return "username already exists.";
		}

		DBExecutor.addUser(username, password);
		return "registaration completed";
	}

	private static boolean verifyDetailsConstrains(String username, String password, StringBuilder msg) { // password/username																							// length, etc.
		if(username.length() < 6) { msg.append("username too short"); return false;} // length 6 = 4 characters
		if(password.length() < 6) {msg.append("password too short"); return false;}
		if(username.length() > 20) {msg.append("username too long"); return false;}
		if(password.length() > 20) { msg.append("username too long"); return false;}
		return true;
	}
}
