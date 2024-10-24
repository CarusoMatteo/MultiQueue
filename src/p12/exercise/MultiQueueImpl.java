package p12.exercise;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/* Q is the type of the unique identifier of queues 
 * that contains elements of type T. 
 */
public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {
	private final Map<Q, Queue<T>> queues = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Q> availableQueues() {
		return Set.copyOf(this.queues.keySet());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void openNewQueue(final Q queue) {
		if (this.queues.containsKey(queue)) {
			throw new IllegalArgumentException("The queue " + queue + " is already available.");
		}

		this.queues.put(queue, new ArrayDeque<T>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isQueueEmpty(final Q queue) {
		if (!this.queues.containsKey(queue)) {
			throw new IllegalArgumentException("The queue " + queue + " is not available.");
		}

		return this.queues.get(queue).isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enqueue(final T elem, final Q queue) {
		if (!this.queues.containsKey(queue)) {
			throw new IllegalArgumentException("The queue " + queue + " is not available.");
		}

		this.queues.get(queue).offer(elem);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T dequeue(final Q queue) {
		if (!this.queues.containsKey(queue)) {
			throw new IllegalArgumentException("The queue " + queue + " is not available.");
		}

		return this.queues.get(queue).poll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Q, T> dequeueOneFromAllQueues() {

		final Map<Q, T> elements = new HashMap<>();

		for (final Q queueKey : this.queues.keySet()) {
			elements.put(queueKey, this.queues.get(queueKey).poll());
		}

		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> allEnqueuedElements() {
		final Set<T> elements = new HashSet<>();

		for (final Queue<T> queue : this.queues.values()) {
			elements.addAll(queue);
		}

		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> dequeueAllFromQueue(final Q queue) {
		if (!this.queues.containsKey(queue)) {
			throw new IllegalArgumentException("The queue " + queue + " is not available.");
		}

		final List<T> elements = new ArrayList<>();
		final Queue<T> queueToEmpty = this.queues.get(queue);

		while (!queueToEmpty.isEmpty()) {
			elements.add(queueToEmpty.poll());
		}

		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeQueueAndReallocate(final Q queue) {
		if (!this.queues.containsKey(queue)) {
			throw new IllegalArgumentException("The queue " + queue + " is not available.");
		}
		if (this.queues.size() == 1) {
			throw new IllegalStateException("There is no alternative queue to move elements into.");
		}

		final Queue<T> queueToRemove = this.queues.remove(queue);
		final Queue<T> queueToUpdate = getFirstAvailableQueue();

		while (!queueToRemove.isEmpty()) {
			queueToUpdate.offer(queueToRemove.poll());
		}
	}

	private Queue<T> getFirstAvailableQueue() {
		return queues.values().iterator().next();
	}
}
