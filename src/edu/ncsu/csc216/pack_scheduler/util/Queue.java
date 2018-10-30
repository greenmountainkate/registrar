package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Outlines the methods of Queues
 * 
 * @author kmbrown
 *
 * @param <E>
 *            elements that can be contained in a Queue
 */
public interface Queue<E> {
	
	
	/**
	 * Adds the element to the back of the Queue
	 * 
	 * @param element
	 *            the element to be added to the back of the Queue, as long as
	 *            there is capacity in the Queue
	 */
	void enqueue(E element);

	
	
	/**
	 * Removes the element at the front of the Queue
	 * 
	 * @return the element removed from the Queue
	 */
	E dequeue();

	
	
	/**
	 * Checks whether there are elements in the Queue
	 * 
	 * @return true if the Queue is empty
	 */
	boolean isEmpty();

	
	
	/**
	 * Retrieves the current number of elements in the Queue
	 * 
	 * @return the current size of the Queue
	 */
	int size();
	
	

	/**
	 * Sets the capacity of the Queue
	 * 
	 * @param capacity
	 *            the number of elements that the Queue should be able to
	 *            contain
	 */
	void setCapacity(int capacity);
}
