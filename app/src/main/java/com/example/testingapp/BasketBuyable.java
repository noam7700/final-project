package com.example.testingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class BasketBuyable implements Buyable {
    private Basket myBasket;
    private int quantity;

    public BasketBuyable(Basket myBasket, int quantity){
        this.myBasket = myBasket;
        this.quantity = quantity;
    }

    @Override
    public double getDiscount(int times_ordered) {
        return myBasket.getDiscount(times_ordered * (int)quantity);
    }

    @Override
    public void addToBasket() {
        BasketActivity.currentBasket.getBasketBuyables().add(this);
        return;
    }

    @Override
    public String getDesc() {
        return this.myBasket.getName();
    }

    @Override
    public double getPrice() {
        return myBasket.getPrice() * quantity;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    protected BasketBuyable(Parcel in) {
        myBasket = (Basket) in.readValue(Basket.class.getClassLoader());
        quantity = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(myBasket);
        dest.writeInt(quantity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BasketBuyable> CREATOR = new Parcelable.Creator<BasketBuyable>() {
        @Override
        public BasketBuyable createFromParcel(Parcel in) {
            return new BasketBuyable(in);
        }

        @Override
        public BasketBuyable[] newArray(int size) {
            return new BasketBuyable[size];
        }
    };
}
