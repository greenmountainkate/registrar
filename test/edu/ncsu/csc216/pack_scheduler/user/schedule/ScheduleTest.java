package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the fields and methods of the Schedule class
 * 
 * @author kmbrown
 *
 */
public class ScheduleTest {
	/** Valid Course */
	Course a = new Course("CSC216", "Java Programming", "001", 3, "ghopper", 10, "MWF", 1000, 1100);
	/** Valid Course */
	Course b = new Course("CSC226", "Discrete Math", "001", 3, "lpence", 10, "TH", 100, 150);
	/** Valid Course */
	Course c = new Course("CSC230", "C and Software Tools", "001", 3, "bmcclintock", 10, "MWF", 900, 950);
	/** Time conflict Course */
	Course d = new Course("CSC316", "Data Structures", "001", 3, "ghopper", 10, "MWF", 1030, 1145);
	/** Arranged Course */
	Course e1 = new Course("CSC333", "Automata, Grammars, and Computability", "001", 3, "ghopper", 10, "A");

	/**
	 * Tests the null constructor of Schedule objects
	 */
	@Test
	public void scheduleTest() {
		try {
			Schedule sc = new Schedule();
			assertTrue(sc != null);
			assertEquals("My Schedule", sc.getTitle());
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests whether valid Courses can be added to the Schedule and whether
	 * attempts to add invalid Courses (null, duplicate, or overlapping time)
	 * throw correct Exceptions
	 */
	@Test
	public void addTest() {
		Schedule sc = new Schedule();
		assertTrue(sc != null);

		// test valid add
		try {
			assertTrue(sc.addCourseToSchedule(a));
		} catch (IllegalArgumentException e) {
			fail();
		}

		// test add multiple valid Courses
		try {
			assertTrue(sc.addCourseToSchedule(b));
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			assertTrue(sc.addCourseToSchedule(c));
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// test add arranged Course
		try {
			assertTrue(sc.addCourseToSchedule(e1));
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// test invalid - duplicate Course
		try {
			assertFalse(sc.addCourseToSchedule(a));
			fail();
		} catch (IllegalArgumentException e) {
			//add validation here
		}
		
		// test invalid - time conflict Course
		try {
			assertFalse(sc.addCourseToSchedule(d));
			fail();
		} catch (IllegalArgumentException e) {
			//add validation here
		}

	}
	
	/**
	 * Tests removing a Course from the Schedule
	 * 
	 */
	@Test
	public void removeTest(){
		//build schedule
		Schedule sc = new Schedule();
		assertTrue(sc != null);
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		assertEquals(3, sc.getScheduledCourses().length);
		//remove Course
		assertTrue(sc.removeCourseFromSchedule(a));
		assertEquals(2, sc.getScheduledCourses().length);
		
		
		//try to remove Course not on Schedule
		assertFalse(sc.removeCourseFromSchedule(e1));
		assertEquals(2, sc.getScheduledCourses().length);
		
		//remove all Courses
		assertTrue(sc.removeCourseFromSchedule(b));
		assertEquals(1, sc.getScheduledCourses().length);
		assertTrue(sc.removeCourseFromSchedule(c));
		assertEquals(0, sc.getScheduledCourses().length);
		
		//re-add Courses to ensure removing all doesn't break the schedule
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		assertEquals(3, sc.getScheduledCourses().length);
		
	}
	
	/**
	 * Tests resetting a full Schedule to an empty Schedule
	 */
	@Test
	public void resetScheduleTest(){
		
		//build schedule
		Schedule sc = new Schedule();
		assertTrue(sc != null);
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		
		//empty schedule
		assertEquals(3, sc.getScheduledCourses().length);
		sc.resetSchedule();
		assertEquals(0, sc.getScheduledCourses().length);
		
		//re-add to schedule
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		assertEquals(3, sc.getScheduledCourses().length);
		
		
	}
	
	
	/**
	 * Tests the getScheduledCourses function of Schedule
	 */
	@Test
	public void getScheduledCoursesTest(){
		//build schedule
		Schedule sc = new Schedule();
		assertTrue(sc != null);
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		
		//get schedule of Courses
		assertEquals(3, sc.getScheduledCourses().length);
		
		//empty schedule
		sc.resetSchedule();
		
		//get empty schedule
		assertEquals(0, sc.getScheduledCourses().length);
	}
	
	/**
	 * Tests resetting the Title of the Schedule
	 */
	@Test
	public void setTitleTest(){
		//build schedule
		Schedule sc = new Schedule();
		assertTrue(sc != null);
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		
		assertEquals("My Schedule", sc.getTitle());
		//test valid set
		try{
			sc.setTitle("New Schedule Title");
			assertEquals("New Schedule Title", sc.getTitle());
		} catch(IllegalArgumentException e){
			fail();
		}
		//test invalid set - null
		String newTitle = null;
		try{
			sc.setTitle(newTitle);
			fail();
		} catch(IllegalArgumentException e){
			assertEquals("New Schedule Title", sc.getTitle());
		}
		
	}
	
	/**
	 * Tests the accurate retrieval of the number of credits on the schedule
	 */
	@Test
	public void getScheduleCreditsTest(){
		Schedule sc = new Schedule();
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		sc.addCourseToSchedule(c);
		assertEquals(9, sc.getScheduleCredits());
	}
	
	/**
	 * Tests the functionality of the canAdd() method - ability to distinguish duplicate, null, or conflicting Courses
	 */
	@Test
	public void canAddTest(){
		
		Schedule sc = new Schedule();
		sc.addCourseToSchedule(a);
		sc.addCourseToSchedule(b);
		
		//check valid add
		assertTrue(sc.canAdd(c));
		//check invalid add - duplicate
		assertFalse(sc.canAdd(b));
		//check invalid add - null
		Course nullCourse = null;
		assertFalse(sc.canAdd(nullCourse));
		//check invalid add - time conflict
		assertFalse(sc.canAdd(d));
		
	}

}
