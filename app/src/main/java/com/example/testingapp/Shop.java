package com.example.testingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import communicationObjects.ProductInfo;

/*
* line1) #categories
* loop #catagories
*
*/
public class Shop {
    private ArrayList<Category> categories;

    public Shop(BufferedReader bufferreader){
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

    public Shop(List<ProductInfo> serverResult){
        this.categories = new ArrayList<Category>();
        ProductInfo curr_productinfo;
        String curr_catname;
        int curr_cat_index;
        for(int i=0; i<serverResult.size(); i++){
            curr_productinfo = serverResult.get(i);
            curr_catname = curr_productinfo.getCategoryBelongsTo();
            curr_cat_index = this.findCategoryByName(curr_catname);

            if(curr_cat_index == -1){ //first time seeing this category
                //create new category
                this.categories.add(new Category(curr_catname, new ArrayList<Product>()));
                curr_cat_index = this.categories.size() - 1;
            }
            this.categories.get(curr_cat_index).getProducts().add(Product.parseProductInfo(curr_productinfo));


        }

    }

    public int findCategoryByName(String catname){
        for(int i=0; i<categories.size(); i++)
            if(categories.get(i).getCatName().equals(catname))
                return i;

        //not found
        return -1;
    }

    //getters:
    public ArrayList<Category> getCategories() {
        return categories;
    }
}
