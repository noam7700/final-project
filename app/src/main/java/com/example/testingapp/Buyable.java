package com.example.testingapp;

//current basket will hold list of Buyable's
public interface Buyable {

    public double getDiscount(double quantity); //calculate overall discount
    public void addToBasket(); //add to current basket that's
    public String getDesc();
    public double getPrice();
}
