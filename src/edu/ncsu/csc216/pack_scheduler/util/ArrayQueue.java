package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * An implementation of Queue that is based on ArrayList functionality
 * 
 * @author kmbrown
 *
 * @param <E>
 *            elements in the Queue
 */
public class ArrayQueue<E> implements Queue<E> {
	/** The Queue */
	private ArrayList<E> arrayQueue;
	/** The amount of room in the Queue */
	private int capacity;
	/** The current size of the Queue */
	private int size;

	/**
	 * Constructs a new ArrayQueue of given capacity
	 * 
	 * @param capacity
	 *            the number of elements that can be held in the ArrayQueue
	 */
	public ArrayQueue(int capacity) {

		arrayQueue = new ArrayList<E>();
		setCapacity(capacity);
		size = 0;
	}

	/**
	 * Adds an element to the end of the Queue and increments the size of the
	 * Queue, if there is room in the Queue
	 * 
	 * @param element
	 *            the data to be added to the end of the Queue, cannot be null
	 */
	@Override
	public void enqueue(E element) {
		
		if (size() < capacity) {
			int end = size();
			arrayQueue.add(end, element);
			size++;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Removes the element from the front of the Queue, if the Queue is not
	 * empty
	 * 
	 * @return the element at the front of the Queue
	 */
	@Override
	public E dequeue() {

		if (size() > 0) {
			E removedElement = arrayQueue.remove(0);
			size--;
			return removedElement;
		} else {
			throw new NoSuchElementException();
		}

	}

	/**
	 * Checks whether the Queue contains elements
	 * 
	 * @return if there are no elements in the Queue
	 */
	@Override
	public boolean isEmpty() {
		return arrayQueue.size() == 0;
	}

	/**
	 * Retrieves the size of the Queue
	 * 
	 * @return the number of elements in the Queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the Queue capacity if the capacity is positive and larger than the
	 * current Queue size
	 * 
	 * @param capacity
	 *            the number of elements that the Queue should be able to
	 *            contain, must be positive and larger than current Queue size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Retrieves the current capacity of the Queue
	 * 
	 * @return the total number of elements that the Queue can hold
	 */
	public int getCapacity() {
		return capacity;
	}

}
