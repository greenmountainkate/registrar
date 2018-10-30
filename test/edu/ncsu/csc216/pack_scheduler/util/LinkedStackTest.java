package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests the methods of the LinkedStack class
 * 
 * @author kmbrown
 *
 * @param <E> Elements contained in the LinkedStack
 */
public class LinkedStackTest<E> {

	/**
	 * Tests Constructor
	 */
	@Test
	public void linkedStackTest() {
		// construct new LinkedStack
		LinkedStack<E> as = new LinkedStack<E>(25);
		assertTrue(as instanceof LinkedStack);
	}

	/**
	 * Tests the ability to add or push elements onto the List
	 */
	@Test
	public void testPush() {
		LinkedStack<String> as = new LinkedStack<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";

		// insert single element
		try {
			as.push(a);
			assertEquals(1, as.size());

		} catch (IllegalArgumentException e) {
			fail();
		}
		// insert multiple elements and check size
		try {
			as.push(b);
			as.push(c);
			as.push(d);
			assertEquals(4, as.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove all and make sure can still add again
		try {
			as.pop();
			assertEquals(3, as.size());
			as.pop();
			assertEquals(2, as.size());
			as.pop();
			assertEquals(1, as.size());
			as.pop();
			assertEquals(0, as.size());
			// add new element
			as.push(d);
			assertEquals(1, as.size());
			// add removed element again
			as.push(a);
			assertEquals(2, as.size());

		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	/**
	 * Tests the ability to remove and properly return the top element in the Stack
	 */
	@Test
	public void testPop() {
		LinkedStack<String> as = new LinkedStack<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";
		String g = "grapes";

		// add several elements
		as.push(a);
		as.push(b);
		as.push(c);
		as.push(d);
		as.push(e1);
		as.push(f);
		assertEquals(6, as.size());

		// remove single element
		try {
			as.pop();
			assertEquals(5, as.size());
		} catch (IllegalArgumentException e) {
			fail();
		}
		// remove multiple elements
		try {
			as.pop();
			assertEquals(4, as.size());
			as.pop();
			assertEquals(3, as.size());
			as.pop();
			assertEquals(2, as.size());
			as.pop();
			assertEquals(1, as.size());

		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove last element
		try {
			as.pop();
			assertEquals(0, as.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// add more
		as.push(a);
		as.push(b);
		assertEquals(2, as.size());
		// remove and add and remove and add
		assertEquals("banana", as.pop());
		as.push(c);
		as.push(d);
		assertEquals("dragonfruit", as.pop());
		as.push(e1);
		as.push(f);
		as.push(g);
		assertEquals("grapes", as.pop());

		// remove all
		assertEquals("fig", as.pop());
		assertEquals("eggplant", as.pop());
		assertEquals("cantaloupe", as.pop());
		assertEquals("apple", as.pop());
		// remove from empty list
		try {
			as.pop();
			fail();
		} catch (EmptyStackException e) {
			assertEquals(0, as.size());
		}

	}

	/**
	 * Tests whether isEmpty properly reports if the Stack contains any elements
	 */
	@Test
	public void testIsEmpty() {
		LinkedStack<String> as = new LinkedStack<String>(25);
		String a = "apple";
		String b = "banana";

		// test on newly constructed list
		assertTrue(as.isEmpty());

		// add items - test for empty list
		as.push(a);
		as.push(b);
		assertFalse(as.isEmpty());

		// remove all - test for empty list
		as.pop();
		as.pop();
		assertTrue(as.isEmpty());

	}

	/**
	 * Tests whether size() properly reports the current size of the Stack
	 */
	@Test
	public void testSize() {
		LinkedStack<String> as = new LinkedStack<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";

		// check size when empty
		assertEquals(0, as.size());
		// check size at multiple sizes
		as.push(a);
		assertEquals(1, as.size());
		as.push(b);
		assertEquals(2, as.size());
		as.push(c);
		assertEquals(3, as.size());
		as.push(d);
		assertEquals(4, as.size());

	}

	/**
	 * Tests whether the capacity of the Stack can correctly be set
	 */
	@Test
	public void testSetCapacity() {
		LinkedStack<String> as = new LinkedStack<String>(25);
		LinkedStack<String> as1 = new LinkedStack<String>(25);
		LinkedStack<String> as2 = new LinkedStack<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";

		// change capacity on empty list
		try {
			as2.setCapacity(18);
			assertEquals(18, as2.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// add several elements to both as and as1
		as.push(a);
		as.push(b);
		as.push(c);
		as.push(d);
		as.push(e1);
		as.push(f);

		as1.push(a);
		as1.push(b);
		as1.push(c);
		as1.push(d);
		as1.push(e1);
		as1.push(f);

		// set valid capacity - reduced from original
		try {
			as.setCapacity(18);
			assertEquals(18, as.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// set valid capacity - increased from original
		try {
			as1.setCapacity(55);
			assertEquals(55, as1.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// set capacity to size
		assertEquals(6, as.size());
		try {
			as.setCapacity(6);
			assertEquals(6, as.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// and increase again
		assertEquals(6, as.size());
		try {
			as.setCapacity(8);
			assertEquals(8, as.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// set capacity to less than size
		assertEquals(6, as.size());
		try {
			as.setCapacity(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(8, as.getCapacity());
		}
		// set capacity negative
		try {
			as.setCapacity(-4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(8, as.getCapacity());
		}
	}

}
