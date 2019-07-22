package updateContentFromWeb;

import java.util.concurrent.locks.ReentrantLock;

public class FileAccessSynchronization {
	 public static Integer currentFileConsumersCount = 0;
	 static ReentrantLock  fileModifyLock            = new ReentrantLock();
	 public static ReentrantLock getUpdateLock() {
		  return fileModifyLock;
	 }

	 public static Integer getCurrentConsumersCount() {
		  return currentFileConsumersCount;
	 }
	 
}
