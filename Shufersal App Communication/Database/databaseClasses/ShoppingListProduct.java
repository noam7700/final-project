package databaseClasses;

/**
 * @author amit
 * 
 *         This class stands for a single product component in the shopping
 *         list.
 *
 */
public class ShoppingListProduct {
	 private Product product;
	 private int     amount;
	 private double  totalPrice;
	 private double  totalDiscount;

	 public ShoppingListProduct(Product product, int amount, double totalPrice, double totalDiscount) {
		  this.product = product;
		  this.amount = amount;
		  this.totalPrice = totalPrice;
		  this.totalDiscount = totalDiscount;
	 }

	 public Product getProduct() {
		  return product;
	 }

	 public int getAmount() {
		  return amount;
	 }

	 public double getTotalPrice() {
		  return totalPrice;
	 }

	 public double getTotalDiscount() {
		  return totalDiscount;
	 }
}
