package communication.databaseObjectsParse;

import com.example.testingapp.Basket;
import com.example.testingapp.Product;

import java.util.ArrayList;
import java.util.List;

import communication.communicationObjects.Products;
import communication.databaseClasses.DbProduct;

public class DatabaseObjectParser {

    public Product parseDbProduct(DbProduct dbProduct) {
        return new Product(dbProduct.getId_str(), dbProduct.getPrice_str(), dbProduct.getDesc(), dbProduct.getSupplier_desc(), dbProduct.getPrice_perunit_str(), dbProduct.getImg_src());
    }

    public List<Product> parseDbProducts(Products productsRetrieved) {
        List<Product> productsList = new ArrayList<>();
        for (DbProduct dbProduct : productsRetrieved.getDbProducts()) {
            productsList.add(parseDbProduct(dbProduct));
        }
        return productsList;
    }
}
