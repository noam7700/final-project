package com.example.testingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Basket implements Buyable {
    private String name;
    private String author;
    private ArrayList<Buyable> basketBuyables;
    private double quantity;

    public Basket(){
        this("Default", "Default", null);
    }
    public Basket(String name, String author){
        this(name, author, null);
    }

    public Basket(String name, String author, ArrayList<Buyable> basketBuyables){
        this.name = name;
        this.author = author;
        if(basketBuyables != null)
            this.basketBuyables = new ArrayList<Buyable>(basketBuyables); //deep copy
        else
            this.basketBuyables = new ArrayList<>();
    }

    @Override
    public double getDiscount() {
        double sum_discounts = 0.0;
        for(int i = 0; i < basketBuyables.size(); i++) {
            sum_discounts += this.basketBuyables.get(i).getDiscount();
        }
        return sum_discounts;
    }

    @Override
    public void addToBasket() {
        BasketActivity.currentBasket.getBasketBuyables().add(this);
    }

    @Override
    public String getDesc() {
        return this.name;
    }

    @Override
    public double getPrice() {
        double sum_allpds = 0;
        for(int i = 0; i < this.basketBuyables.size(); i++){
            sum_allpds += this.basketBuyables.get(i).getPrice();
        }
        return sum_allpds;
    }


    //getters & setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Buyable> getBasketBuyables() {
        return basketBuyables;
    }
    public void setBasketBuyables(ArrayList<Buyable> basketBuyables) {
        this.basketBuyables = basketBuyables;
    }

    @Override
    public double getQuantity(){
        return quantity;
    }

    @Override
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //implement Parcelable
    protected Basket(Parcel in) {
        this.name = in.readString();
        this.author = in.readString();
        this.quantity = in.readDouble();
        if (in.readByte() == 0x01) {
            this.basketBuyables = new ArrayList<>();
            in.readList(this.basketBuyables, Basket.class.getClassLoader());
        } else {
            this.basketBuyables = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeDouble(this.quantity);
        if(this.basketBuyables == null){
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(this.basketBuyables);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Basket> CREATOR = new Parcelable.Creator<Basket>() {
        @Override
        public Basket createFromParcel(Parcel in) {
            return new Basket(in);
        }

        @Override
        public Basket[] newArray(int size) {
            return new Basket[size];
        }
    };
}
