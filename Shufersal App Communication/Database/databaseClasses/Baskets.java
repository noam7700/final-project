package databaseClasses;

import java.io.Serializable;
import java.util.List;

public class Baskets implements Serializable {
	 private static final long serialVersionUID = 1L;

	 private List<Basket> baskets;

	 public Baskets(List<Basket> baskets) throws IllegalArgumentException {
		  if (baskets == null)
			   throw new IllegalArgumentException("Basket list cannot be null");
		  
		  this.baskets = baskets;
	 }

	 public List<Basket> getBaskets() {
		  return baskets;
	 }

}
