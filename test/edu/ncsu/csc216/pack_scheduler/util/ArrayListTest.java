package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the functionality of the custom ArrayList class
 * @author kmbrown
 *
 */
public class ArrayListTest {

	/**
	 * Tests ArrayList null constructor, which should default to creating an ArrayList that is empty, with a capacity of 10
	 */
	@Test
	public void arrayListConstructorTest() {
		try {
			ArrayList<Object> as = new ArrayList<Object>();
			assertEquals(0, as.size());
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests the adding of Elements to the ArrayList.  Must be within a valid index and must not be a null element
	 */
	@Test
	public void addTest() {
		ArrayList<String> as1 = new ArrayList<String>();
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";
		String g = "grapes";
		String h = "huckleberries";
		String i = "iceberg lettuce";
		String j = "jalepeno";
		String k = "kale";
		String l = "lemon";

		// add to beginning of list
		try {
			as1.add(0, a);
			assertEquals(1, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// add to end of list
		try {
			as1.add(1, b);
			assertEquals(2, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// add to beginning of list
		try {
			as1.add(0, c);
			assertEquals(3, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		// add to middle of list
		try {
			as1.add(1, d);
			assertEquals(4, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
		// add past current capacity of list
		try {
			as1.add(0, e1);
			assertEquals(5, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			as1.add(0, f);
			assertEquals(6, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			as1.add(1, g);
			assertEquals(7, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			as1.add(4, h);
			assertEquals(8, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			as1.add(5, i);
			assertEquals(9, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			as1.add(1, j);
			assertEquals(10, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			as1.add(7, k);
			assertEquals(11, as1.size());
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// add duplicate to list
		try {
			as1.add(1, b);
			fail();
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(11, as1.size());
		}

		// add to negative index
		try {
			as1.add(-1, l);
			fail();
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(11, as1.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// add to index position greater than list size
		try {
			as1.add(14, l);
			fail();
		} catch (NullPointerException e) {
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(11, as1.size());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		//test add null element
		try {
			String n = null;
			as1.add(0, n);
			fail();
		} catch (NullPointerException e) {
			assertEquals(11, as1.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests remove() functionality of ArrayList
	 */
	@Test
	public void removeTest() {
		ArrayList<String> as = new ArrayList<String>();
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";
		String g = "grapes";

		// build list
		as.add(0, a);
		as.add(0, b);
		as.add(0, c);
		as.add(0, d);
		as.add(0, e1);
		as.add(0, f);
		as.add(0, g);
		assertEquals(7, as.size());

		// test remove from end of list
		try {
			Object o = as.remove(6);
			assertEquals("apple", o);
			assertEquals(6, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}
		// test remove from beginning of list
		try {
			Object o = as.remove(0);
			assertEquals("grapes", o);
			assertEquals(5, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}

		// test remove from middle of list
		try {
			Object o = as.remove(2);
			assertEquals("dragonfruit", o);
			assertEquals(4, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}

		// remove all items from list
		try {
			as.remove(0);
			assertEquals(3, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}

		try {
			as.remove(0);
			assertEquals(2, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}

		try {
			as.remove(0);
			assertEquals(1, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}

		try {
			as.remove(0);
			assertEquals(0, as.size());
			
		} catch (NullPointerException e) {
			fail();
		}

		// try to remove from empty list
		try {
			as.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, as.size());
		}
		// add new items
		as.add(0, a);
		assertEquals("apple", as.get(0));
		as.add(0, b);
		assertEquals("banana", as.get(0));
		assertEquals("apple", as.get(1));
		as.add(0, c);
		assertEquals("cantaloupe", as.get(0));
		assertEquals("banana", as.get(1));
		assertEquals("apple", as.get(2));

		// remove an item
		as.remove(1);
		assertEquals("cantaloupe", as.get(0));
		assertEquals("apple", as.get(1));
		

		// test remove item beyond index, low boundary
		try {
			as.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, as.size());
		}

		// test remove item beyond index, way low
		try {
			as.remove(-16);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, as.size());
		}

		// test remove item beyond index, high boundary
		try {
			as.remove(2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, as.size());
		}
		// test remove item beyond index, way high
		try {
			as.remove(12);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, as.size());
		}

	}

	
	/**
	 * Tests functionality of set() in ArrayList
	 */
	@Test
	public void setTest(){
		
		ArrayList<String> as = new ArrayList<String>();
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		String d = "dragonfruit";
		String e1 = "eggplant";
		String f = "fig";
		String g = "grapes";
		String h = "huckleberries";
		String j = "jalepeno";
		String k = "kale";
		

		// build list
		as.add(0, a);
		as.add(0, b);
		as.add(0, c);
		as.add(0, d);
		as.add(0, e1);
		as.add(0, f);
		assertEquals(6, as.size());
		

		//test valid set - first element of list
		try{
			assertEquals("fig", as.get(0));
			as.set(0, g);
			assertEquals(6, as.size());
			assertEquals("grapes", as.get(0));
			assertEquals("eggplant", as.get(1));
		} catch(IllegalArgumentException e){
			fail();
		}
		
		//test valid set - last element of list
		try{
			assertEquals("apple", as.get(5));
			as.set(5, h);
			assertEquals(6, as.size());
			assertEquals("huckleberries", as.get(5));
			assertEquals("banana", as.get(4));
			
		} catch(IllegalArgumentException e){
			fail();
		}
		
		//test valid set - middle element of list
		try{
			assertEquals("cantaloupe", as.get(3));
			as.set(3, j);
			assertEquals(6, as.size());
			assertEquals("jalepeno", as.get(3));
			assertEquals("banana", as.get(4));
			
		} catch(IllegalArgumentException e){
			fail();
		}
		//test invalid set - adding to end of list
		try{
			as.set(6, k);
			fail();
		} catch(IndexOutOfBoundsException e){
			assertEquals(6, as.size());
			
		}
		
		//test null set
		String n = null;
		try{
			as.set(0, n);
			fail();
		} catch(NullPointerException e){
			assertEquals(6, as.size());
			
			assertEquals("grapes", as.get(0));
		}
		//test duplicate set
		try{
			as.set(1, g);
			fail();
		} catch(IllegalArgumentException e){
			assertEquals(6, as.size());
			
			assertEquals("grapes", as.get(0));
			assertEquals("eggplant", as.get(1));		
		}
		
		
		//test set beyond range, low
		try{
			as.set(-6, k);
			fail();
		} catch(IndexOutOfBoundsException e){
			assertEquals(6, as.size());
			
		}
		
		//reset element that was previously replaced
		try{
			as.set(5, f);
			assertEquals("fig", as.get(5));
			assertEquals(6, as.size());
			
		} catch(IllegalArgumentException e){
			fail();
		}
	}
	
	/**
	 * Test get() functionality of ArrayList
	 */
	@Test
	public void getTest(){
		ArrayList<String> as = new ArrayList<String>();
		String a = "apple";
		String b = "banana";
		String c = "cantaloupe";
		

		// build list
		as.add(0, a);
		as.add(0, b);
		as.add(0, c);
		
		assertEquals(3, as.size());
		
		//test get valid index, not null
		assertEquals("cantaloupe", as.get(0));
		assertEquals("banana", as.get(1));
		assertEquals("apple", as.get(2));
		
		
		//test invalid index - low
		try{
			as.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(3, as.size());
		}
		

		//test invalid index - high
		try{
			as.get(3);
			fail();
		} catch (IndexOutOfBoundsException e){
			assertEquals(3, as.size());
		}
	}
}
