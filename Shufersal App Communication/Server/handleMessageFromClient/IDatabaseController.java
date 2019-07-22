package handleMessageFromClient;

import java.util.Set;

import org.w3c.dom.Text;

import databaseClasses.Basket;
import databaseClasses.ShoppingListProduct;

public interface IDatabaseController {
	 
	 Set<Basket> getUserBaskets(String username);
	 Set<ShoppingListProduct> getProductPurchasesByInputText(Text text);
	 void removeSavedBasket(String username, int basketID);
	 void removeAllSavedBaskets(String username);
	 int saveUserBasket(String username/*, all basket components*/);
	 void removeProductFromUserBasket(String username, int basketID, int productID);
	 void removeUserBasket(String username, int basketID);
	 
	 
}
