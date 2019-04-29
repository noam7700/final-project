package ServerComm;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

// readers = 0, noneReaders = 1
public class ParallelSets {
	ReentrantLock[] groupsLocks;
	Queue<ReentrantLock> nextGroupQueue = new PriorityQueue<ReentrantLock>();
	ReentrantLock queueLock = new ReentrantLock();

	public ParallelSets(int numGroups) {
		groupsLocks = new ReentrantLock[numGroups];
	}

	public void awaitToGroup(int groupNum) { // groupNum has to be valid
		ReentrantLock groupLock = groupsLocks[groupNum];
		synchronized (nextGroupQueue) {
			nextGroupQueue.add(groupLock);
			nextGroupQueue.notify();
		}

		groupLock.lock();
		for (ReentrantLock lock : groupsLocks)
			if (lock != groupLock) {
				// waits till lock is released (no corresponding group members are using the
				// system)
				lock.lock();
				lock.unlock();
			}
	}
	
	public void releaseGroup(int groupNum) { // groupNum has to be valid
		groupsLocks[groupNum].unlock();
	}
}
