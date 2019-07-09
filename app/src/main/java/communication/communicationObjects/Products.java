package communication.communicationObjects;

import java.io.Serializable;
import java.util.List;

import communication.databaseClasses.DbProduct;

public class Products implements Serializable {
	 private static final long serialVersionUID = 1L;

	 private List<DbProduct> DbProducts;

	 public Products(List<DbProduct> DbProducts) throws IllegalArgumentException {
		  if (DbProducts == null)
			   throw new IllegalArgumentException("DbDbProduct list cannot be null");
		  
		  this.DbProducts = DbProducts;
	 }

	 public List<DbProduct> getDbProducts() {
		  return DbProducts;
	 }

}
