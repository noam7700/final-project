package databaseClasses;

import java.sql.Date;
import java.util.Set;

public class Basket {
	 private int                      id;
	 private String                   basketOwnerUsername;
	 private String                   name;
	 private Date                     creationDate;
	 private Set<ShoppingListProduct> products;

	 public Basket(int id, String basketOwnerUsername, String name, Date creationDate,
	           Set<ShoppingListProduct> products) {
		  this.id = id;
		  this.basketOwnerUsername = basketOwnerUsername;
		  this.name = name;
		  this.creationDate = creationDate;
		  this.products = products;
	 }

	 public int getId() {
		  return id;
	 }

	 public String getBasketOwnerUsername() {
		  return basketOwnerUsername;
	 }

	 public String getName() {
		  return name;
	 }

	 public Date getCreationDate() {
		  return creationDate;
	 }

	 public Set<ShoppingListProduct> getProducts() {
		  return products;
	 }
}
