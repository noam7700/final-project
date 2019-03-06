import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
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
		String discount = "";
		try {
			String urlParameters  = "AjaxCallAction=AddProductToBasket&paramProductID="+prodID+"&paramQuantity="+qty+"&paramRemarks=&paramUM="+paramUM;
			URL url = new URL("https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx");
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			
			conn.setRequestProperty( "charset", "UTF-8");
			conn.setRequestProperty("Cookie", "ASP.NET_SessionId=lbfycn45xacz3y55hfpifk2n; BIGipServerPool_ShufersalDirect_Commerce_Servers_HTTP=1953305610.20480.0000;");								
			// Send post request
			conn.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
			wr.writeBytes( urlParameters );
			wr.flush();
			wr.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String HtmlObject="",line; while((line = in.readLine()) != null) HtmlObject += line; // casting the response to string
			discount = parseDiscFromHtml(HtmlObject);
			in.close();
		}
		catch(Exception e) {e.printStackTrace();}
		
		return discount;
	}
	public static void main(String[] args){
		String qty = "94", prodID = "408354";
		System.out.println("discount for " + qty + " products of {pd: pd_id = " +prodID + "} is:  " + getDiscount(prodID, qty)); // notice that the values are strings
		
	}
	
}







