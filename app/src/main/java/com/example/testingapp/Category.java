package com.example.testingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/*cat stands for category*/
public class Category implements Parcelable {
    private String catName;
    private ArrayList<Product> products;
    Category(BufferedReader bufferreader) {
        //TODO: handles reading from ProductsTextData
        //reads all products' data and creates them
        /*
        * line 2.1) cat's name
        * line 2.2) #pds in cat
        * loop #pds in cat:
        *   line 3.1) pd's id
        *   line 3.2) pd's price
        *   line 3.3) pd's desc
        *   line 3.4) pd's supplier's desc
        *   line 3.5) pd's price per unit
        *   line 3.6) pd's image's url source
        */
        try {
            this.catName = bufferreader.readLine(); //line 2.1
            int numOfPds = Integer.parseInt(bufferreader.readLine()); //line 2.2
            products = new ArrayList<Product>();

            //insert cat's pds
            Product pd;
            String id_str, price_str, desc, supplier_desc, price_perunit_str, img_src;
            for(int i=0; i<numOfPds; i++){
                id_str = bufferreader.readLine(); //line 3.1
                price_str = bufferreader.readLine(); //line 3.2
                desc = bufferreader.readLine(); //line 3.3
                supplier_desc = bufferreader.readLine(); //line 3.4
                price_perunit_str = bufferreader.readLine(); //line 3.5
                img_src = bufferreader.readLine(); //line 3.6
                pd = new Product(id_str, price_str, desc, supplier_desc, price_perunit_str, img_src);
                products.add(pd); //push_back
            }


        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Category(String catName, ArrayList<Product> products){
        this.catName = catName;
        this.products = products; //low copy
    }

    //getters:
    public String getCatName() {
        return catName;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }


    //implement Parcelable
    protected Category(Parcel in) {
        catName = in.readString();
        if (in.readByte() == 0x01) {
            products = new ArrayList<Product>();
            in.readList(products, Product.class.getClassLoader());
        } else {
            products = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(catName);
        if (products == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(products);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
