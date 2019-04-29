package ServerComm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUpdateThread extends Thread {
	DataUpdateThread() {
		start();
	}

	void updateData() { // running web-scraping script by cmd
		String command = new String("node readProductsData.js&&exit"); // script has to be in project's directory
		try {
			System.out.println("Starting to update products details");
			Process p = Runtime.getRuntime().exec("cmd /c start /wait cmd.exe /K \"" + command + "\"");
			p.waitFor(); // waiting for script to be finished
			System.out.println("Data update finished.");

		} catch (Exception e) {
			System.err.println("Error running nodejs script");
			e.printStackTrace();
		}
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
			Sync.updateLock.lock();
//			Sync.consumersCount.lock();
			synchronized (Sync.DataConsumers) {
				while (Sync.DataConsumers > 0) { // waiting for current data consumers to finish
					try {
						Sync.DataConsumers.wait();
					} catch (Exception e) {
						System.err.println("Error waiting to file data consumers to finish.");
						System.err.println(e.getMessage());
					}
				}
				// data consumers is now 0 -> we can start to update the data.
			}
//			Sync.consumersCount.unlock();
			updateData();
			Sync.updateLock.unlock(); // only now data can be consumed again
		}
	}
}
