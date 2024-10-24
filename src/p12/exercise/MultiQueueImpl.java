package p12.exercise;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

	@Override
	public Set<Q> availableQueues() {
		return null;
	}

	@Override
	public void openNewQueue(Q queue) {
	}

	@Override
	public boolean isQueueEmpty(Q queue) {
		return false;
	}

	@Override
	public void enqueue(T elem, Q queue) {
	}

	@Override
	public T dequeue(Q queue) {
		return null;
	}

	@Override
	public Map<Q, T> dequeueOneFromAllQueues() {
		return null;
	}

	@Override
	public Set<T> allEnqueuedElements() {
		return null;
	}

	@Override
	public List<T> dequeueAllFromQueue(Q queue) {
		return null;
	}

	@Override
	public void closeQueueAndReallocate(Q queue) {
	}

}
