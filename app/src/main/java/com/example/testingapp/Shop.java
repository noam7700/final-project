package com.example.testingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/*
* line1) #categories
* loop #catagories
*
*/
public class Shop {
    private ArrayList<Category> categories;

    Shop(BufferedReader bufferreader){
        try{
            categories = new ArrayList<Category>();
            int numOfCats = Integer.parseInt(bufferreader.readLine());
            Category cat;
            for (int i = 0; i < numOfCats - 1; i++){ //(numOfCats - 1) <- doesn't include "specials"
                cat = new Category(bufferreader); //this also updates the reader to the next cat
                categories.add(cat);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //getters:
    public ArrayList<Category> getCategories() {
        return categories;
    }
}
