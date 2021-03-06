package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author amit
 * 
 *         This class rule is to manage all the things that are related to the
 *         database connectivity. it supplies all the details and references
 *         objects that correspond to the connection to mysql database server.
 */
public class DBConnector {
	 private static final String host     = "remotemysql.com";
	 private static final String DBName   = "ZUXezOHIFk";
	 private static final String username = "ZUXezOHIFk";
	 private static final String password = "KRbDbupYZL";
	 private static Connection   conn     = null;
	 private static Statement    connStmt = null;

	 public static Connection connect(String host, String DBName, String username, String password) {
		  // Allocate a database 'Connection' object
		  Connection conn = null;
		  try {
			   conn = DriverManager.getConnection(
			             "jdbc:mysql://" + host + ":3306/" + DBName
			                       + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
			             username, password); // For MySQL
		  } catch (SQLException e) {
			   System.err.println("unable to connect to DB");
			   System.err.println(e.getMessage());
			   System.exit(1);
		  }
		  return conn;
	 }

	 public static Statement getStatement() {
		  return connStmt;
	 }

	 public static Connection connect() {
		  if (conn == null) {
			   try {
					conn = DriverManager.getConnection(
					          "jdbc:mysql://" + host + ":3306/" + DBName
					                    + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					          username, password);
					connStmt = conn.createStatement();

			   } catch (SQLException e) {
					System.err.println("unable to connect to DB");
					System.err.println(e.getMessage());
					e.printStackTrace();
			   }
		  }
		  return conn;
	 }

	 public static void closeConnection() {
		  try {
			   if (!conn.isClosed()) {
					conn.close();
			   }
			   if (!connStmt.isClosed()) {
					connStmt.close();
			   }
		  } catch (SQLException e) {
			   System.err.println("Error in closing connection");
			   System.err.println(e.getMessage());
			   e.printStackTrace();

		  }
	 }

	 public static void main(String[] args) {
		  try {
			   Connection conn = connect();
			   Statement stmt = conn.createStatement();
			   String strSelect = "select username, password from usersDetails";
			   System.out.println("The SQL query is: " + strSelect); // Echo For debugging
			   System.out.println();

			   ResultSet rset = stmt.executeQuery(strSelect);
			   System.out.println("The details selected are:");
			   int rowCount = 0;
			   while (rset.next()) { // Move the cursor to the next row, return false if no more row
					String username = rset.getString("username");
					String password = rset.getString("password");
					System.out.println(username + ", " + password);
					++rowCount;
			   }
			   System.out.println("Total number of users = " + rowCount);

			   stmt.close();
			   rset.close();
			   conn.close();
		  } catch (Exception ex) {
			   System.err.println("unable to read from DB");
			   System.err.println(ex.getMessage());
			   ex.printStackTrace();

		  }
	 }

	 public static Connection getConnection() {
		  return conn;
	 }
}
