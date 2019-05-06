package DatabaseComm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class DBExecutor {
	static final String usersdetailsTable = "usersDetails";
	static final String usersbasketsTable = "usersBaskets";
	static ReentrantLock DBLock = new ReentrantLock();

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

	public static boolean userExists(String username, String password) {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username
				+ "' AND password = '" + password + "';";
		boolean success = false;
		try {
			synchronized (DBLock) {
				success = DBConnector.connStmt.executeQuery(sqlquery).next();
			}
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public static boolean userExists(String username) {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username + "';";
		try {
			if (DBConnector.connStmt.executeQuery(sqlquery).next() == true)
				return true;
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return false;
	}

	public static boolean addUser(String username, String password) {
		String sqlquery = "INSERT INTO " + usersdetailsTable + " values ('" + username + "', '" + password + "');";
		boolean success = false;
		try {
			synchronized (DBLock) {
				if (!userExists(username))
					success = DBConnector.connStmt.execute(sqlquery);
			}
			System.out.println("User added: " + username + ", password saved: " + password);
		} catch (SQLException e) {
			System.err.println("Error in adding user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public static void addBasket(String username, byte[] basket) {
		String sqlquery = "INSERT INTO " + usersbasketsTable + " values (?, ?);";
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			pstmt.setBytes(2, basket);
			synchronized (DBLock) {
				pstmt.execute();
			}
		} catch (SQLException e) {
			System.err.println("Error in inserting new basket");
			System.err.println(e.getMessage());
		}
	}

	public static void removeBasket(String username, byte[] basket) {
		String sqlquery = "DELETE from " + usersbasketsTable + " WHERE username = ? AND basket = ?;";
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			pstmt.setBytes(2, basket);
			synchronized (DBLock) {
				pstmt.execute();
			}
		} catch (SQLException e) {
			System.err.println("Error in deleting basket: " + basket);
			System.err.println(e.getMessage());
		}
	}

	static public Object[] getBaskets(String username) {
		String sqlquery = "SELECT basket FROM " + usersbasketsTable + " WHERE username = ?;";
		Object[] userBaskets = null;
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			synchronized (DBLock) {
				userBaskets = bsktArray(pstmt.executeQuery());
			}
		} catch (SQLException e) {
			System.err.println("Error in inserting new basket");
			System.err.println(e.getMessage());
		}
		return userBaskets;
	}

	private static Object[] bsktArray(ResultSet basketsRset) {
		ArrayList<Object> baskets = new ArrayList<Object>();
		try {
			while (basketsRset.next()) {
				byte[] basketBytes = basketsRset.getBytes("basket");
				Object bskt = getObject(basketBytes);
				baskets.add(bskt);
			}
			basketsRset.close();
		} catch (SQLException e) {
			System.err.println("Error in converting baskets to string");
			System.err.println(e.getMessage());
		}
		Object[] basketsArr = baskets.toArray();
		return basketsArr;
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

	public static void removeAllBaskets(String username) {
		// TODO Auto-generated method stub

	}

}
