package com.example.testingapp;

import android.os.Parcelable;

//current basket will hold list of Buyable's
public interface Buyable extends Parcelable {

    double getDiscount(int times_ordered); //calculate overall discount - depends on qty of parent too
    void addToBasket(); //add to current basket that's
    String getDesc();
    double getPrice();

    //Buyable should be abstract class so it can hold quantity, but fuck it
    int getQuantity();
    void setQuantity(int quantity);

}

//BasketData{
//    List<BuyableData>
//
//    BuyableData
//        {
//            discount
//            dscription
//            price
//            quantity
//            type
//        }
//
//    name;
//    author;
//    quantity;
//}