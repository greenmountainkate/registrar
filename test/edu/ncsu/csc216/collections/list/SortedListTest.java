package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the SortedList Library
 * 
 * @author kmbrown
 *
 */
public class SortedListTest {

	/**
	 * Tests that SortedList can be initialized and that it is able to be
	 * expanded
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));

		// TODO Test that the list grows by adding at least 11 elements
		// Remember the list's initial capacity is 10
		list.add("apple");
		assertEquals(1, list.size());
		list.add("banana");
		assertEquals(2, list.size());
		list.add("carrot");
		assertEquals(3, list.size());
		list.add("dragonfruit");
		assertEquals(4, list.size());
		list.add("eggplant");
		assertEquals(5, list.size());
		list.add("fig");
		assertEquals(6, list.size());
		list.add("grapefruit");
		assertEquals(7, list.size());
		list.add("honeydew");
		assertEquals(8, list.size());
		list.add("ice fruit");
		assertEquals(9, list.size());
		list.add("jicama");
		assertEquals(10, list.size());
		list.add("kale");
		assertEquals(11, list.size());
	}

	/**
	 * Tests adding elements to SortedList in various locations, as well as
	 * adding null and duplicate elements
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// TODO Test adding to the front, middle and back of the list
		// Test add to front of the list
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		// Test add to end of list
		list.add("kale");
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("kale", list.get(2));

		// Test add to middle of list
		list.add("grapefruit");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("grapefruit", list.get(2));
		assertEquals("kale", list.get(3));

		// Test adding a null element

		String nullElement = null;
		try {

			list.add(nullElement);
			fail();

		} catch (NullPointerException e) {

			assertEquals(4, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("grapefruit", list.get(2));
			assertEquals("kale", list.get(3));
		}

		// TODO Test adding a duplicate element
		try {
			list.add("grapefruit");
		} catch (IllegalArgumentException e) {
			assertEquals(4, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("grapefruit", list.get(2));
			assertEquals("kale", list.get(3));
		}

	}

	/**
	 * Tests the functionality of retrieving items from SortedList, focusing on
	 * potential error and boundary cases
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		try {
			list.get(0);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}

		// Add some elements to the list
		list.add("apple");
		list.add("banana");
		list.add("nectarine");
		assertEquals(3, list.size());

		// Test getting an element at an index < 0
		try {
			list.get(-2);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
		}

		// Test getting an element at size
		try {
			assertEquals("nectarine", list.get(3));
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
		}

		// Test getting last element
		try {
			assertEquals("nectarine", list.get(2));
			assertEquals(3, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

	}

	/**
	 * Tests removing elements from various locations of a SortedList
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}

		// Add some elements to the list - at least 4
		list.add("apple");
		list.add("banana");
		list.add("mango");
		list.add("kiwi");
		list.add("nectarine");
		list.add("apricot");
		list.add("lemon");
		assertEquals(7, list.size());

		// Test removing an element at an index < 0
		try {
			list.remove(-3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(7, list.size());
		}

		// Test removing an element at size
		try {
			list.remove(7);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(7, list.size());
		}

		// TODO Test removing a middle element
		try {
			list.remove(3);
			assertEquals(6, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test removing the last element
		try {
			list.remove(5);
			assertEquals(5, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test removing the first element
		try {
			list.remove(0);
			assertEquals(4, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test removing the last element
		try {
			list.remove(3);
			assertEquals(3, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test remove all of the elements in the list
		try {
			list.remove(2);
			list.remove(1);
			list.remove(0);
			assertEquals(0, list.size());
			assertTrue(list.isEmpty());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

	}

	/**
	 * Tests the indexOf functionality of SortedList
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// TODO Test indexOf on an empty list
		assertEquals(-1, list.indexOf("apple"));

		// TODO Add some elements
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("dragonfruit");
		list.add("eggplant");
		list.add("fig");
		list.add("grapefruit");
		list.add("honeydew");
		assertEquals(8, list.size());

		// TODO Test various calls to indexOf for elements in the list
		// and not in the list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("banana"));
		assertEquals(2, list.indexOf("carrot"));
		assertEquals(3, list.indexOf("dragonfruit"));
		assertEquals(4, list.indexOf("eggplant"));
		assertEquals(5, list.indexOf("fig"));
		assertEquals(6, list.indexOf("grapefruit"));
		assertEquals(7, list.indexOf("honeydew"));

		assertEquals(-1, list.indexOf("watermelon"));
		assertEquals(-1, list.indexOf("raspberry"));

		// TODO Test checking the index of null
		try {
			list.indexOf(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(8, list.size());
		}

	}

	/**
	 * Tests whether SortedList can be emptied correctly
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("dragonfruit");
		list.add("eggplant");
		list.add("fig");
		list.add("grapefruit");
		list.add("honeydew");
		assertEquals(8, list.size());

		// Clear the list
		list.clear();

		// Test that the list is empty
		assertEquals(0, list.size());
	}

	/**
	 * Test to ensure that isEmpty() correctly returns proper value for empty
	 * and not-empty situations
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());

		// Add at least one element
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("dragonfruit");
		list.add("eggplant");
		list.add("fig");
		list.add("grapefruit");
		list.add("honeydew");
		assertEquals(8, list.size());

		// Check that the list is no longer empty
		assertFalse(list.isEmpty());

	}

	/**
	 * Tests the contains() method for SortedList by checking whether the
	 * SortedList contains various specified elements
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// TODO Test the empty list case
		assertFalse(list.contains("apple"));

		// TODO Add some elements
		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("dragonfruit");
		list.add("eggplant");
		list.add("fig");
		list.add("grapefruit");
		list.add("honeydew");
		assertEquals(8, list.size());

		// TODO Test some true and false cases
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("carrot"));
		assertTrue(list.contains("fig"));
		assertTrue(list.contains("banana"));

		assertFalse(list.contains("apples"));
		assertFalse(list.contains("watermelon"));
		assertFalse(list.contains("raspberries"));
		assertFalse(list.contains("12345"));

	}

	/**
	 * Tests SortedLists for equality on all elements and in both directions
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// TODO Make two lists the same and one list different
		list1.add("apple");
		list1.add("banana");
		list1.add("carrot");
		list1.add("dragonfruit");
		list1.add("eggplant");
		list1.add("fig");
		list1.add("grapefruit");
		list1.add("honeydew");
		assertEquals(8, list1.size());

		list2.add("apple");
		list2.add("banana");
		list2.add("carrot");
		list2.add("dragonfruit");
		list2.add("eggplant");
		list2.add("fig");
		list2.add("grapefruit");
		list2.add("honeydew");
		assertEquals(8, list2.size());

		list3.add("watermelon");
		list3.add("apple");
		list3.add("apples");
		list3.add("raspberries");

		// Test for equality in both directions
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));

		// Test for equality on same object
		assertTrue(list1.equals(list1));

		// Test for inequality in both directions
		assertFalse(list1.equals(list3));
		assertFalse(list3.equals(list1));
	}

	/**
	 * Tests for proper hashcode creation for SortedLists
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("apple");
		list1.add("banana");
		list1.add("carrot");
		list1.add("dragonfruit");
		list1.add("eggplant");
		list1.add("fig");
		list1.add("grapefruit");
		list1.add("honeydew");
		assertEquals(8, list1.size());

		list2.add("apple");
		list2.add("banana");
		list2.add("carrot");
		list2.add("dragonfruit");
		list2.add("eggplant");
		list2.add("fig");
		list2.add("grapefruit");
		list2.add("honeydew");
		assertEquals(8, list2.size());

		list3.add("watermelon");
		list3.add("apple");
		list3.add("apples");
		list3.add("raspberries");
		assertEquals(4, list3.size());

		// TODO Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list1.hashCode());
		assertEquals(list1.hashCode(), list2.hashCode());
		assertEquals(list2.hashCode(), list1.hashCode());

		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list3.hashCode(), list1.hashCode());
	}

}
