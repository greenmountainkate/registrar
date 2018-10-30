/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Activity Class, specifically for the checkConflict method of
 * Activity
 * 
 * @author kmbrown
 *
 */
public class ActivityTest {

	/** Activity for testing conflicts */
	Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);
	/** Activity for testing conflicts */
	Activity a2 = new Course("CSC216", "Programming Concepts - Java", "002", 4, "sesmith5", 10, "TH", 1330, 1445);
	/** Activity for testing conflicts */
	Activity a3 = new Course("CSC216", "Programming Concepts - Java", "003", 4, "sesmith5", 10, "MW", 1330, 1445);
	/** Activity for testing conflicts on single day */
	Activity a4 = new Course("CSC216", "Programming Concepts - Java", "004", 4, "sesmith5", 10, "W", 1330, 1445);
	/** Activity for testing conflicts on single minute overlap */
	Activity a5 = new Course("CSC216", "Programming Concepts - Java", "005", 4, "sesmith5", 10, "MW", 1200, 1330);
	/** Activity for testing contained within overlaps */
	Activity a6 = new Course("CSC216", "Programming Concepts - Java", "006", 4, "sesmith5", 10, "MW", 1400, 1420);
	/** Activity for checking partial overlap, multiple minutes */
	Activity a7 = new Course("CSC216", "Programming Concepts - Java", "007", 4, "sesmith5", 10, "MW", 1400, 1520);
	/** Activity for testing time */
	Activity a8 = new Course("CSC216", "Programming Concepts - Java", "007", 4, "sesmith5", 10, "MW", 100, 200);

	/**
	 * Test method for checkConflict() method from Activity class
	 */
	@Test
	public void testCheckConflict() {

		// Tests Activities without conflict
		try {

			a1.checkConflict(a2);

			assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM",
					a2.getMeetingString());

		} catch (ConflictException e) {

			fail();
		}

		// Tests Activities without conflict in other direction
		try {

			a2.checkConflict(a1);

			assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM",
					a2.getMeetingString());

		} catch (ConflictException e) {

			fail();
		}

		// Tests Activities with full overlap, meeting days and meeting times
		try {

			a1.checkConflict(a3);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a3.getMeetingString());

		}

		// Tests Activities with full overlap, meeting days and meeting times in
		// other direction
		try {

			a3.checkConflict(a1);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 1:30PM-2:45PM", a3.getMeetingString());

		}

		// Tests Activities with single day overlap, in both directions
		try {

			a1.checkConflict(a4);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("W 1:30PM-2:45PM", a4.getMeetingString());

		}

		try {

			a4.checkConflict(a1);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("W 1:30PM-2:45PM", a4.getMeetingString());

		}

		// Tests Conflicts with single minute overlap - beginning of Activity
		try {

			a1.checkConflict(a5);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 12:00PM-1:30PM", a5.getMeetingString());

		}
		// Tests Conflicts with multi minute overlap - beginning of Activity
		try {

			a7.checkConflict(a1);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 2:00PM-3:20PM", a7.getMeetingString());

		}

		// Tests Conflicts with single minute overlap - end of Activity
		try {

			a5.checkConflict(a1);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 12:00PM-1:30PM", a5.getMeetingString());

		}
		// Tests Conflicts with multi minute overlap - end of Activity
		try {

			a1.checkConflict(a7);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 2:00PM-3:20PM", a7.getMeetingString());

		}

		// Tests Conflicts where potentially conflicting Activity is contained
		// within Activity
		try {

			a1.checkConflict(a6);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 2:00PM-2:20PM", a6.getMeetingString());

		}

		// Tests Conflicts where Activity is contained within potentially
		// conflicting Activity
		try {

			a6.checkConflict(a1);
			fail();

		} catch (ConflictException e) {

			assertEquals("MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("MW 2:00PM-2:20PM", a6.getMeetingString());

		}
	}

	/**
	 * Test setting various meeting time combinations, valid and invalid
	 * combinations, as well as all clock hours
	 */
	@Test
	public void testSetMeetingTimes() {

		// Test set invalid start time
		try {
			a8.setActivityTime(8900, 400);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(100, a8.getStartTime());
			assertEquals(200, a8.getEndTime());
		}

		// Test set invalid end time
		try {
			a8.setActivityTime(300, 5500);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(100, a8.getStartTime());
			assertEquals(200, a8.getEndTime());
		}

		// Test set invalid start time and end time relationship, start before
		// end
		try {
			a8.setActivityTime(900, 550);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(100, a8.getStartTime());
			assertEquals(200, a8.getEndTime());
		}

		// Test set start and end time relationship, equals
		try {
			a8.setActivityTime(650, 650);

		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(650, a8.getStartTime());
		assertEquals(650, a8.getEndTime());

		// Test change back to varying start and end times
		try {
			a8.setActivityTime(450, 750);

		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(450, a8.getStartTime());
		assertEquals(750, a8.getEndTime());

		// Test various times for validity
		a8.setActivityTime(1620, 1720);
		assertEquals(1620, a8.getStartTime());
		assertEquals(1720, a8.getEndTime());

		a8.setActivityTime(1835, 1948);
		assertEquals(1835, a8.getStartTime());
		assertEquals(1948, a8.getEndTime());

		a8.setActivityTime(2020, 2112);
		assertEquals(2020, a8.getStartTime());
		assertEquals(2112, a8.getEndTime());

		a8.setActivityTime(2222, 2332);
		assertEquals(2222, a8.getStartTime());
		assertEquals(2332, a8.getEndTime());

		a8.setActivityTime(2335, 2359);
		assertEquals(2335, a8.getStartTime());
		assertEquals(2359, a8.getEndTime());

	}

	/**
	 * Checks the output of getMeetingString() for edge cases of clock time,
	 * 12AM, 12PM, and mid points
	 */
	@Test
	public void testGetMeetingString() {
		// Test format for 12AM Activity
		a8.setActivityTime(15, 35);
		assertEquals("MW 12:15AM-12:35AM", a8.getMeetingString());
		// Test format for late morning Activity
		a8.setActivityTime(1135, 1140);
		assertEquals("MW 11:35AM-11:40AM", a8.getMeetingString());
		// Test format for 12PM Activity
		a8.setActivityTime(1214, 1223);
		assertEquals("MW 12:14PM-12:23PM", a8.getMeetingString());
		// Test format for late afternoon Activity
		a8.setActivityTime(1814, 1823);
		assertEquals("MW 6:14PM-6:23PM", a8.getMeetingString());

	}

}
