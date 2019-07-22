package communicationObjects;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductInfo implements Serializable {
	 private static final long serialVersionUID = 1L;
	 private String id_str;       // for example: "divProduct_112552446"
	 private String price_str;    // presented in the app (with NIS symbol)
	 private double price;        // the price itself
	 private String desc;         // pd's description
	 private String supplier_desc;
	 // just for user's usage. if the pd is per unit, it's already in the price attr
	 private String price_perunit_str;
	 private String img_src;
	 private String categoryBelongsTo;
	 public ProductInfo(String id_str, String price_str, String desc, String supplier_desc,
	           String price_perunit_str, String img_src,String categoryBelongsTo) {
		  this.id_str = id_str;
		  this.price_str = price_str;
		  // parts[0] is the price, parts[1] is NIS symbol
		  String[] parts = price_str.split(" ");

		  // supports commas in the number. like "1,399.00"
		  NumberFormat format = NumberFormat.getInstance(Locale.US);
		  Number number = null;
		  try {
			   number = format.parse(parts[0]);

		  } catch (ParseException e) {
			   e.printStackTrace();
		  }
		  this.price = number.doubleValue();
		  this.desc = desc;
		  this.supplier_desc = supplier_desc;
		  this.price_perunit_str = price_perunit_str;
		  this.img_src = img_src;
		  this.categoryBelongsTo = categoryBelongsTo;
	 }

	 // getters:
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

	 public String getImg_src() {
		  return img_src;
	 }

	 public String getCategoryBelongsTo() {
		  return categoryBelongsTo;
	 }

}
