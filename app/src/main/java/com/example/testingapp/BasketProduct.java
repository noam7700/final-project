package com.example.testingapp;

public class BasketProduct {
    private Product myProduct;
    private double quantity;

    public BasketProduct(Product product, double quantity){
        myProduct = product;
        this.quantity = quantity;
    }

    //getters & setters
    public double getQuantity(){
        return quantity;
    }
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
    public Product getMyProduct(){
        return myProduct;
    }
}
