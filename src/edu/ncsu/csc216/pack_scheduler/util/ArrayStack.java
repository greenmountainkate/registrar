package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * An implementation of Stack that is based on ArrayList functionality
 * 
 * @author kmbrown
 *
 * @param <E>
 *            elements in the Stack
 */
public class ArrayStack<E> implements Stack<E> {

	/** The Stack */
	private ArrayList<E> arrayStack;
	/** The amount of room on the Stack */
	private int capacity;
	/** The current size of the Stack */
	private int size;

	/**
	 * Constructs a new ArrayStack of given capacity
	 * 
	 * @param capacity
	 *            the number of elements that can be held in the ArrayStack
	 */
	public ArrayStack(int capacity) {
		arrayStack = new ArrayList<E>();
		setCapacity(capacity);
		size = 0;
	}

	/**
	 * Adds an element to the top of the Stack and increments the size of the Stack
	 * @param element the data to be added to the top of the Stack
	 */
	@Override
	public void push(E element) {
		
		if (arrayStack.size() < getCapacity()){
		arrayStack.add(0, element);
		size++;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Removes the element at the top of the Stack and decrements the size of the Stack
	 * 
	 * @return the element removed from the Stack
	 */
	@Override
	public E pop() {

		if(!isEmpty()){
		E removedElement = arrayStack.remove(0);
		size--;
		return removedElement;
		} else {
			throw new EmptyStackException();
		}
	}

	/**
	 * Checks whether the Stack contains elements
	 * @return true if there are no elements in the Stack
	 */
	@Override
	public boolean isEmpty() {
		return arrayStack.size() == 0;
	}

	/**
	 * Retrieves the size of the Stack
	 * @return the number of elements in the Stack
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the Stack capacity if the capacity is positive and larger than the current Stack size
	 * @param capacity the number of elements that the Stack should be able to contain, must be positive and larger than current Stack size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		
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
