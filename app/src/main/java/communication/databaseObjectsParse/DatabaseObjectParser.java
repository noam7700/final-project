package communication.databaseObjectsParse;

import com.example.testingapp.Product;

import java.util.ArrayList;
import java.util.List;

import communicationObjects.Products;
import communicationObjects.ProductInfo;

public class DatabaseObjectParser {

//    public Product parseDbProduct(ProductInfo dbProduct) {
//        return new Product(dbProduct.getId_str(), dbProduct.getPrice_str(), dbProduct.getDesc(), dbProduct.getSupplier_desc(), dbProduct.getPrice_perunit_str(), dbProduct.getImg_src());
//    }

    public List<ProductInfo> parseDbProducts(Products productsRetrieved) {
//        List<Product> productsList = new ArrayList<>();
//        for (ProductInfo dbProduct : productsRetrieved.getProductsInfo()) {
//            productsList.add(parseDbProduct(dbProduct));
//        }
//        return productsList;
        return productsRetrieved.getProducts();
    }
}
