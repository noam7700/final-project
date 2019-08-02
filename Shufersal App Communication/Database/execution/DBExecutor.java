package execution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import communicationObjects.BasketContent;
import communicationObjects.BasketsContent;
import communicationObjects.ProductInfo;
import communicationObjects.Products;
import communicationObjects.User;
import connection.DBConnector;
import execution.genericSqlQueriesExecution.GenericSqlQueriesExecutor;

public class DBExecutor {
	 static final String              usersdetailsTable         = "usersDetails";
	 static final String              usersbasketsTable         = "usersBaskets";
	 static final String              productsTable             = "products_info";
	 static ReentrantLock             DBLock                    = new ReentrantLock();
	 static GenericSqlQueriesExecutor genericSqlQueriesExecutor = new GenericSqlQueriesExecutor(DBConnector.connect());

	 public static void cleanTables() {
		  try {
			   synchronized (DBLock) {
					DBConnector.getStatement().execute("truncate table " + usersdetailsTable + ";");
					DBConnector.getStatement().execute("truncate table " + usersbasketsTable + ";");
			   }
		  } catch (SQLException e) {
			   System.err.println("Error in erasing tables data");
			   System.err.println(e.getMessage());
		  }
	 }

	 public static void cleanProductsTable() throws SQLException {
		  synchronized (DBLock) {
			   DBConnector.getStatement().execute("truncate table " + productsTable + ";");
		  }

	 }

	 public static User login(String username, String password) {
		  try {

			   String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username
			             + "' AND password = '" + password + "';";// TODO
			   boolean success = false;

			   synchronized (DBLock) {
					success = DBConnector.getStatement().executeQuery(sqlquery).next();
			   }

			   if (success)
					return new User(username);
			   else
					return null;

		  } catch (Exception e) {
			   System.err.println("Error in login");
			   e.printStackTrace();
			   return null;
		  }
	 }

	 public static boolean userExists(String username) {
		  String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username + "';";// TODO
		  try {
			   ResultSet resultSet = DBConnector.getStatement().executeQuery(sqlquery);
			   if (resultSet.next() == true)
					return true;
		  } catch (SQLException e) {
			   System.err.println("Error in verifying user");
			   System.err.println(e.getMessage());
			   e.printStackTrace();

		  }
		  return false;
	 }

	 public static boolean register(User user, String password) {
		  String username;
		  try {
			   username = user.getUsername();

			   String sqlquery = "INSERT INTO " + usersdetailsTable + " values ('" + username + "', '" + password
			             + "');";

			   boolean success = false;
			   try {
					synchronized (DBLock) {
						 if (!userExists(username)) {
							  DBConnector.getStatement().execute(sqlquery);
							  success = true;
							  System.out.println("User added: " + username + ", password saved: " + password);
						 }
					}
					return success;
			   } catch (SQLException e) {
					System.err.println("Database error: add user " + username + " failed.");
					e.printStackTrace();
			   }
		  } catch (Exception e) {
		  }
		  return false;

	 }

	 public static void addBasket(String username, byte[] basket) {
		  String sqlquery = "INSERT INTO " + usersbasketsTable + " values (?, ?);";
		  try {
			   PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sqlquery);
			   pstmt.setString(1, username);
			   pstmt.setBytes(2, basket);
			   synchronized (DBLock) {
					pstmt.execute();
			   }
		  } catch (SQLException e) {
			   System.err.println("Error in inserting new basket");
			   System.err.println(e.getMessage());
			   e.printStackTrace();

		  }
	 }

	 public static void removeBasket(String username, byte[] basket) {
		  String sqlquery = "DELETE from " + usersbasketsTable + " WHERE username = ? AND basket = ?;";
		  try {
			   PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sqlquery);
			   pstmt.setString(1, username);
			   pstmt.setBytes(2, basket);
			   synchronized (DBLock) {
					pstmt.execute();
			   }
		  } catch (SQLException e) {
			   System.err.println("Error in deleting basket: " + basket);
			   System.err.println(e.getMessage());
			   e.printStackTrace();

		  }
	 }

	 static public BasketsContent getBaskets(String username) {
		  String sqlquery = "SELECT basket FROM " + usersbasketsTable + " WHERE username = ?;";
		  List<BasketContent> userBaskets;
		  try {
			   PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sqlquery);
			   pstmt.setString(1, username);
			   synchronized (DBLock) {
					userBaskets = rsToBasketContentList(pstmt.executeQuery());
			   }
			   return new BasketsContent(userBaskets);

		  } catch (SQLException e) {
			   System.err.println("Error in user baskets retrieval");
			   e.printStackTrace();
			   return null;
		  }
	 }

	 private static List<BasketContent> rsToBasketContentList(ResultSet basketsRset) {
		  List<BasketContent> baskets = new ArrayList<>();
		  try {
			   while (basketsRset.next()) {
					try {
						 byte[] basketBytes = basketsRset.getBytes("basket");
						 BasketContent basketContent = new BasketContent(basketBytes);
						 baskets.add(basketContent);
					} catch (Exception e) {
					}
			   }
			   basketsRset.close();
		  } catch (SQLException e) {
			   System.err.println("Error in converting baskets to string");
			   System.err.println(e.getMessage());
			   e.printStackTrace();

		  }
		  return baskets;
	 }

	 public static void removeAllBaskets(String username) {
		  // TODO Auto-generated method stub

	 }

	 public static void insertProductInfo(String id_str, String price_str, double price, String desc,
	           String supplier_dec, String price_perunit_str, String img_src, String category) throws SQLException {
		  List<Object> databaseRow = Arrays.asList(id_str, price_str, price, desc, supplier_dec, price_perunit_str,
		            img_src, category);
		  synchronized (DBLock) {
			   genericSqlQueriesExecutor.insertToTable(productsTable, databaseRow);
		  }
	 }

	 public static Products searchProductsByDescription(String description) throws SQLException {
		  List<List<Object>> rows;
		  synchronized (DBLock) {
//			   rows = genericSqlQueriesExecutor.selectColumnsByValue(productsTable, "description", description, "*");
			   rows = genericSqlQueriesExecutor.selectColumnsByPartialValue(productsTable, "description",
			             " " + description + " ", "*");
		  }
		  return parseRowsToProducts(rows);
	 }

	 private static Products parseRowsToProducts(List<List<Object>> rows) {
		  List<ProductInfo> productInfos = new ArrayList<>();
		  rows.forEach((row) -> {
			   try {
					productInfos.add(new ProductInfo((String) row.get(0), (String) row.get(1), (String) row.get(3),
					          (String) row.get(4), (String) row.get(5), (String) row.get(6), (String) row.get(7)));
			   } catch (Exception e) {
			   }
		  });
		  return new Products(productInfos);
	 }

	 public static Products getProducts() throws SQLException {
		  List<List<Object>> rows;
		  synchronized (DBLock) {
			   rows = genericSqlQueriesExecutor.selectTableContent(productsTable);
		  }
		  return parseRowsToProducts(rows);
	 }

}
