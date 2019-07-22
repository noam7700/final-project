package com.example.testingapp;

/*pd stands for product*/

import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import communicationObjects.ProductInfo;

public class Product implements Parcelable{
    private String id_str; //for example: "divProduct_112552446"
    private String price_str; //presented in the app (with NIS symbol)
    private double price; //the price itself
    private String desc; //pd's description
    private String supplier_desc;
    //just for user's usage. if the pd is per unit, it's already in the price attr
    private String price_perunit_str;
    private String img_src;

    Product(String id_str, String price_str, String desc, String supplier_desc, String price_perunit_str, String img_src) {
        this.id_str = id_str;
        this.price_str = price_str;
        //parts[0] is the price, parts[1] is NIS symbol
        String[] parts = price_str.split(" ");

        //supports commas in the number. like "1,399.00"
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        Number number = null;
        try {
            number = format.parse(parts[0]);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        this.price = number.doubleValue();
        this.desc = desc;
        this.supplier_desc = supplier_desc;
        this.price_perunit_str = price_perunit_str;
        this.img_src = img_src;
    }

    //getters:
    public String getId_str() {
        return id_str;
    }
    public String getPrice_str() {
        return price_str;
    }
    public double getPrice() {
        return price;
    }
    public String getDesc() {
        return desc;
    }
    public String getSupplier_desc() {
        return supplier_desc;
    }
    public String getPrice_perunit_str() {
        return price_perunit_str;
    }
    public String getImg_src() { return img_src; }


    protected Product(Parcel in) {
        id_str = in.readString();
        price_str = in.readString();
        price = in.readDouble();
        desc = in.readString();
        supplier_desc = in.readString();
        price_perunit_str = in.readString();
        img_src = in.readString();

    }

    public static Product parseProductInfo(ProductInfo pdinfo){
        String id_str, price_str, desc, supplier_desc, price_perunit_str, img_src;
        id_str = pdinfo.getId_str();
        price_str = pdinfo.getPrice_str();
        desc = pdinfo.getDesc();
        supplier_desc = pdinfo.getSupplier_desc();
        price_perunit_str = pdinfo.getPrice_perunit_str();
        img_src = pdinfo.getImg_src();

        Product retPd = new Product(id_str, price_str, desc, supplier_desc, price_perunit_str, img_src);

        return retPd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_str);
        dest.writeString(price_str);
        dest.writeDouble(price);
        dest.writeString(desc);
        dest.writeString(supplier_desc);
        dest.writeString(price_perunit_str);
        dest.writeString(img_src);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
