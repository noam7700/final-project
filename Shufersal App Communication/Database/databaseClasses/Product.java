package databaseClasses;
//
//import org.w3c.dom.Text;
//
//public class Product {
//	 private String   id;
//	 private String   name;
//	 private Text     ingredientsText;
//	 private Category categoryRelatedTo;
//	 /**
//	  * the full price of the product, without any discount
//	  */
//	 private double   price;
//
//	 public Product(String id, String name, Text ingredientsText, Category categoryRelatedTo, double price) {
//		  this.id = id;
//		  this.name = name;
//		  this.ingredientsText = ingredientsText;
//		  this.categoryRelatedTo = categoryRelatedTo;
//		  this.price = price;
//	 }
//
//	 public String getId() {
//		  return id;
//	 }
//
//	 public String getName() {
//		  return name;
//	 }
//
//	 public Text getIngredientsText() {
//		  return ingredientsText;
//	 }
//
//	 public Category getCategoryRelatedTo() {
//		  return categoryRelatedTo;
//	 }
//
//	 public double getFullPrice() {
//		  return price;
//	 }
//
//}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Product {
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


    protected Product(BufferedReader in) throws IOException {
        id_str = in.readLine();
        price_str = in.readLine();
        price = Double.parseDouble(in.readLine());
        desc = in.readLine();
        supplier_desc = in.readLine();
        price_perunit_str = in.readLine();
        img_src = in.readLine();

    }

    public void writeToParcel(PrintWriter dest, int flags) {
        dest.write(id_str);
        dest.write(price_str);
        dest.write(String.valueOf(price));
        dest.write(desc);
        dest.write(supplier_desc);
        dest.write(price_perunit_str);
        dest.write(img_src);
    }
}
