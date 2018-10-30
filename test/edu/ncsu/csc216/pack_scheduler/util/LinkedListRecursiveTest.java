package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the methods and functionality of the LinkedListRecursive class
 * @author kmbrown
 *
 */
public class LinkedListRecursiveTest {
	
	String a = "apple";
	String b = "banana";
	String c = "cantaloupe";
	String d = "dragonfruit";
	String f = "fig";
	

	/**
	 * Tests the Constructor
	 */
	@Test
	public void testLinkedListRecursive() {
		try {
			LinkedListRecursive<Object> llr = new LinkedListRecursive<Object>();
			assertEquals(0, llr.size());
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests adding an element to the end of the List
	 */
	@Test
	public void testAddE() {
		LinkedListRecursive<String> lR	= new LinkedListRecursive<String>();
		//valid add to empty list
		try{
			lR.add(a);
			assertEquals(1, lR.size());
		} catch(Exception e){
			fail();
		}
		
		//valid add to list with contents
		try{
			lR.add(b);
			lR.add(c);
			lR.add(d);
			assertEquals(4, lR.size());
		} catch(Exception e){
			fail();
		}
		
		//invalid add - duplicate element
		try{
			lR.add(a);
			fail();
		} catch(IllegalArgumentException e){
			assertEquals(4, lR.size());
		}
		
	}

	/**
	 * Tests whether isEmpty() correctly reports the condition of the List
	 */
	@Test
	public void testIsEmpty() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		assertTrue(lR.isEmpty());
		lR.add(a);
		assertFalse(lR.isEmpty());
		lR.remove(a);
		assertTrue(lR.isEmpty());
	}

	/**
	 * Tests the ability to accurately retrieve the current size of the List
	 */
	@Test
	public void testSize() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		assertEquals(0, lR.size());
		lR.add(a);
		assertEquals(1, lR.size());
		lR.add(b);
		assertEquals(2, lR.size());
		lR.add(c);
		assertEquals(3, lR.size());
		lR.add(d);
		assertEquals(4, lR.size());
		lR.remove(a);
		assertEquals(3, lR.size());
		lR.remove(c);
		assertEquals(2, lR.size());
		
	}

	/**
	 * Tests the ability to add using int position and element
	 */
	@Test
	public void testAddIntE() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		//valid add to empty list
		try{
			lR.add(0, a);
			assertEquals(1, lR.size());
			assertEquals(a, lR.get(0));
		} catch(Exception e){
			fail();
		}
		
		//valid add to front
		try{
			lR.add(0, b);
			assertEquals(2, lR.size());
			assertEquals(b, lR.get(0));
			assertEquals(a, lR.get(1));
		} catch(Exception e){
			
			fail();
		}
		
		//valid add to end
		try{
			lR.add(2, c);
			assertEquals(3, lR.size());
			assertEquals(c, lR.get(2));
		} catch(Exception e){
			
			fail();
		}
		
		//valid add to middle
		try{
			lR.add(1, d);
			assertEquals(4, lR.size());
			assertEquals(d, lR.get(1));
		} catch(Exception e){
			fail();
		}
		
		//add to front with longer list
		try{
			lR.add(0, f);
			assertEquals(5, lR.size());
			assertEquals(f, lR.get(0));
		} catch(Exception e){
			fail();
		}
		//invalid add - duplicate
		try{
			lR.add(1, d);
			fail();
		} catch(Exception e){
			assertEquals(5, lR.size());
		}
		
		//invalid add - null
				try{
					lR.add(1, null);
					fail();
				} catch(Exception e){
					assertEquals(5, lR.size());
				}
		
		//invalid add - index low
		try{
			lR.add(-1, d);
			fail();
		} catch(Exception e){
			assertEquals(5, lR.size());
		}
		//invalid add - index greater than size
		try{
			lR.add(7, d);
			fail();
		} catch(Exception e){
			assertEquals(5, lR.size());
		}
	
	}

	/**
	 * Tests whether the get() function correctly retrieves the element at a given position
	 */
	@Test
	public void testGet() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		//invalid get - empty list
		try{
			lR.get(0);
			fail();
		} catch (Exception e){
			assertEquals(0, lR.size());
		}
		//valid get
		lR.add(a);
		assertEquals(a, lR.get(0));
		//invalid get - index low
		try{
			lR.get(-1);
			fail();
		} catch (Exception e){
			assertEquals(1, lR.size());
		}
		//invalid get - index high
		try{
			lR.get(3);
			fail();
		} catch (Exception e){
			assertEquals(1, lR.size());
		}
	}
	
	/**
	 * Tests the ability to remove a given element
	 */
	@Test
	public void testRemoveE() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		
		//try to remove from empty list
		assertFalse(lR.remove(a));
		
		lR.add(a);
		lR.add(b);
		lR.add(c);
		lR.add(d);
		
		//valid remove
		assertTrue(lR.remove(a));
		assertTrue(lR.remove(c));
		
		//invalid remove - not present
		assertFalse(lR.remove(f));
		
		//invalid remove - null
		assertFalse(lR.remove(null));
	}

	/**
	 * Tests the ability to accurately remove an element from a given position and return the removed element
	 */
	@Test
	public void testRemoveInt() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		
		//try to remove from empty list
		try{
			lR.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(0, lR.size());
		}
		
		lR.add(a);
		lR.add(b);
		lR.add(c);
		lR.add(d);
		lR.add(f);
		
		
		//remove from end
		assertEquals(f, lR.remove(4));
		//remove from front
		assertEquals(a, lR.remove(0));
		//remove from middle
		assertEquals(c, lR.remove(1));
		//remove from after point of previous remove
		assertEquals(d, lR.remove(1));
		
		//invalid remove - index low
		try{
			lR.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(1, lR.size());
		}
		//invalid remove - index = size
		try{
			lR.remove(1);
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(1, lR.size());
		}
		
		//invalid remove - index high
		try{
			lR.remove(5);
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(1, lR.size());
		}
		
	}

	/**
	 * Tests the ability of the set() method to accurately replace the value at a given position with a new value
	 */
	@Test
	public void testSet() {
		LinkedListRecursive<String> lR = new LinkedListRecursive<String>();
		lR.add(a);
		lR.add(b);
		lR.add(c);
		
		//valid set
		assertEquals(b, lR.set(1, d));
		
		//invalid set - index low
		try{
			lR.set(-1, f);
		} catch(IndexOutOfBoundsException e){
			assertEquals(3, lR.size());
		}
		//invalid set - index high
		try{
			lR.set(4, f);
		} catch(IndexOutOfBoundsException e){
			assertEquals(3, lR.size());
		}
		
	}

//	@Test
//	public void testContains() {
//		fail("Not yet implemented");
//	}

}
