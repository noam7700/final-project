package databaseProductsUpdate;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import databaseClasses.Category;
import databaseClasses.Product;
import execution.DBExecutor;

public class DatabaseProductsUpdater {
	 public static void updateDatabaseProducts(File productsFile) throws IOException, SQLException {
		  List<Category> categories = ProductsFileParser.parseProductsFile(productsFile);
		  cleanStoredProductsContent();
		  insertCategories(categories);

	 }

	 private static void cleanStoredProductsContent() throws SQLException {
		  DBExecutor.cleanProductsTable();
	 }

	 private static void insertCategories(List<Category> categories) throws SQLException {
		  for (Category category : categories) {
			   insertCategory(category);
		  }
	 }

	 private static void insertCategory(Category category) throws SQLException {
		  String categoryBelongsTo = category.getCatName();
		  for (Product product : category.getProducts()) {
			   insertProduct(product, categoryBelongsTo);
		  }
	 }

	 private static void insertProduct(Product product, String categoryBelongsTo) throws SQLException {
		  String id_str = product.getId_str();
		  String price_str = product.getPrice_str();
		  double price = product.getPrice();
		  String desc = product.getDesc();
		  String supplier_dec = product.getSupplier_desc();
		  String price_perunit_str = product.getPrice_perunit_str();
		  String img_src = product.getImg_src();
		  DBExecutor.insertProductInfo(id_str, price_str, price, desc, supplier_dec, price_perunit_str, img_src,
		            categoryBelongsTo);
	 }

}
