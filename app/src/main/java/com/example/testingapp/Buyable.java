package com.example.testingapp;

//current basket will hold list of Buyable's
public interface Buyable {

    public double getDiscount(); //calculate overall discount
    public void addToBasket(); //add to current basket that's
    public String getDesc();
    public double getPrice();

    //Buyable should be abstract class so it can hold quantity, but fuck it
    public double getQuantity();
    public void setQuantity(double quantity);
}
