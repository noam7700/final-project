package com.example.testingapp;

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

    public Basket(String name, String author, ArrayList<BasketProduct> basketBuyables){
        this.name = name;
        this.author = author;
        if(basketBuyables != null)
            this.basketBuyables = new ArrayList<Buyable>(basketBuyables); //deep copy
        else
            this.basketBuyables = new ArrayList<>();
    }

    @Override
    public double getDiscount() {
        return 0;
    }

    @Override
    public void addToBasket() {
        BasketActivity.currentBasket.getBasketBuyables().add(this);
        return;
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

}
