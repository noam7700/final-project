package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.util.Charsets;

import db.DBExecutor;

public class RequestParser {

	private static final String error_msg = "Error" + '\n';
	private static final String[] acceptedQueries = { "action=getData", "action=register&username=.*&password=.*",
			"action=verifyUser&username=.*&password=.*", "action=saveBasket&username=.*&password=.*&basket=.*",
			"action=getSavedBaskets&username=.*&password=.*" };

	private static byte[] getData() {
		System.out.println("Getting Products data..");
		System.out.println("update lock is locked: " + Sync.updateLock.isLocked());
		while (Sync.updateLock.isLocked())
			; // waiting for update to execute
		//if(!Sync.updateLock.tryLock()) { Sync.updateLock.lock(); Sync.updateLock.unlock();} else{
		synchronized (Sync.DataConsumers) {
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
		synchronized (Sync.DataConsumers) {
			Sync.DataConsumers--;
			Sync.DataConsumers.notifyAll(); // releases update-thread if it is waiting
		}
		return fileContent;
	}

	static void parse(BufferedReader input, OutputStream out) throws IOException {
		System.out.println("Parsing Client's request..");
		String query = input.readLine();
//		Map<String, String> m = new HashMap<String, String>();
		Map<String,ArrayList<String>> m = new HashMap<String,ArrayList<String>>();

		try {
			if (!ValidateFormat(query)) {
				out.write((error_msg + "unknown query").getBytes());
				return;
			}
			UrlEncodedParser.parse(query, m);
		} catch (IOException e) {
			System.err.println("Error parsing request. -- fromat not valid");
			System.err.println(e.getMessage());
			try {
				System.out.println("data sent from client is: " + input.readLine());
			} catch (IOException ex) {
				System.err.println("Error reading client's data");
				System.err.println(ex.getMessage());
			}
		}
		try {
			byte[] msg;
			Object action = m.get("action");
			Object un = m.get("username"), ps = m.get("password"), bskt = m.get("basket");
			String actionDesc = action.toString();
			switch (actionDesc) {
			case "[register]":
				msg = register(un.toString(), ps.toString()).getBytes();
				break;
			case "[verifyUser]":
				msg = verifyUser(un.toString(), ps.toString()).getBytes();
				break;
			case "[getData]":
				msg = getData();
				break;
			case "[getSavedBaskets]":
				msg = getBaskets(un.toString(), ps.toString());
				break;
			case "[saveBasket]":
				msg = saveBasket(un.toString(), ps.toString(), bskt.toString()).getBytes();
				break;
			case "[removeBasket]":
				msg = removeBasket(un.toString(), ps.toString(), bskt.toString()).getBytes();
				break;
			case "[removeAllBaskets]":
				msg = removeAllBaskets(un.toString(), ps.toString()).getBytes();
				break;
			default:
				msg = (error_msg + "unknown action").getBytes();
			}

			System.out.println("Client's request parsed.");
//			ObjectOutputStream oout = new ObjectOutputStream(out);
			out.write(msg);
		} catch (IOException e) {
			System.err.println("Error in writing data to client");
			System.err.println(e.getMessage());
		}
	}

	private static String verifyUser(String un, String ps) {
		if (DBExecutor.userExists(un, ps))
			return "Verification succeeded.";
		else
			return error_msg + "username or password are wrong.";
	}

	private static boolean ValidateFormat(String input) {
		// String rgx = "action=.*(&username=.*&password=.*(&basket=.*)?)?"; // general
		// query pattern
		for (String query : acceptedQueries)
			if (input.matches(query))
				return true;
		// map[query](m);
		return false;
	}

	private static String saveBasket(String username, String password, String basket) {
		if (!DBExecutor.userExists(username, password)) // no such user
			return error_msg + "username or paswword are incorrect";
		DBExecutor.addBasket(username, basket);
		return "basket added successfuly";
	}

	private static String removeBasket(String username, String password, String basket) {
		if (!DBExecutor.userExists(username, password)) // no such user
			return error_msg + "username or paswword are incorrect";
		DBExecutor.removeBasket(username, basket);
		return "basket removed successfuly";
	}

	private static String removeAllBaskets(String username, String password) {
		if (!DBExecutor.userExists(username, password)) // no such user
			return error_msg + "username or paswword are incorrect";
		DBExecutor.removeBasket(username, "*");
		return "All baskets removed";
	}

	private static byte[] getBaskets(String username, String password) {
		if (!DBExecutor.userExists(username, password))
			return (error_msg + "username or paswword are incorrect").getBytes();
		// lock
		ResultSet basketsRset = DBExecutor.retreiveBaskets(username);
		String Baskets = bsktRsetToString(basketsRset);

		// unlock
		try {
			return Baskets.getBytes();
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
		} catch (SQLException e) {
			System.err.println("Error in converting baskets to string");
			System.err.println(e.getMessage());
		}
		return baskets;
	}

	private static String register(String username, String password) {
		StringBuilder msg = new StringBuilder();
		if (!verifyDetailsConstrains(username, password, msg))
			return error_msg + "username or password are not valid. " + msg;
		if (DBExecutor.userExists(username)) {
			return error_msg + "username already exists.";
		}

		DBExecutor.addUser(username, password);
		return "registaration completed";
	}

	// verify password/username length etc.
	private static boolean verifyDetailsConstrains(String username, String password, StringBuilder msg) {
		if (username.length() < 6) {
			msg.append(error_msg + "username too short");
			return false;
		} // length 6 = 4 characters
		if (password.length() < 6) {
			msg.append(error_msg + "password too short");
			return false;
		}
		if (username.length() > 20) {
			msg.append(error_msg + "username too long");
			return false;
		}
		if (password.length() > 20) {
			msg.append(error_msg + "username too long");
			return false;
		}
		return true;
	}
}
