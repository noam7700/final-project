package ServerComm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import DatabaseComm.DBExecutor;
import request_response.RequestObject;
import request_response.ResponseObject;

public class RequestParser {

	private static ResponseObject getData() {
		ResponseObject res = new ResponseObject();
		System.out.println("Getting Products data..");
		System.out.println("update lock is locked: " + Sync.updateLock.isLocked());
		while (Sync.updateLock.isLocked())
			; // waiting for update to execute
		synchronized (Sync.DataConsumers) {
			Sync.DataConsumers++;
		}
		File file = new File(".\\ProductsTextData.txt");
		res.setResponse(file);
		synchronized (Sync.DataConsumers) {
			Sync.DataConsumers--;
			Sync.DataConsumers.notifyAll(); // releases update-thread if it is waiting
		}
		return res;
	}

	static void parse(RequestObject req, ObjectOutputStream out) throws IOException {
		System.out.println("Parsing Client's request..");
//		Map<String, String> m = new HashMap<String, String>();
		ResponseObject res = new ResponseObject();
		if (!Validate(req)) {
			res.setError("query, username or password are null.");
		} else {
//			try {
			switch (req.getQuery()) {
			case register:
				res = register(req);
				break;
			case verifyUser:
				res = verifyUser(req);
				break;
			case getData:
				res = getData();
				break;
			case getBaskets:
				res = getBaskets(req);
				break;
			case saveBasket:
				res = saveBasket(req);
				break;
			case removeBasket:
				res = removeBasket(req);
				break;
			case removeAllBaskets:
				res = removeAllBaskets(req);
				break;
			default:
				res.setError("unknown query");
			}
			System.out.println("Client's request parsed.");
//			} catch (IOException e) {
//				System.err.println("Error in writing data to client");
//				System.err.println(e.getMessage());
//			}
		}
		out.writeObject(res);
	}

	private static ResponseObject verifyUser(RequestObject req) {
		ResponseObject res = new ResponseObject();
		String username = req.getUname(), password = req.getPass();
		if (DBExecutor.userExists(username, password))
			res.setResponse("Verification succeeded.");
		else
			res.setError("username or password are wrong.");
		return res;
	}

	private static boolean Validate(RequestObject req) {
		if (req.getQuery() == null || req.getUname() == null || req.getPass() == null)
			return false;
		return true;
	}

	private static ResponseObject saveBasket(RequestObject req) {
		ResponseObject res = new ResponseObject();
		String username = req.getUname(), password = req.getPass();
		Object basket = req.getObject();
		if (!DBExecutor.userExists(username, password)) // no such user
			res.setError("username or paswword are incorrect");
		DBExecutor.addBasket(username, getBytes(basket));
		res.setResponse("basket added successfuly");
		return res;
	}

	private static ResponseObject removeBasket(RequestObject req) {
		ResponseObject res = new ResponseObject();
		String username = req.getUname(), password = req.getPass();
		byte[] basket = getBytes(req.getObject());
		if (!DBExecutor.userExists(username, password)) // no such user
			res.setError("username or paswword are incorrect");
		DBExecutor.removeBasket(username, basket);
		res.setResponse("basket removed successfuly");
		return res;
	}

	private static ResponseObject removeAllBaskets(RequestObject req) {
		ResponseObject res = new ResponseObject();
		String username = req.getUname(), password = req.getPass();
		if (!DBExecutor.userExists(username, password)) // no such user
			res.setError("username or paswword are incorrect");
		DBExecutor.removeAllBaskets(username);
		res.setResponse("all baskets removed.");
		return res;
	}

	private static ResponseObject getBaskets(RequestObject req) {
		ResponseObject res = new ResponseObject();
		String username = req.getUname(), password = req.getPass();
		if (!DBExecutor.userExists(username, password)) {
			res.setError("username or paswword are incorrect");
		}

		// lock
//		ResultSet basketsRset = DBExecutor.getBaskets(username);
//		Object baskets = bsktArray(basketsRset); // array of baskets
		Object[] baskets = DBExecutor.getBaskets(username);
		// unlock

		res.setResponse(baskets);
		return res;
	}

	public static byte[] getBytes(Object object) {
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

//	private static Object bsktArray(ResultSet basketsRset) {
//		ArrayList<Object> baskets = new ArrayList<Object>();
//		try {
//			while (basketsRset.next()) {
////				Blob basketBlob = basketsRset.getBlob("basket");
//				byte[] basketBytes = basketsRset.getBytes("basket");
////				byte[] basketBytes = basketBlob.getBytes(1, (int) basketBlob.length());
//				Object bskt = getObject(basketBytes);
//				baskets.add(bskt);
////				basketBlob.free();
//			}
//			basketsRset.close();
//		} catch (SQLException e) {
//			System.err.println("Error in converting baskets to string");
//			System.err.println(e.getMessage());
//		}
//		Object[] basketsArr = baskets.toArray();
//		return basketsArr;
//	}

	private static ResponseObject register(RequestObject req) {
		ResponseObject res = new ResponseObject();
		String username = req.getUname(), password = req.getPass();
		if (verifyDetailsConstrains(username, password).Error())
			res = verifyDetailsConstrains(username, password);
		else if (DBExecutor.userExists(username)) {
			res.setError("username already exists.");
		} else {
			DBExecutor.addUser(username, password);
			res.setResponse("registeration completed");
		}
		return res;
	}

	// verify password/username length etc.
	private static ResponseObject verifyDetailsConstrains(String username, String password) {
		ResponseObject res = new ResponseObject();
		if (username.length() < 6 || password.length() < 6) {
			res.setError("username or password too short (required minimum of 6 letters).");
		}
//		if (password.length() < 6) {
//			res.setError("password too short");
//		}
		if (username.length() > 20 || password.length() > 20) {
			res.setError("username or password to long. maximum length is 20 letters.");
		}
//		if (password.length() > 20) {
//			res.setError("password too long");
//		}
		return res;
	}
}
