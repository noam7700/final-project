import java.io.IOException;
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
			Process p = Runtime.getRuntime().exec("cmd /c start /wait cmd.exe /K \"" + command + "\"");
			p.waitFor(); // waiting for script to be finished
		} catch (Exception e) {
			System.err.println("Error running nodejs script");
			e.printStackTrace();
		}
	}

	long calcTimeTill(String time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date dateTo = null;
		try {
			dateTo = format.parse(time);
		} catch (Exception e) {
			System.err.println("Error in parsing time for update-thread to sleep");
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0);
		long zero_point = cal.getTimeInMillis();
		long timeNow = System.currentTimeMillis() - zero_point;
		return dateTo.getTime() - timeNow;
	}

	public void run() {
		while (true) {
			long timeTillMidnight = calcTimeTill("23:59:59");
			try {
				sleep(timeTillMidnight); // sleeping till 00:00
			} catch (InterruptedException e) {
				System.err.println("Failure of update_thread.sleep().");
			}
			synchronized (Sync.updateLock) {// blocking new clients to consume data
				synchronized (Sync.counter_lock) {
					while (Sync.DataConsumers > 0) { // waiting for current clients to finish consume data
						try {
							Sync.counter_lock.wait();
						} catch (Exception e) {
							System.err.println("Error waiting to counter_lock in updateThread.");
						}
					}
				}
				updateData();
			}
		}
	}
}
