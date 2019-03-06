/*
skeleton of the discount function. left to run&check it
*/
import java.lang.*;
import java.util.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.OutputStream;
import java.util.Scanner;
import java.lang.Objecj.*;

CookieHandler fg;
public class discountFunction{
    public static String getPriceAfterDiscount(String prodID, String qty){ /*returns a string of price after discount. note: id and qty are sent as strings.*/
        try {
            URL url = new URL("https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx");

            String remarks = "";
            String unitofmeasure = "";
            //String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
            String AjaxCallAction = "AddProductToBasket";
            String paramProductID = prodID;
            String paramQuantity = qty;
            String paramRemarks = remarks;
            String paramUM = unitofmeasure;
            // ...

            String query = String.format("AjaxCallAction=%s&paramProductID=%s&paramQuantity=%s&paramRemarks=%s&paramUM=%s",
                    URLEncoder.encode(AjaxCallAction, "UTF-8"),
                    URLEncoder.encode(paramProductID, "UTF-8"),
                    URLEncoder.encode(paramQuantity, "UTF-8"),
                    URLEncoder.encode(paramRemarks, "UTF-8"),
                    URLEncoder.encode(paramUM, "UTF-8"));

            URLConnection connection = url.openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + "UTF-8");


            OutputStream output = connection.getOutputStream();
            output.write(query.getBytes("UTF-8"));


            InputStream instream = connection.getInputStream();
            Scanner scanner = new Scanner(instream).useDelimiter("\\A");
            String objectHtml = scanner.hasNext() ? scanner.next() : ""; // returns the instream in one string. objectHtml is the POST response in a string.
            String s = "<span class='productPrice'>";
            int index = objectHtml.indexOf(s) + s.length(); // end of first occurance of s (beginning of pd price)
            int offset = objectHtml.substring(index).indexOf(' '); // product's price length
            String price = objectHtml.substring(index, index + offset);
            return price;
        }
        catch(Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }
	public static void main(String[] args){
		System.out.println(getPriceAfterDiscount("7296073231578","3"));
	}
}