package communication.communicationObjects;

import java.io.Serializable;
import java.util.List;

import communication.databaseClasses.ProductInfo;

public class Products implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ProductInfo> DbProducts;

    public Products(List<ProductInfo> DbProducts) throws IllegalArgumentException {
        if (DbProducts == null)
            throw new IllegalArgumentException("DbProduct list cannot be null");

        this.DbProducts = DbProducts;
    }

    public List<ProductInfo> getProductsInfo() {
        return DbProducts;
    }

}
