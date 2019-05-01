package com.example.testingapp;

public class BasketProduct implements Buyable{
    private Product myProduct;
    private double quantity;

    public BasketProduct(Product product, double quantity){
        myProduct = product;
        this.quantity = quantity;
    }

    //getters & setters
    @Override
    public double getQuantity(){
        return quantity;
    }

    @Override
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public Product getMyProduct(){
        return myProduct;
    }

    @Override
    public double getDiscount(){
        //TODO: call discountRequest.js which POST requests from the server
        //maybe call .bat(cmd script) that calls the discountRequest?
        return 0;
    }

    @Override
    public void addToBasket() {
        //TODO: add to current basket (BasketActivity.basketBuyables)
        BasketActivity.currentBasket.getBasketBuyables().add(this);
        return;
    }

    @Override
    public String getDesc() {
        return this.myProduct.getDesc();
    }

    @Override
    public double getPrice() {
        return quantity * myProduct.getPrice();
    }
}
