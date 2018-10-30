package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * An implementation of Stack that is based in LinkedAbstractList functionality
 * 
 * @author kmbrown
 *
 * @param <E>
 *            elements that are included in the Stack
 */
public class LinkedStack<E> implements Stack<E> {

	/** The list */
	private LinkedAbstractList<E> linkStack;
	/** The amount of room on the list */
	private int capacity;
	/** The current size of the list */
	private int size;

	/**
	 * Constructs a new LinkedStack of given capacity
	 * 
	 * @param capacity
	 *            the number of elements that can be held in the LinkedStack
	 */
	public LinkedStack(int capacity) {
		linkStack = new LinkedAbstractList<>(capacity);
		setCapacity(capacity);
		size = 0;
	}

	/**
	 * Adds an element to the top of the Stack and increments the size of the
	 * Stack
	 */
	@Override
	public void push(E element) {
		if (linkStack.size() < linkStack.getCapacity()) {
			linkStack.add(0, element);
			size++;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Removes the element at the top of the Stack and decrements the size of
	 * the Stack
	 * 
	 * @return the element removed from the Stack
	 */
	@Override
	public E pop() {

		if (size() > 0) {
			E removedElement = linkStack.remove(0);
			size--;
			return removedElement;
		} else {
			throw new EmptyStackException();
		}
	}

	/**
	 * Checks whether the Stack contains elements
	 * 
	 * @return true if there are no elements in the Stack
	 */
	@Override
	public boolean isEmpty() {
		return linkStack.size() == 0;
	}

	/**
	 * Retrieves the size of the Stack
	 * 
	 * @return the number of elements in the Stack
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the list capacity if the capacity is positive and larger than the
	 * current list size
	 * 
	 * @param capacity
	 *            the number of elements that the list should be able to
	 *            contain, must be positive and larger than current list size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		linkStack.setCapacity(capacity);
	}

	/**
	 * Retrieves the current capacity of the ArrayStack
	 * 
	 * @return the total number of elements that can be added to the ArrayStack
	 */
	public int getCapacity() {
		return capacity;
	}

}
