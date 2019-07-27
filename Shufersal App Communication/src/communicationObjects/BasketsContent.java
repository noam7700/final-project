package communicationObjects;

import java.io.Serializable;
import java.util.List;

public class BasketsContent implements Serializable {
	 private static final long serialVersionUID = 1L;

	 private List<BasketContent> baskets;

	 public BasketsContent(List<BasketContent> baskets) throws IllegalArgumentException {
		  if (baskets == null)
			   throw new IllegalArgumentException("DbBasket list cannot be null");

		  this.baskets = baskets;
	 }

	 public List<BasketContent> getBaskets() {
		  return baskets;
	 }

}
