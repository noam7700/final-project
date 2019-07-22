package databaseClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/*cat stands for category*/
public class Category {
	 private String             catName;
	 private ArrayList<Product> products;

	 public Category(BufferedReader bufferreader) throws IOException {
		  // TODO: handles reading from ProductsTextData
		  // reads all products' data and creates them
		  /*
		   * line 2.1) cat's name line 2.2) #pds in cat loop #pds in cat: line 3.1) pd's
		   * id line 3.2) pd's price line 3.3) pd's desc line 3.4) pd's supplier's desc
		   * line 3.5) pd's price per unit line 3.6) pd's image's url source
		   */
			   this.catName = bufferreader.readLine(); // line 2.1
			   int numOfPds = Integer.parseInt(bufferreader.readLine()); // line 2.2
			   products = new ArrayList<Product>();

			   // insert cat's pds
			   Product pd;
			   String id_str, price_str, desc, supplier_desc, price_perunit_str, img_src;
			   for (int i = 0; i < numOfPds; i++) {
					id_str = bufferreader.readLine(); // line 3.1
					price_str = bufferreader.readLine(); // line 3.2
					desc = bufferreader.readLine(); // line 3.3
					supplier_desc = bufferreader.readLine(); // line 3.4
					price_perunit_str = bufferreader.readLine(); // line 3.5
					img_src = bufferreader.readLine(); // line 3.6
					pd = new Product(id_str, price_str, desc, supplier_desc, price_perunit_str, img_src);
					products.add(pd); // push_back
			   }
	 }

	 // getters:
	 public String getCatName() {
		  return catName;
	 }

	 public ArrayList<Product> getProducts() {
		  return products;
	 }

}
