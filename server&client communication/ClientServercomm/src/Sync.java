import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// global synchronization variables
public class Sync {
	static int DataConsumers = 0;
	static Lock updateLock = new ReentrantLock();
	static Lock counter_lock = new ReentrantLock();;
}
