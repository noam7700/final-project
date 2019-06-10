package com.example.testingapp;

import android.os.Parcel;

import java.text.DecimalFormat;

//amit's imports


public class BasketProduct implements Buyable{
    private Product myProduct;
    private double quantity;

    public BasketProduct(Product product, double quantity){
        myProduct = product;
        this.quantity = quantity;
    }

    //getters & setters
    @Override
    public double getQuantity(){
        return quantity;
    }

    @Override
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public Product getMyProduct(){
        return myProduct;
    }

    @Override
    public double getDiscount(){
        //TODO: check with amit the server
        //amit's "parameters"
        String prodID = myProduct.getId_str(); //for example: "divProduct_112552446"
        prodID = prodID.substring(prodID.lastIndexOf("_") + 1); //get the number itself
        String qty = new DecimalFormat("##.##").format(quantity); //take only two digits after the dot

        /*String paramUM = "";
        String discount = "";
        try {
            String urlParameters  = "AjaxCallAction=AddProductToBasket&paramProductID="+prodID+"&paramQuantity="+qty+"&paramRemarks=&paramUM="+paramUM;
            URL url = new URL("https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx");

            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            conn.setRequestProperty( "charset", "UTF-8");
            conn.setRequestProperty("Cookie", "SMC=UID=ef6d67a7-bb61-470c-878f-5ec40050c744; expires=No;"); // maybe update the cookie once in a while in a file (while updating the data), because it expires after few days
            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            wr.writeBytes( urlParameters );
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String HtmlObject="",line; while((line = in.readLine()) != null) HtmlObject += line; // casting the response to string
            discount = parseDiscFromHtml(HtmlObject);//HtmlObject;//
            in.close();
        }
        catch(Exception e) {e.printStackTrace();}

        return Double.parseDouble(discount);*/
        return 0.0;
    }

    //help function for getDiscount
    public static String parseDiscFromHtml(String htmlString){
        String object = "discountPerUnit='"; // for some reason this field contains the total discount value.
        String price = "";
        if(htmlString.contains(object)){
            int index = htmlString.indexOf(object) + object.length();
            price = htmlString.substring(index, htmlString.indexOf('\'', index)).replaceAll(" ","");
        }
        return price;
    }

    @Override
    public void addToBasket() {
        //TODO: add to current basket (BasketActivity.basketBuyables)
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
        quantity = in.readDouble();
        myProduct = (Product) in.readValue(Product.class.getClassLoader()); //i hope it works
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeValue(myProduct);
    }
}
