
package handleMessageFromClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import communicationObjects.ClientQuery;
import communicationObjects.Products;
import communicationObjects.Request;
import communicationObjects.ResponseObject;
import communicationObjects.User;
import databaseClasses.Baskets;
import execution.DBExecutor;
import updateContentFromWeb.FileAccessSynchronization;

/**
 * @author amit
 * 
 *         This class rule is to parse the contents of a client request and
 *         handle it.
 *
 */
public class ClientRequestManager {

	 private static File getFileData() {
		  System.out.println("Getting Products data..");
		  System.out.println("update lock is locked: " + FileAccessSynchronization.getUpdateLock().isLocked());
		  while (FileAccessSynchronization.getUpdateLock().isLocked()) {
		  } // waiting for update to execute
		  synchronized (FileAccessSynchronization.currentFileConsumersCount) {
			   FileAccessSynchronization.currentFileConsumersCount++;
		  }
		  File file = new File(".\\ProductsTextData.txt");
		  synchronized (FileAccessSynchronization.currentFileConsumersCount) {
			   FileAccessSynchronization.currentFileConsumersCount--;
			   FileAccessSynchronization.currentFileConsumersCount.notifyAll(); // releases update-thread if it is
			                                                                    // waiting
		  }
		  return file;
	 }

	 private static Products getProductsData() {
		  try {
			   return DBExecutor.getProducts();
		  } catch (SQLException e) {
			   e.printStackTrace();
			   return new Products(new ArrayList<>());
		  }
	 }

	 static void handle(Request req, ObjectOutputStream out) throws IOException {
		  System.out.println("Parsing Client's request..");

		  Object res;

		  ClientQuery query = req.getQuery();

		  if (isGuestPrivilege(query)) {
			   res = guestQueriesHandler(req);
		  } else if (verifyLoggedIn(req)) {
			   res = systemUsersQueriesHandler(req);
		  } else {
			   res = null;
		  }

		  System.out.println("Client's request parsed.");

		  out.writeObject(res);
	 }

	 private static boolean isGuestPrivilege(ClientQuery query) {
		  if (query == ClientQuery.REGISTER || query == ClientQuery.LOGIN || query == ClientQuery.GET_PRODUCTS_DATA|| query == ClientQuery.SEARCH_PRODUCTS) {
			   return true;
		  } else {
			   return false;
		  }
	 }

	 private static Object systemUsersQueriesHandler(Request req) {

		  switch (req.getQuery()) {

			   case GET_BASKET:
					return getBaskets(req);

			   case SAVE_BASKET:
					saveBasket(req);

			   case REMOVE_BASKET:
					removeBasket(req);

			   case REMOVE_ALL_BASKETS:
					removeAllBaskets(req);

			   default:
					break;
		  }
		  return null;
	 }

	 private static Object guestQueriesHandler(Request req) {
		  switch (req.getQuery()) {
			   case REGISTER:
					return register(req);
			   case LOGIN:
					return login(req);
			   case GET_PRODUCTS_DATA:
					return getProductsData();
			   case SEARCH_PRODUCTS:
					return searchProductsByDescription(req);

			   default:
					return null;
		  }

	 }

	 private static Products searchProductsByDescription(Request req) {
		  Object object = req.getObject();
		  try {
			   String description = (String) object;
			   return DBExecutor.searchProductsByDescription(description);
		  } catch (Exception e) {
			   return new Products(new ArrayList<>());
		  }
	 }

	 private static boolean verifyLoggedIn(Request req) {
		  return login(req) != null;
	 }

	 private static User login(Request req) {
		  String username = req.getUname(), password = req.getPass();
		  return DBExecutor.login(username, password);
	 }

	 private static boolean Validate(Request req) {
		  if (req.getQuery() == null || req.getUname() == null || req.getPass() == null)
			   return false;
		  return true;
	 }

	 private static void saveBasket(Request req) {
		  String username = req.getUname();
		  Object basket = req.getObject();
		  DBExecutor.addBasket(username, getBytes(basket));
	 }

	 private static void removeBasket(Request req) {
		  String username = req.getUname();
		  byte[] basket = getBytes(req.getObject());
		  DBExecutor.removeBasket(username, basket);
	 }

	 private static void removeAllBaskets(Request req) {
		  String username = req.getUname();
		  DBExecutor.removeAllBaskets(username);
	 }

	 private static Baskets getBaskets(Request req) {
		  String username = req.getUname();

		  Baskets baskets = DBExecutor.getBaskets(username);
		  return baskets;
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

	 // private static Object bsktArray(ResultSet basketsRset) {
//			   		ArrayList<Object> baskets = new ArrayList<Object>();
//			   		try {
//			   			while (basketsRset.next()) {
////			   				Blob basketBlob = basketsRset.getBlob("basket");
//			   				byte[] basketBytes = basketsRset.getBytes("basket");
////			   				byte[] basketBytes = basketBlob.getBytes(1, (int) basketBlob.length());
//			   				Object bskt = getObject(basketBytes);
//			   				baskets.add(bskt);
////			   				basketBlob.free();
//			   			}
//			   			basketsRset.close();
//			   		} catch (SQLException e) {
//			   			System.err.println("Error in converting baskets to string");
//			   			System.err.println(e.getMessage());
//			   		}
//			   		Object[] basketsArr = baskets.toArray();
//			   		return basketsArr;
	 // }

	 private static boolean register(Request req) {
		  User user;
		  String password;
		  try {
			   password = req.getPass();
			   user = (User) req.getObject();
		  } catch (Exception e) {
			   return false;
		  }
//			   		  if (verifyDetailsConstrains(username, password).Error())
//			   			   res = verifyDetailsConstrains(username, password);
//			   		  else if (DBExecutor.userExists(username)) {
//			   			   res.setError("username already exists.");
//			   		  } else {
		  boolean success = DBExecutor.register(user, password);
//			   		  }
		  return success;
	 }

	 // verify password/username length etc.
	 private static ResponseObject verifyDetailsConstrains(String username, String password) {
		  ResponseObject res = new ResponseObject();
		  if (username.length() < 6 || password.length() < 6) {
			   res.setError("username or password too short (required minimum of 6 letters).");
		  }
//			   		if (password.length() < 6) {
//			   			res.setError("password too short");
//			   		}
		  if (username.length() > 20 || password.length() > 20) {
			   res.setError("username or password to long. maximum length is 20 letters.");
		  }
//			   		if (password.length() > 20) {
//			   			res.setError("password too long");
//			   		}
		  return res;
	 }
}
