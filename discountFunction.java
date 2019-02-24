
/*
skeleton of the discount function. left to run&check it
*/
public static String getPriceAfterDiscount(String prodID, String qty){ /*returns a string of price after discount. note: id and qty are sent as strings.*/
    HttpClient httpclient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost("https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx");
    String remarks = "";
    String unitofmeasure = "";
    // Request parameters and other properties.
    List<NameValuePair> params = new ArrayList<NameValuePair>(5);
    params.add(new BasicNameValuePair("AjaxCallAction", "AddProductToBasket"));
    params.add(new BasicNameValuePair("paramProductID", prodID));
    params.add(new BasicNameValuePair("paramQuantity", qty));
    params.add(new BasicNameValuePair("paramRemarks", remarks));
    params.add(new BasicNameValuePair("paramUM", unitofmeasure));

    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

    //Execute and get the response.
    HttpResponse response = httpclient.execute(httppost);
    HttpEntity entity = response.getEntity();
    if (entity != null) {
        try (InputStream instream = entity.getContent()) {
            String objectHtml = IOUtils.toString(instream, "UTF-8");
            String s = "<span class='productPrice'>";
            int index1 = objectHtml.indexOf(s) + s.length();
            int index2 = objectHtml.substring(index1).indexOf(' ');
            String price = objectHtml.substring(index1,index1+index2);
            return price;
        }
    }
    return null;
}

public static void main(){
	system.out.println(getPriceAfterDiscount("7296073280354","3"));
	return;
}