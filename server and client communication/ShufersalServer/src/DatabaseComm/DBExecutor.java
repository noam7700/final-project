package DatabaseComm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBExecutor {
	static final String usersdetailsTable = "usersDetails";
	static final String usersbasketsTable = "usersBaskets";
	
	public static void cleanTables() {
		try {
		DBConnector.getStatement().execute("truncate table " + usersdetailsTable + ";");
		DBConnector.getStatement().execute("truncate table " + usersbasketsTable + ";");
		} catch (SQLException e) {
			System.err.println("Error in erasing tables data");
			System.err.println(e.getMessage());
		}
	}
	public static boolean verifyUser(String username, String password) {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username
				+ "' AND password = '" + password + "';";
		try {
			if (DBConnector.connStmt.executeQuery(sqlquery).next() == true)
				return true;
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return false;
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
		try {
			if (!userExists(username))
				return DBConnector.connStmt.execute(sqlquery);
		} catch (SQLException e) {
			System.err.println("Error in adding user");
			System.err.println(e.getMessage());
		}
		return false;
	}

	public static void addBasket(String username, String basket) {
		String sqlquery = "INSERT INTO " + usersbasketsTable + " values ('" + username + "', '" + basket + "');";
		try {
			DBConnector.connStmt.execute(sqlquery);
		} catch (SQLException e) {
			System.err.println("Error in inserting new basket");
			System.err.println(e.getMessage());
		}
	}

	static public ResultSet retreiveBaskets(String username) {
		String sqlquery = "SELECT basket FROM " + usersbasketsTable + " WHERE username = '" + username + "';";
		ResultSet userBaskets = null;
		try {
			userBaskets = DBConnector.connStmt.executeQuery(sqlquery);
		} catch (SQLException e) {
			System.err.println("Error in inserting new basket");
			System.err.println(e.getMessage());
		}
		return userBaskets;
	}
}
