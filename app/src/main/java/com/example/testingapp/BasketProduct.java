package com.example.testingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import communication.clientDataAccess.ClientDataAccessObject;

//amit's imports


public class BasketProduct implements Buyable, Serializable {

    private Product myProduct;
    private int quantity;

    public BasketProduct(Product product, int quantity) {
        myProduct = product;
        this.quantity = quantity;
    }

    //getters & setters
    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getMyProduct() {
        return myProduct;
    }

    @Override
    public double getDiscount(int times_ordered) {
        //TODO: check with amit the server - LATER
        //amit's "parameters"
        String prodID = myProduct.getId_str(); //for example: "divProduct_112552446"
        prodID = prodID.substring(prodID.lastIndexOf("_") + 1); //get the number itself
        int qty = quantity * times_ordered; //take only two digits after the dot
        try {
            return ClientDataAccessObject.getProductDiscount(prodID, qty);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    //help function for getDiscount
    public static String parseDiscFromHtml(String htmlString) {
        String object = "discountPerUnit='"; // for some reason this field contains the total discount value.
        String price = "";
        if (htmlString.contains(object)) {
            int index = htmlString.indexOf(object) + object.length();
            price = htmlString.substring(index, htmlString.indexOf('\'', index)).replaceAll(" ", "");
        }
        return price;
    }

    @Override
    public void addToBasket() {
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

    //implement Parcelable
    protected BasketProduct(Parcel in) {
        quantity = in.readInt();
        myProduct = (Product) in.readValue(Product.class.getClassLoader()); //i hope it works
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeValue(myProduct);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BasketProduct> CREATOR = new Parcelable.Creator<BasketProduct>() {
        @Override
        public BasketProduct createFromParcel(Parcel in) {
            return new BasketProduct(in);
        }

        @Override
        public BasketProduct[] newArray(int size) {
            return new BasketProduct[size];
        }
    };

}
