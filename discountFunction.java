import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import java.lang.*;

import javax.net.ssl.HttpsURLConnection;


public class discountFunction{
	
	public static String parseDiscFromHtml(String htmlString){
		String object = "discountPerUnit='"; // for some reason this field contains the total discount value. 
		String price = "";
		if(htmlString.contains(object)){
			int index = htmlString.indexOf(object) + object.length(); 
			price = htmlString.substring(index, htmlString.indexOf('\'', index)).replaceAll(" ","");
		}
		return price;
	}
	
	public static String getDiscount(String prodID, String qty){
		String paramUM = "";
		String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";
		String discount = "";
		try {

			String urlParameters  = "AjaxCallAction=AddProductToBasket&paramProductID="+prodID+"&paramQuantity="+qty+"&paramRemarks=&paramUM="+paramUM;
			byte[] postData       = urlParameters.getBytes("UTF-8");//StandardCharsets.UTF_8 );
			
			int postDataLength = postData.length;
			URL url = new URL("https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx");
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty( "charset", "UTF-8");
			//conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9,he;q=0.8");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty("Host", "www.shufersal.co.il");
			conn.setRequestProperty("Origin", "https://www.shufersal.co.il");
			conn.setRequestProperty("Referer", "https://www.shufersal.co.il");
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.addRequestProperty("X-Requested-With","XMLHttpRequest");
			conn.setRequestProperty("Cookie", "ASP.NET_SessionId=lbfycn45xacz3y55hfpifk2n; BIGipServerPool_ShufersalDirect_Commerce_Servers_HTTP=1953305610.20480.0000;");
											
			// Send post request
			conn.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
			wr.writeBytes( urlParameters );
			wr.flush();
			wr.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String HtmlObject="",line; while((line = in.readLine()) != null) HtmlObject += line;
			//System.out.println(HtmlObject);
			discount = parseDiscFromHtml(HtmlObject);
			in.close();
		}
		catch(Exception e) {e.printStackTrace();}
		
		return discount;
	}
	public static void main(String[] args){
		System.out.println("discount for 8 products of {pd: pd_id = 408354} is:  " + getDiscount("408354", "8")); // notice that the values are strings
		
	}
	
}







