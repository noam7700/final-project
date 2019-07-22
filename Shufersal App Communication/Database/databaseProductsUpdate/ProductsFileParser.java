package databaseProductsUpdate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import databaseClasses.Category;

public class ProductsFileParser {
	 public static List<Category> parseProductsFile(File productdsFile) throws IOException {
		  InputStream productsTextData_is = new FileInputStream(productdsFile);
		  BufferedReader bufferreader = new BufferedReader(new InputStreamReader(productsTextData_is, "UTF-8"));
		  List<Category> categories = new ArrayList<Category>();
		  int numOfCats = Integer.parseInt(bufferreader.readLine());
		  Category cat;
		  for (int i = 0; i < numOfCats - 1; i++) { // (numOfCats - 1) <- doesn't include "specials"
			   cat = new Category(bufferreader); // this also updates the reader to the next cat
			   categories.add(cat);
		  }
		  return categories;
	 }

}
