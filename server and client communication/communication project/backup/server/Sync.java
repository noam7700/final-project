package server;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

// global synchronization variables
public class Sync {
	static Integer DataConsumers = 0;
	static ReentrantLock updateLock = new ReentrantLock();
	//static ReentrantLock consumersCount = new ReentrantLock();
	Integer o;
	static ReentrantLock detailsTableReaders = new ReentrantLock();
	static ReentrantLock basketsTableReaders = new ReentrantLock();
	
}
