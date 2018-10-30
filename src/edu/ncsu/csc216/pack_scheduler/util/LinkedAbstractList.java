package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom implementation of the LinkedAbstractList functionality to create a
 * list that doesn't allow for null elements or duplicate elements
 * 
 * @author kmbrown
 *
 * @param <E>
 *            Elements that can be added to a LinkedAbstractList
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** First Node in the LinkedList */
	private ListNode<E> front;
	/** Last Node in the LinkedList */
	private ListNode<E> back;
	/** The size of the LinkedList */
	private int size;
	/** The capacity of the LinkedList */
	private int capacity;

	/**
	 * Constructor for the LinkedAbstractList
	 * 
	 * @param capacity
	 *            the initial capacity for the LinkedAbstractList
	 */
	public LinkedAbstractList(int capacity) {
		front = null;
		back = null;
		size = 0;
		if (capacity < 0) {
			throw new IllegalArgumentException();
		} else {
			this.capacity = capacity;
		}
	}

	@Override
	public E get(int index) {
		// check for valid index
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return nodeAt(index).data;
	}

	/**
	 * Updates the data at a particular Node of the LinkedList
	 * 
	 * @param index
	 *            the indexed location to change
	 * @param element
	 *            the element to place at the indexed location
	 * 
	 * @return the data that was replaced by the updated data
	 */
	public E set(int index, E element) {
		// check valid index
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		// check that element is not null
		if (element == null) {
			throw new NullPointerException();
		}
		// check that element is not a duplicate
		for (int i = 0; i < size(); i++) {
			if (element.equals(get(i))) {
				throw new IllegalArgumentException();
			}
		}
		E removedData;
		// set element
		if (index == 0) {
			removedData = front.data;
			front.data = element;
			return removedData;
		} else {
			ListNode<E> current = nodeAt(index);
			removedData = current.data;
			current.data = element;
			return removedData;
		}
	}

	/**
	 * Inserts new data into the list at the given location, without breaking
	 * the list or disordering it
	 * 
	 * @param index
	 *            the index at which the new data needs to be inserted
	 * @param element
	 *            the data to insert, cannot be null
	 */
	public void add(int index, E element) {

		// Check that the list has space for new element
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		// Check if index is valid
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		// Test if element to be added is null
		if (element == null) {
			throw new NullPointerException();
		}
		// Test for duplicate
		for (int i = 0; i < size(); i++) {
			if (element.equals(get(i))) {
				throw new IllegalArgumentException();
			}
		}

		// Check if List is empty
		if (front == null) {
			front = new ListNode<E>(element, null);
			back = front;
			size++;
			return;
		}

		// add element to front
		if (index == 0) {
			front = new ListNode<E>(element, front);
			size++;
		} else { // add element to end
			if (index == size() && size != 0) {
				back.next = new ListNode<E>(element, null);
				back = back.next;
				size++;
			} else {
				// add element to middle
				ListNode<E> current = nodeAt(index - 1);
				current.next = new ListNode<E>(element, current.next);
				size++;
			}

		}
	}

	/**
	 * Deletes a Node from the LinkedList at a given indexed location
	 * 
	 * @param index
	 *            the indexed location of the Node to remove from the LinkedList
	 * 
	 * @return the element removed from the list or null if the list is empty
	 */
	public E remove(int index) {
		// check that target index is valid
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		// Check that list isn't empty
		if (front == null) {
			return null;
		}

		E removedData;

		// remove from front
		if (index == 0) {
			removedData = front.data;
			front = front.next;
			size--;
			return removedData;
		} else {
			// remove from back
			if (index == size() - 1 && size() != 1) {
				ListNode<E> previous = nodeAt(index - 1);
				removedData = back.data;
				back = previous;
				size--;
				return removedData;

			} else {
				// remove from middle
				ListNode<E> current = nodeAt(index - 1);
				removedData = current.next.data;
				current.next = current.next.next;
				size--;
				return removedData;
			}
		}

	}

	@Override
	public int size() {

		return size;
	}

	/**
	 * Updates the capacity of the LinkedAbstractList, as long as the new
	 * capacity is positive and greater than the current capacity of the
	 * LinkedAbstractList
	 * 
	 * @param capacity
	 *            the updated capacity of the list, cannot be less than or equal to 0 or less than the
	 *            current capacity of the LinkedAbstractList
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Retrieves the capacity of the LinkedAbstractList
	 * 
	 * @return the current capacity of the LinkedAbstractList
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Finds the ListNode at a given index location in the LinkedList
	 * 
	 * @param index
	 *            the numeric location of the ListNode
	 * @return the ListNode at the given location
	 */
	private ListNode<E> nodeAt(int index) {

		ListNode<E> current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current;
	}

	/**
	 * A private, nested class that manages the individual Nodes in the
	 * LinkedList
	 * 
	 * @author kmbrown
	 *
	 * @param <T>
	 *            the Type that could be included in a ListNode
	 */
	private class ListNode<T> {

		/** The data in the node */
		T data;
		/** The next node in the list */
		private ListNode<T> next;

		public ListNode(T type) {
			data = type;
		}

		public ListNode(T type, ListNode<T> nextNode) {
			this(type);
			next = nextNode;
		}

	}

}
