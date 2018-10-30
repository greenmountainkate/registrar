package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * An implementation of Queue based on LinkedAbstractList functionality
 * 
 * @author kmbrown
 *
 * @param <E>
 *            the elements in the Queue
 */
public class LinkedQueue<E> implements Queue<E> {

	/** The Queue */
	private LinkedAbstractList<E> linkedQueue;
	/** The amount of room in the Queue */
	private int capacity;
	/** The current size of the Queue */
	private int size;

	/**
	 * Constructs a new LinkedQueue of given capacity
	 * 
	 * @param capacity
	 *            the number of elements that can be held in the LinkedQueue
	 */
	public LinkedQueue(int capacity) {
		linkedQueue = new LinkedAbstractList<E>(capacity);
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

		if (size() < getCapacity()) {
			int end = size();
			linkedQueue.add(end, element);
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
			E removedElement = linkedQueue.remove(0);
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
		return linkedQueue.size() == 0;
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
		linkedQueue.setCapacity(capacity);
	}

	/**
	 * Retrieves the current capacity of the Queue
	 * 
	 * @return the total number of elements that the Queue can hold
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Retrieves the information at a particular index of the LinkedQueue
	 * 
	 * @param index
	 *            the location to retrieve data from, cannot be negative or
	 *            larger than the size of the Queue
	 * @return the data contained at the targeted index
	 */
	public E getData(int index) {
		if (!isEmpty() && index >= 0 && index < size()) {
			return linkedQueue.get(index);
		} else {
			throw new IndexOutOfBoundsException();
		}

	}
	
	/**
	 * Removes an element from the Queue, based on an indexed location
	 * @param index the location of the element to remove
	 * @return the element removed
	 */
	public E remove(int index) {
		if (!isEmpty() && index >= 0 && index < size()) {
			E removedElement = linkedQueue.remove(index);
			size--;
			return removedElement;
		} else {
			throw new IndexOutOfBoundsException();
		}
		
	}

}
