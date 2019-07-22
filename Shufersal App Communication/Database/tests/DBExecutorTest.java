package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import communicationObjects.Products;
import execution.DBExecutor;

class DBExecutorTest {
	 @Test
	 void searchProductsTest() throws SQLException {
		  Products products = DBExecutor.searchProductsByDescription("3%");
		  System.out.println();
	 }

}
