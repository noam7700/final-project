package com.example.testingapp;

import android.os.Parcelable;

//current basket will hold list of Buyable's
public interface Buyable extends Parcelable {

    double getDiscount(); //calculate overall discount
    void addToBasket(); //add to current basket that's
    String getDesc();
    double getPrice();

    //Buyable should be abstract class so it can hold quantity, but fuck it
    double getQuantity();
    void setQuantity(double quantity);

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