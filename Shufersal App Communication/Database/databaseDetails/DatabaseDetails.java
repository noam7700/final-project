package databaseDetails;

import java.util.HashMap;


@SuppressWarnings("serial")
public class DatabaseDetails {
	 
	 private static final String host     = "remotemysql.com";
	 private static final String DBName   = "ZUXezOHIFk";   
	 private static final String username = "ZUXezOHIFk";   
	 
	 private static HashMap<Table, String> tablesNames = new HashMap<Table, String>() {
    	 {
    		  put(Table.customerUsers, "customerUsers");
    		  put(Table.editorUsers, "editorUsers");
    		  put(Table.contentManagerUsers, "contentManagerUsers");
    		  put(Table.generalManagerUsers, "generalManagerUsers");
    		  put(Table.mapsMetaDetails, "mapsMetaDetails");
    		  put(Table.mapsFiles, "mapsFiles");
    		  put(Table.mapsSites, "mapsSites");
    		  put(Table.citiesMetaDetails, "citiesMetaDetails");
    		  put(Table.citiesMapsIds, "citiesMaps");
    		  put(Table.citiesSitesIds, "citiesSites");
    		  put(Table.sites, "sites");
    		  put(Table.tourSitesIdsAndDurance, "toursSites");
    		  put(Table.citiesTours, "citiesTours");
    		  put(Table.toursMetaDetails, "toursMetaDetails");
    		  put(Table.mapsTours, "mapsTours");
    		  put(Table.purchaseHistory, "purchaseDeatailsHistory");
    		  put(Table.mapsDownloadHistory, "mapsDownloadHistory");
    		  put(Table.customerPurchaseDetails, "costumerPurchaseDetails");
    	 }
	 };

	 public static enum Table {
		  customerUsers, editorUsers, contentManagerUsers, generalManagerUsers, mapsMetaDetails, mapsFiles, mapsSites,
		  citiesMetaDetails, citiesMapsIds, citiesSitesIds, sites, toursMetaDetails, tourSitesIdsAndDurance, mapsTours,
		  citiesTours, purchaseHistory, mapsDownloadHistory, customerPurchaseDetails
	 }
	 
	 public static String getTableName(Table table) {
		  return tablesNames.get(table);
	 }
	 
	 public static String getHostName() {
		  return host;
	 }

	 public static String getDbName() {
		  return DBName;
	 }

	 public static String getDbUsername() {
		  return username;
	 }

}
