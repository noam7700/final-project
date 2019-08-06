package updateContentFromWeb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import databaseProductsUpdate.DatabaseProductsUpdater;

public class DataUpdateThread extends Thread {
	 public DataUpdateThread() {
		  start();
	 }

	 private void modifyWebContentToFile() throws IOException, InterruptedException, SQLException { // running web-scraping
	                                                                                        // script by cmd
		  String command = new String("node readProductsData.js"); // script has to be in project's directory
		  System.out.println("Starting to update products details");
		  System.out.println("Do you allow to update the details? [y/n]: ");
		  InputStreamReader inputStream = new InputStreamReader(System.in);
		  char updatePermission = (char) inputStream.read();
		  if( updatePermission != 'y' ) return; 

		  Process p = Runtime.getRuntime().exec("cmd /c start /wait cmd.exe /K \"" + command + "&&exit\"");
		  p.waitFor(); // waiting for script to be finished
		  System.out.println("Products File update finished.");
		  File productsFile = new File("productsTextData.txt");
		  DatabaseProductsUpdater.updateDatabaseProducts(productsFile);

	 }

	 long calcTimeTill(String time) {
		  SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		  Date dateTo = null, zero_p = null;
		  try {
			   dateTo = format.parse(time);
			   zero_p = format.parse("00:00:00");
		  } catch (Exception e) {
			   System.err.println("Error in parsing time for update-thread to sleep");
		  }
		  Calendar cal = Calendar.getInstance();
		  cal.set(Calendar.HOUR_OF_DAY, 0);
		  cal.set(Calendar.MINUTE, 0);
		  cal.set(Calendar.SECOND, 0);
		  cal.set(Calendar.MILLISECOND, 0);
		  long zero_point = cal.getTimeInMillis();
		  long timeNow = System.currentTimeMillis() - zero_point;
		  long timeTo = dateTo.getTime() - zero_p.getTime();
		  return timeTo - timeNow;
	 }

	 public void run() {
		  while (true) {
			   long timeTillMidnight = calcTimeTill("23:59:59");
			   try {
					sleep(timeTillMidnight); // sleeping till 00:00
			   } catch (InterruptedException e) {
					System.err.println("Failure of update_thread.sleep().");
			   }
			   FileAccessSynchronization.fileModifyLock.lock();
			   synchronized (FileAccessSynchronization.currentFileConsumersCount) {
					while (FileAccessSynchronization.currentFileConsumersCount > 0) { // waiting for current data
					                                                                  // consumers to finish
						 try {
							  FileAccessSynchronization.currentFileConsumersCount.wait();
						 } catch (Exception e) {
							  System.err.println("Error waiting to file data consumers to finish.");
							  System.err.println(e.getMessage());
						 }
					}
					// data consumers is now 0 -> we can start to modify the data.
			   }
			   try {
					modifyWebContentToFile();
			   } catch (IOException | InterruptedException | SQLException e) {
					e.printStackTrace();
			   }
			   FileAccessSynchronization.fileModifyLock.unlock(); // only now data can be consumed again
		  }
	 }
}
