package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Manages the construction and maintenance of a LinkedListRecursive. The List
 * requires an E parameter.
 * 
 * @author kmbrown
 *
 * @param <E> element that can be added to the List
 */
public class LinkedListRecursive<E> {

	/** The number of elements in the List */
	private int size;
	/** Points to the front of the List */
	private ListNode front;

	/**
	 * Creates an empty List.
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;

	}

	/**
	 * Checks whether there are any elements in the List
	 * 
	 * @return true if the List is empty
	 */
	public boolean isEmpty() {

		return size == 0;
	}

	/**
	 * Retrieves the current size of the List
	 * 
	 * @return the number of elements in the List
	 */
	public int size() {

		return size;

	}

	/**
	 * Adds an element to the end of the List
	 * 
	 * @param element
	 *            the element to add to the List, cannot already be present in
	 *            the List
	 * @return true if the element was added to the list
	 * @throws IllegalArgumentException
	 *             if the element is already present on the list
	 */
	public boolean add(E element) {

		if (contains(element)) {
			throw new IllegalArgumentException();
		}

		if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		}

		return front.add(element);
	}

	/**
	 * Adds the given Element at the given position in the List, if the index is
	 * valid and the Element is not a duplicate
	 * 
	 * @param index
	 *            the position where the Element should be added
	 * @param element
	 *            the Element to add to the List, cannot already be present on
	 *            the List or null
	 * @throws IllegalArgumentException
	 *             if the Element is already on the List
	 * @throws NullPointerException
	 *             if the Element to add is null
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public void add(int index, E element) {

		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (!isEmpty() && index == 0) {
			front = new ListNode(element, front);
			size++;
		} else if (index == 0 && isEmpty()){
			front = new ListNode(element, null);
			size++;
		} else {
		
			front.add(index, element);
		}
	}

	/**
	 * Retrieves the Element at a particular index in the List, throws an
	 * IndexOutOfBoundsException if the index is invalid
	 * 
	 * @param index
	 *            the location of the Element to retrieve
	 * @return the Element at that position in the List
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public E get(int index) {

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		E e = front.get(index);

		return e;
	}

	/**
	 * Removes the targeted Element if it is present in the List
	 * @param element the Element to remove, cannot be null
	 * @return true if the Element was removed from the List
	 * @throws NullPointerException if the Element to be removed is null
	 */
	public boolean remove(E element) {

		if (element == null) {
			return false;
		}
		if (isEmpty()) {
			return false;
		}
		if (front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		}

		return front.remove(element);
	}

	/**
	 * Removes the element at a particular position in the List
	 * 
	 * @param index
	 *            the position to remove
	 * @return the Element removed from the List
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public E remove(int index) {
		
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		E e;

		if (index == 0) {
			e = front.data;
			front = front.next;
			size--;
			return e;
		}

		e = front.remove(index);
		return e;

	}
	
	/**
	 * Sets the value at a particular index, if the index is valid 
	 * @param index the position where the value should be updated
	 * @param element the updated value to be placed at the given position
	 * @return the value that was replaced in the list
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public E set(int index, E element) {
		
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		
		if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return null;
		}
		
		if (element == null) {
			throw new NullPointerException();
		}
		
		return front.set(index, element);
		
		
	}

	/**
	 * Determines whether a given Element is present on the List
	 * @param element the value to search the List for
	 * @return true if the element is present on the List
	 */
	public boolean contains(E element) {

		if (isEmpty()) {
			return false;
		}

		return front.contains(element);

	}

	/**
	 * Manages the individual Nodes of the List
	 * 
	 * @author kmbrown
	 *
	 */
	private class ListNode {

		public ListNode next;

		public E data;

		public ListNode(E element, ListNode listNode) {
			next = listNode;
			data = element;
		}

		public boolean add(E element) {

			if (next == null) {

				next = new ListNode(element, next);
				size++;
				return true;
			}

			return next.add(element);

		}

		public void add(int index, E element) {
			
			if (index == 1) {
				this.next = new ListNode(element, next);
				size++;
				return;
			}

			index--;
			
			next.add(index, element);

		}

		public E get(int index) {

			if (index == 0) {
				return data;
			}
			index--;
			return next.get(index);
		}

		public E remove(int index) {

			if (index == 1 && next != null) {
				
				E e = next.data;
				next = next.next;
				//TODO error is here.  Figure it out.  next.next is always null, somehow not linking chain properly
				size--;
				return e;
				
			}
			index--;
			return next.remove(index);

		}

		public boolean remove(E element) {

			if (next == null) {
				return false;
			}
			
			if (next.data.equals(element)) {
				next = next.next;
				size--;
				return true;
			}

			

			return next.remove(element);
		}

		public E set(int index, E element) {
			
			if(index == 0){
				E e = data;
				data = element;
				return e;				
			}
			
			index--;
			return next.set(index, element);
		}

		public boolean contains(E element) {

			if (data.equals(element)) {
				return true;
			}

			if (next == null) {
				return false;
			}

			return next.contains(element);
		}
	}
}
