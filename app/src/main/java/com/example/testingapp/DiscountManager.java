package com.example.testingapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class DiscountManager {

    public static double getDiscount(String prodID, String qty){
        DiscountThread discountThread = new DiscountThread(prodID, qty);
        Thread thread = new Thread(discountThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(discountThread.getResponse_discount().charAt(0) == '-')
            return -Double.parseDouble(discountThread.getResponse_discount()); //"-2.80" to 2.8
        else
            return Double.parseDouble(discountThread.getResponse_discount()); //only when discount=0
    }
}

class DiscountThread extends Thread {
    private String prodID;
    private String qty;
    private String cookie;
    private String response_discount;

    public DiscountThread(String prodID, String qty){
        this.prodID = prodID;
        this.qty = qty;
        this.cookie = ""; //will be set in "run()"
        this.response_discount = ""; //will be set in "run()"
    }

    public String getResponse_discount() {
        return response_discount;
    }

    @Override
    public void run() {
        this.cookie = getSMCCookie(); //used in "getDiscount()"
        this.response_discount = getDiscount();
    }

    public static String parseDiscFromHtml(String htmlString){
        String object = "discountPerUnit='"; // for some reason this field contains the total discount value.
        String price = "";
        if(htmlString.contains(object)){
            int index = htmlString.indexOf(object) + object.length();
            price = htmlString.substring(index, htmlString.indexOf('\'', index)).replaceAll(" ","");
        }
        return price;
    }

    public String getDiscount(){
        String paramUM = "";
        String discount = "";
        try {
            String urlParameters  = "AjaxCallAction=AddProductToBasket&paramProductID="+prodID+"&paramQuantity="+qty+"&paramRemarks=&paramUM="+paramUM;
            URL url = new URL("https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx");

            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            conn.setRequestProperty( "charset", "UTF-8");
            //conn.setRequestProperty("Cookie", "SMC=; expires=Tue, 11-Aug-2020 11:09:24 GMT; path=/; HttpOnly"); // maybe update the cookie once in a while in a file (while updating the data), because it expires after few days
            conn.setRequestProperty("Cookie", cookie); //updated cookie (got it from getSMCCookie function)
            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            wr.writeBytes( urlParameters );
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String HtmlObject="",line; while((line = in.readLine()) != null) HtmlObject += line; // casting the response to string
            System.out.println(HtmlObject); //debug
            discount = parseDiscFromHtml(HtmlObject);//HtmlObject;//
            in.close();
        }
        catch(Exception e) {e.printStackTrace();}

        return discount;
    }

    public static String getSMCCookie(){
        String ret = "";

        try{

            URL url = new URL("https://www.shufersal.co.il/pages/catalog.aspx");
            URLConnection conn = url.openConnection();

            Map<String, List<String>> headerFields = conn.getHeaderFields();

            Set<String> headerFieldsSet = headerFields.keySet();
            Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

            while (hearerFieldsIter.hasNext()) {

                String headerFieldKey = hearerFieldsIter.next();

                if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {

                    List<String> headerFieldValue = headerFields.get(headerFieldKey);

                    for (String headerValue : headerFieldValue) {

                        System.out.println("Cookie Found...");

                        if(headerValue.contains("SMC"))
                            ret = headerValue; //wanted cookie

                        String[] fields = headerValue.split(";\\s*");

                        String cookieValue = fields[0];
                        String expires = null;
                        String path = null;
                        String domain = null;
                        boolean secure = false;

                        // Parse each field
                        for (int j = 1; j < fields.length; j++) {
                            if ("secure".equalsIgnoreCase(fields[j])) {
                                secure = true;
                            }
                            else if (fields[j].indexOf('=') > 0) {
                                String[] f = fields[j].split("=");
                                if ("expires".equalsIgnoreCase(f[0])) {
                                    expires = f[1];
                                }
                                else if ("domain".equalsIgnoreCase(f[0])) {
                                    domain = f[1];
                                }
                                else if ("path".equalsIgnoreCase(f[0])) {
                                    path = f[1];
                                }
                            }

                        }

                        System.out.println("cookieValue:" + cookieValue);
                        System.out.println("expires:" + expires);
                        System.out.println("path:" + path);
                        System.out.println("domain:" + domain);
                        System.out.println("secure:" + secure);
                        System.out.println("Noam:" + headerValue);

                        System.out.println("*****************************************");


                    }

                }

            }


        }
        catch(Exception e) {e.printStackTrace();}

        return ret;
    }

}
