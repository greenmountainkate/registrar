package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom implementation of the ArrayList functionality to simplify use of
 * unordered lists
 * 
 * @author kmbrown
 *
 * @param <E>
 *            Elements that can be added to an ArrayList
 */
public class ArrayList<E> extends AbstractList<E> {

	/** Constant value for initializing the list size */
	private static final int INIT_SIZE = 10;
	/** The list being managed by ArrayList */
	private E[] list;
	/** The size of the list */
	private int size;

	/**
	 * Null constructor for ArrayList, creates an empty list, with a capacity of
	 * 10
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;

	}

	/**
	 * Allows an element to be added to the list, if it is not null and is not
	 * being added beyond the valid index of the list
	 * 
	 * @param index
	 *            the location in the list where the element should be inserted
	 * @param element
	 *            the element to be inserted into the list, cannot be null or a
	 *            duplicate of an element already in the list
	 */
	@Override
	public void add(int index, E element) {

		// if at capacity, grow list
		if (size == list.length) {
			growArray();
		}

		// check if element to add is null
		if (element == null) {
			throw new NullPointerException();
		}

		// check for duplicates
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		// check if out of range
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		// if front or middle, shift and add
		if (index < size) {
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
			list[index] = element;
			size++;

		}

		// if end, add
		if (index == size && size < list.length) {

			list[index] = element;
			size++;

		}

	}

	@SuppressWarnings("unchecked")
	private void growArray() {

		int newSize = size * 2;

		E[] growingList = (E[]) new Object[newSize];

		for (int i = 0; i < list.length; i++) {
			growingList[i] = list[i];
		}

		list = growingList;

	}

	/**
	 * Seamlessly removes elements from the ArrayList, if the index provided is
	 * valid.
	 * 
	 * @param index
	 *            the position of the Element to be removed
	 * @return the Element removed from the list
	 */
	@SuppressWarnings("unchecked")
	public E remove(int index) {

		// check if in range
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		// set e to Object at index
		Object e = list[index];

		// if at end, remove
		if (index == size - 1 && size < list.length) {
			list[size - 1] = null;
			size--;
		}

		// if front or middle, shift and remove
		if (size - 1 > index) {

			// shift list to the left
			for (int i = index; i < size - 1; i++) {
				list[i] = list[i + 1];
			}

			// remove item at index
			list[size - 1] = null;

			// decrement size
			size--;
		}

		// return element removed

		return (E) e;

	}

	@Override
	public E get(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		return list[index];
	}

	@Override
	public int size() {

		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E set(int index, E element) {

		// check if element to add is null
		if (element == null) {
			throw new NullPointerException();
		}

		// check for duplicates
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		// check if out of range
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		// replace element
		Object e = list[index];

		if (index < size) {
			list[index] = element;
		}

		return (E) e;

	}

}
