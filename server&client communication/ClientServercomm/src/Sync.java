import java.util.concurrent.locks.ReentrantLock;

// global synchronization variables
public class Sync {
	static int DataConsumers = 0;
	static ReentrantLock updateLock = new ReentrantLock();
	static ReentrantLock counter_lock = new ReentrantLock();;
}
