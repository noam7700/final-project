package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import databaseProductsUpdate.DatabaseProductsUpdater;

class DataUpdateTest {

	 @Test
	 void test() throws IOException, SQLException {
		  DatabaseProductsUpdater.updateDatabaseProducts(new File("ProductsTextData.txt"));
	 }

}
