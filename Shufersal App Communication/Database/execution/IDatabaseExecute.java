package execution;

import java.util.Set;

import org.w3c.dom.Text;

import databaseClasses.Basket;
import databaseClasses.CategoryEnum;
import databaseClasses.Product;
import databaseClasses.ShoppingListProduct;

public interface IDatabaseExecute {
	 
	 /**
	  * @param usernameRelatedTo
	  * 			username of the user that the basket is belongs to
	  * @param basketName
	  * 			the basket name
	  * @param basketProducts
	  * 			the products that are contained in the basket
	  * 
	  * @return the ID of the basket that stored in the database. the ID principle is to allow us to access the object in the database.
	  * 	
	  */
	 int addBasket(String usernameRelatedTo, String basketName, Set<ShoppingListProduct> basketProducts);
	 /**
	  * @param name
	  * 			the name of the product
	  * @param ingrediantsText
	  * 			the full text of the product's ingredients. can be empty if no such is available
	  * @param categoryRelatedTo
	  * 			the category that the products is belongs to in the Shufersal web.
	  * @param price
	  * 			the full price (including discount) of the product
	  * @param priceDiscount
	  * 			the discount for the product's price (can be 0)
	  * 
	  * @return the ID of the product that stored in the database
	  */
	 int addProduct(String name, Text ingrediantsText, CategoryEnum categoryRelatedTo, double price, double priceDiscount);
	 
	 /**
	  * @param usernameRelatedTo
	  * 			username the the basket belongs to
	  * @param basketID
	  * 			the ID of the basket
	  * 
	  * @return	the basket corresponds to <code>basketID</code> supplied
	  */	 
	 Basket getBasket(String usernameRelatedTo, int basketID);	 
	 void deleteBasket(int basketID);
	 
	 /**
	  * @param productID
	  * 			the ID of the product
	  * 
	  * @return	the produt corresponds to <code>productID</code> supplied
	  */
	 Product getProduct(int productID);	 
	 void deleteProduct(int productID);
	 
	 /**
	  * @param usernameRelatedTo
	  * 			the username of the user that the baskets belong to
	  * 
	  * @return	all the user baskets stored in the database
	  */
	 Set<Basket> getUserBaskets(String usernameRelatedTo);
	 void deleteUserBaskets(String usernameRelatedTo);
	 
	 /**
	  * @param productName
	  * 			the partial or full name of the product to search by
	  * 
	  * @return all the products that correspond to the search obligation
	  */
	 Set<Product> searchProductsByName(String productName);

}
