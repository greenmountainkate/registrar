package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests the methods of the LinkedQueue class
 * @author kmbrown
 *
 */
public class LinkedQueueTest {

	/**
	 * Tests the ability to add elements to the end of the Queue
	 */
	@Test
	public void testEnqueue() {

		LinkedQueue<String> as = new LinkedQueue<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";

		// insert single element
		try {
			as.enqueue(a);
			assertEquals(1, as.size());

		} catch (IllegalArgumentException e) {
			fail();
		}
		// insert multiple elements and check size
		try {
			as.enqueue(b);
			as.enqueue(c);
			as.enqueue(d);
			assertEquals(4, as.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove all and make sure can still add again
		try {
			as.dequeue();
			assertEquals(3, as.size());
			as.dequeue();
			assertEquals(2, as.size());
			as.dequeue();
			assertEquals(1, as.size());
			as.dequeue();
			assertEquals(0, as.size());
			// add new element
			as.enqueue(d);
			assertEquals(1, as.size());
			// add removed element again
			as.enqueue(a);
			assertEquals(2, as.size());

		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	/**
	 * Tests the ability to remove and properly return the first element from the Queue
	 */
	@Test
	public void testDequeue() {

		LinkedQueue<String> as = new LinkedQueue<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";
		String g = "grapes";

		// add several elements
		as.enqueue(a);
		as.enqueue(b);
		as.enqueue(c);
		as.enqueue(d);
		as.enqueue(e1);
		as.enqueue(f);
		assertEquals(6, as.size());

		// remove single element
		try {
			as.dequeue();
			assertEquals(5, as.size());
		} catch (IllegalArgumentException e) {
			fail();
		}
		// remove multiple elements
		try {
			as.dequeue();
			assertEquals(4, as.size());
			as.dequeue();
			assertEquals(3, as.size());
			as.dequeue();
			assertEquals(2, as.size());
			as.dequeue();
			assertEquals(1, as.size());

		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove last element
		try {
			as.dequeue();
			assertEquals(0, as.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// add more
		as.enqueue(a);
		as.enqueue(b);
		assertEquals(2, as.size());
		// remove and add and remove and add
		assertEquals("apple", as.dequeue());
		as.enqueue(c);
		as.enqueue(d);
		assertEquals("banana", as.dequeue());
		as.enqueue(e1);
		as.enqueue(f);
		as.enqueue(g);
		assertEquals("cantaloupe", as.dequeue());

		// remove all
		assertEquals("dragonfruit", as.dequeue());
		assertEquals("eggplant", as.dequeue());
		assertEquals("fig", as.dequeue());
		assertEquals("grapes", as.dequeue());
		// remove from empty list
		try {
			as.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(0, as.size());
		}
	}

	/**
	 * Tests whether isEmpty properly returns whether the Queue contains elements
	 */
	@Test
	public void testIsEmpty() {

		LinkedQueue<String> as = new LinkedQueue<String>(25);
		String a = "apple";
		String b = "banana";
		
		// test on newly constructed list
				assertTrue(as.isEmpty());

				// add items - test for empty list
				as.enqueue(a);
				as.enqueue(b);
				assertFalse(as.isEmpty());

				// remove all - test for empty list
				as.dequeue();
				as.dequeue();
				assertTrue(as.isEmpty());
	}

	/**
	 * Tests whether size() correctly returns the current number of elements in the Queue
	 */
	@Test
	public void testSize() {

		LinkedQueue<String> as = new LinkedQueue<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";

		// check size when empty
		assertEquals(0, as.size());
		// check size at multiple sizes
		as.enqueue(a);
		assertEquals(1, as.size());
		as.enqueue(b);
		assertEquals(2, as.size());
		as.enqueue(c);
		assertEquals(3, as.size());
		as.enqueue(d);
		assertEquals(4, as.size());
	}

	/**
	 * Tests the ability to set the capacity of the Queue
	 */
	@Test
	public void testSetCapacity() {
		
		LinkedQueue<String> as = new LinkedQueue<String>(25);
		LinkedQueue<String> as1 = new LinkedQueue<String>(25);
		LinkedQueue<String> as2 = new LinkedQueue<String>(25);
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";

		
		//change capacity on empty list
		try{
			as2.setCapacity(18);
			assertEquals(18, as2.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}
		

		// add several elements to both as and as1
		as.enqueue(a);
		as.enqueue(b);
		as.enqueue(c);
		as.enqueue(d);
		as.enqueue(e1);
		as.enqueue(f);
		
		as1.enqueue(a);
		as1.enqueue(b);
		as1.enqueue(c);
		as1.enqueue(d);
		as1.enqueue(e1);
		as1.enqueue(f);
		
		// set valid capacity - reduced from original
		try{
			as.setCapacity(18);
			assertEquals(18, as.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		//set valid capacity - increased from original
		try{
			as1.setCapacity(55);
			assertEquals(55, as1.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// set capacity to size
		assertEquals(6, as.size());
		try{
			as.setCapacity(6);
			assertEquals(6, as.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// and increase again
		assertEquals(6, as.size());
		try{
			as.setCapacity(8);
			assertEquals(8, as.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// set capacity to less than size
		assertEquals(6, as.size());
		try{
			as.setCapacity(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(8, as.getCapacity());
		}
		// set capacity negative
		try{
			as.setCapacity(-4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(8, as.getCapacity());
		}
	}

}
