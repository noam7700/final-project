package com.example.testingapp;

import java.util.ArrayList;


public class User {
    private String username;
    private String password;
    private ArrayList<Basket> mBaskets;

    public User(String username, String password){
        //TODO: implement User's c'tor with amit
        this.username = username;
        this.password = password;
    }

    public void addBasket(Basket basket){
        mBaskets.add(basket);
        //TODO: add the basket in the server
    }
    public void deleteBasket_byIndex(int position){
        mBaskets.remove(position);
        //TODO: remove the basket in the server
    }
    public int findBasketIndex_byName(String name){
        for(int i = 0; i < mBaskets.size(); i++)
            if(mBaskets.get(i).getName() == name)
                return i;
        return -1;
    }

    public ArrayList<Basket> getmBaskets(){
        return mBaskets;
    }
    public String getUsername(){
        return username;
    }
}
