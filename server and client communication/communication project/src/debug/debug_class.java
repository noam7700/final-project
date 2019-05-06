package debug;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.api.client.http.UrlEncodedParser;

import Client_side.Client;
import DatabaseComm.DBConnector;
import DatabaseComm.DBExecutor;
import request_response.AcceptedQuery;
import request_response.RequestObject;
import request_response.ResponseObject;

public class debug_class implements Serializable {
	public int field = 102;

	static void printBaskets(ResultSet rs) {
		try {

			System.out.println("baskets:");
			// TODO rs.getRow()
			while (rs.next()) {
				String bs = rs.getString("basket");
				System.out.println("\t" + bs);
			}
		} catch (SQLException e) {
			System.err.println("cannot print tables");
			System.err.println(e.getMessage());
		}
	}

	static void printTable1() {
		try {
			ResultSet rs = DBConnector.getStatement().executeQuery("SELECT * from usersdetails");
			while (rs.next()) {
				String us = rs.getString("username");
				String ps = rs.getString("password");
				System.out.println("username = " + us + " password = " + ps);
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("cannot print tables");
			System.err.println(e.getMessage());
		}
	}

	static void printTable2() {
		try {
			ResultSet rs = DBConnector.getStatement().executeQuery("SELECT * from usersbaskets");
			while (rs.next()) {
				String us = rs.getString("username");
				String bs = rs.getString("basket");
				System.out.println("username = " + us + " basket = " + bs);
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("cannot print tables");
			System.err.println(e.getMessage());
		}
	}

	public static byte[] getByteArrayObject(Object object) {
		byte[] objectBytes = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.close();
			bos.close();
			objectBytes = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return objectBytes;
		}
		return objectBytes;
	}

	public static Object getObject(byte[] bytes) {

		Object object = bytes;
		;// null;
		ByteArrayInputStream bais;
		ObjectInputStream ins;
		try {
			bais = new ByteArrayInputStream(bytes);
			ins = new ObjectInputStream(bais);
			object = (Object) ins.readObject();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	public static void main(String args[]) throws SQLException, InterruptedException {

		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] result = mDigest.digest("blabla".getBytes());
		String rawSha1 = new String(result);
		System.out.println(rawSha1);
		try {
			String hashed = sha1("blabla");
			System.out.println(hashed + ", sha1 len:" + hashed.length() + ", raw sha1 len:" + rawSha1.length());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Data[] data = new Data[10];
		data[0] = new Data();
		data[1] = new Data();

		data[0].data = "data0";
		data[1].data = "data1";
		byte[] bytes = getByteArrayObject(data);
		Object o = getObject(bytes);
		Data[] data2 = (Data[]) o;
		System.out.println(data2[0].getData() + ", " + data2[1].getData());

//		DBConnector.connect();
//		System.out.println("tables before:");
//		System.out.println(DBExecutor.userExists("[a]"));
//		printTable1();
//		printTable2();
//		System.out.println(DBExecutor.verifyUser("AmitUsername", "AmitPassword"));
//		System.out.println(DBExecutor.userExists("AmitUsername"));
//		DBExecutor.addBasket("AmitUsername", "מלפפון עגבניה ופלפל");
//		DBExecutor.addBasket("AmitUsername", "גמבה וחלב");
//
//		DBExecutor.addUser("user1", "pass1");
//		ResultSet rs = DBExecutor.retreiveBaskets("AmitUsername");
//		printBaskets(rs);
//		System.out.println();
//		try {
//			ResultSet r = DBExecutor.retreiveBaskets("AmitUsername");
//			//printBaskets(r);
//			r.next();
//	        InputStream is = new ByteArrayInputStream(r.getBytes("basket"));
//			BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//			System.out.println(in.readLine());
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		rs.close();
//		printTable1();
//		printTable2();
//		DBExecutor.cleanTables();
//		DBConnector.closeConnection();
//		String command = new String("node readProductsData.js&&exit"); // script has to be in project's directory
//		try {
//			Process p = Runtime.getRuntime().exec("cmd /c start /wait cmd.exe /K \"" + command + "\"");
//			p.waitFor(); // waiting for script to be finished
//		} catch (Exception e) {
//			System.err.println("Error running nodejs script");
//			e.printStackTrace();
//		}
		StringBuilder sb = null;// new StringBuilder("Hello");
		Object b = new debug_class();
		if (b instanceof debug_class) {
			System.out.println(b.getClass());
		}
//		DBConnector.connect();
//		DBExecutor.cleanTables();
//		DBConnector.closeConnection();
		String r = "action=register&username=.*&password=.*";
		System.out.println();
		// System.out.println(q.replaceAll(r, "Hello world"));
		for (int i = 0; i <= 23; i++) {
			String time = String.valueOf(i) + ":59:59";
			if (i < 10)
				time = "0" + time;
			System.out.println(calcTimeTill("23:59:59") - calcTimeTill(time));
		}
		System.out.println("from now: " + calcTimeTill("23:59:59"));

		int numwriters, numreaders;
		numwriters = numreaders = 25;

//		DBConnector.connect();
//		writer[] writers = new writer[numwriters];
//		reader[] readers = new reader[numreaders];
//		remover[] removers = new remover[numwriters];
//
//		debug_class db = new debug_class();
//		for (int i = 0; i < numreaders + numwriters; i++) {
//			if (i % 2 == 0) {
//				writers[i / 2] = db.new writer();
//				writers[i / 2].start();
//			}
////			else {
////				readers[(i-1)/2] = db.new reader();
////				readers[(i-1)/2].start();
////			}
//			else {
//				removers[(i - 1) / 2] = db.new remover("Thread-" + ((i - 1) / 2));
//				removers[(i - 1) / 2].start();
//			}
//		}
//		for (int i = 0; i < numwriters; i++) {
////			readers[i].join();
//			writers[i].join();
//		}
		Data d = new Data();
		t(d);
		System.out.println(d.getData());

		Map<String, ArrayList<String>> m = new HashMap<String, ArrayList<String>>();
		UrlEncodedParser.parse("a=b&c=d", m);
		System.out.println(m.get("a").get(0));
		Integer i = 0;
		i++;
		// t(ss);
//		final String un = "[1234]";
//		DBExecutor.addUser("[user]", "sgbdbd");
//		ResultSet baskets = DBExecutor.retreiveBaskets("[user]");
//		DBExecutor.removeBasket(un, "Thread-4");
//		if (!baskets.isClosed() && baskets.next()) {
//			// System.out.println(baskets.);
//			System.out.println(baskets.getString("basket"));
//		}
//		DBConnector.closeConnection();

	}

	static void t(Data o) {
		o.data = (Object) "svsf";
	}
//	static void sendAndPrint(Object value, AcceptedQuery q, String un, String ps){
//		ResponseObject ro;
//		if(q == AcceptedQuery.getBaskets) {
//			ro = Client.getSavedBaskets();
//			
//		}
//		switch(q) {
//		case register:
//			ro = Client.register(un, ps);
//			break;
//		case getData:
//			ro = Client.getData();
//			break;
//		case saveBasket:
//			ro = Client.saveBasket(value);
//			break;
//		case removeBasket:
//			ro = Client.removeBasket(value);
//			break;
//		case verifyUser:
//			ro = Client.verifyUser(un, ps);
//			break;
//		}
//	}

	static long calcTimeTill(String time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date dateTo = null, zero_p = null;
		try {
			dateTo = format.parse(time);
			zero_p = format.parse("00:00:00");
		} catch (Exception e) {
			System.err.println("Error in parsing time for update-thread to sleep");
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long zero_point = cal.getTimeInMillis();
		long timeNow = System.currentTimeMillis() - zero_point;
		long timeTo = dateTo.getTime() - zero_p.getTime();
		return timeTo - timeNow;

	}

	public class reader extends Thread {
		public void run() {
			System.out.println("reader " + this.getName() + ": ");
		}
	}

	public class writer extends Thread {
		public void run() {
//			DBExecutor.addBasket("[1234]", this.getName());
			System.out.println("writer " + this.getName() + " added its name");
		}
	}

	public class remover extends Thread {
		String basket;

		public remover(String basketToRemove) {
			basket = basketToRemove;
		}

		public void run() {
//			DBExecutor.removeBasket("[1234]", basket);
			System.out.println("remover " + this.getName() + " removed basket: " + basket);
		}
	}

	public static class Data implements Serializable {
		Object data = null;

		public Object setData(Object o) {
			return data = o;
		}

		public Object getData() {
			return data;
		}
	}
}
