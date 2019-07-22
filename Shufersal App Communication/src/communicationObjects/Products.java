package communicationObjects;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {
	 private static final long serialVersionUID = 1L;

	 private List<ProductInfo> productsInfo;

	 public Products(List<ProductInfo> productsInfo) throws IllegalArgumentException {
		  if (productsInfo == null)
			   throw new IllegalArgumentException("products list cannot be null");

		  this.productsInfo = productsInfo;
	 }

	 public List<ProductInfo> getProducts() {
		  return productsInfo;
	 }

}
