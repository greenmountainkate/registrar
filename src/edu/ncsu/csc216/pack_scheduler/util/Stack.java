package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface that outlines the required behaviors of Stack objects
 * 
 * @author kmbrown
 * 
 * @param <E>
 *            the elements in the Stack
 */
public interface Stack<E> {

	/**
	 * Adds an element to the stack in the top position, if there is capacity
	 * for another element to be added
	 * 
	 * @param element
	 *            the element to be added to the list
	 * @throws IllegalArgumentException
	 *             if there is no capacity on the list for another element
	 */
	void push(E element);

	/**
	 * Removes and returns the element on the top of the Stack
	 * 
	 * @return the element removed from the list
	 * 
	 */
	E pop();

	/**
	 * Checks whether the stack is empty
	 * 
	 * @return true if the stack is empty
	 */
	boolean isEmpty();

	/**
	 * Retrieves the number of elements in the stack
	 * 
	 * @return the number of elements in the stack
	 */
	int size();

	/**
	 * Sets the capacity of the stack, if the new capacity is positive and
	 * greater than the current number of elements in the stack
	 * 
	 * @param capacity the amount of space in the stack
	 */
	void setCapacity(int capacity);

}
