package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the methods and fields of the Faculty class
 * @author kmbrown
 *
 */
public class FacultyTest {
	/** Test Faculty First Name valid */
	public static final String FIRST = "first";
	/** Test Faculty Last Name valid */
	public static final String LAST = "last";
	/** Test Faculty ID valid */
	public static final String ID = "flast";
	/** Test Faculty email valid */
	public static final String EMAIL = "flast@ncsu.edu";
	/** Test Faculty password valid, but not hashed for testing */
	public static final String PASSWORD = "hashPassword";
	/** Test Faculty max courses valid */
	public static final int MAX_COURSES = 2;


	/**
	 * Tests the Faculty constructor on all fields
	 */
	@Test
	public void testFaculty() {

		Faculty faculty = null;
		try {
			faculty = new Faculty(FIRST, LAST, ID, EMAIL, "hashPassword", MAX_COURSES);
		} catch (IllegalArgumentException e) {

			fail("Invalid input to constructor");
		}
		assertEquals("first", faculty.getFirstName());
		assertEquals("last", faculty.getLastName());
		assertEquals("flast", faculty.getId());
		assertEquals("flast@ncsu.edu", faculty.getEmail());
		assertEquals("hashPassword", faculty.getPassword());
		assertEquals(2, faculty.getMaxCourses());

		User facultyNullFirstName = null;
		try {
			facultyNullFirstName = new Faculty(null, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyNullFirstName);
		}

		User facultyESFirstName = null;
		try {
			facultyESFirstName = new Faculty("", LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyESFirstName);
		}

		User facultyNullLastName = null;
		try {
			facultyNullLastName = new Faculty(FIRST, null, ID, EMAIL, PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyNullLastName);
		}

		User facultyESLastName = null;
		try {
			facultyESLastName = new Faculty(FIRST, "", ID, EMAIL, PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyESLastName);
		}

		User facultyNullID = null;
		try {
			facultyNullID = new Faculty(FIRST, LAST, null, EMAIL, PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyNullID);
		}

		User facultyESID = null;
		try {
			facultyESID = new Faculty(FIRST, LAST, "", EMAIL, PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyESID);
		}

		User facultyNullEmail = null;
		try {
			facultyNullEmail = new Faculty(FIRST, LAST, ID, null, PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyNullEmail);
		}

		User facultyESEmail = null;
		try {
			facultyESEmail = new Faculty(FIRST, LAST, ID, "", PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyESEmail);
		}

		User facultyInvalidEmail1 = null;
		try {
			facultyInvalidEmail1 = new Faculty(FIRST, LAST, ID, "not an email", PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyInvalidEmail1);
		}

		User facultyInvalidEmail2 = null;
		try {
			facultyInvalidEmail2 = new Faculty(FIRST, LAST, ID, "email@blahblahDOTcom", PASSWORD, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyInvalidEmail2);
		}

		User facultyNullHPassword = null;
		try {
			facultyNullHPassword = new Faculty(FIRST, LAST, ID, EMAIL, null, MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyNullHPassword);
		}

		User facultyESHPassword = null;
		try {
			facultyESHPassword = new Faculty(FIRST, LAST, ID, EMAIL, "", MAX_COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyESHPassword);
		}

		User facultyShortPassword = null;
		try {
			facultyShortPassword = new Faculty(FIRST, LAST, ID, EMAIL, "ab", MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(facultyShortPassword);
		}

		User facultyInvalidCoursesLow = null;
		try {
			facultyInvalidCoursesLow = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, 0);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyInvalidCoursesLow);
		}

		User facultyInvalidCoursesHigh = null;
		try {
			facultyInvalidCoursesHigh = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, 4);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyInvalidCoursesHigh);
		}

		User facultyInvalidCoursesNegative = null;
		try {
			facultyInvalidCoursesNegative = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, -2);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(facultyInvalidCoursesNegative);
		}

		
	}
	
	/**
	 * Tests the setter for faculty first name. Tests null and empty string
	 * conditions, as well as a valid set
	 */
	@Test
	public void testSetFirstName() {

		// Test valid name change
		User faculty = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		faculty.setFirstName("New");
		assertEquals("New", faculty.getFirstName());

		// Test change to Null
		User faculty1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty1.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST, faculty1.getFirstName());
		}

		// Test change to Empty String
		User faculty2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty2.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST, faculty2.getFirstName());
		}
	}

	/**
	 * Tests the setter for faculty last name. Tests null, empty string, and
	 * valid sets.
	 */
	@Test
	public void testSetLastName() {

		// Test valid name change
		User faculty = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		faculty.setLastName("New");
		assertEquals("New", faculty.getLastName());

		// Test change to Null
		User faculty1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty1.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(LAST, faculty1.getLastName());
		}

		// Test change to Empty String
		User faculty2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty2.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(LAST, faculty2.getLastName());
		}
	}

	/**
	 * Tests the setter for faculty email. Tests null, empty string, valid, and
	 * several invalid sets. Invalid sets include missing characters, incorrect
	 * character combinations, and multiple special character situations.
	 */
	@Test
	public void testSetEmail() {
		// test valid email change
		User faculty = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		faculty.setEmail("newemail@ncsu.edu");
		assertEquals("newemail@ncsu.edu", faculty.getEmail());

		// test change to null string
		User facultyNullEmail = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail(null);
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyNullEmail.getEmail());
		}

		// test change to empty string
		User facultyESEmail = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail("");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyESEmail.getEmail());
		}

		// test change to invalid format (all alphanumeric)
		User facultyInvalidEmail1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail("abcde123ATncsuDOTedu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyInvalidEmail1.getEmail());
		}

		// test change to invalid format (no @)
		User facultyInvalidEmail2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail("abcdefgATncsu.edu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyInvalidEmail2.getEmail());
		}

		// test change to invalid format (no . after @)
		User facultyInvalidEmail3 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail("abcdefg@ncsuDOTedu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyInvalidEmail3.getEmail());
		}

		// test change to invalid format (2 @)
		User facultyInvalidEmail4 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail("@bcdefg@ncsu.edu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyInvalidEmail4.getEmail());
		}

		// test change to invalid format (@ after final .)
		User facultyInvalidEmail5 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			faculty.setEmail("abcdefg.ncsu@edu");
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, facultyInvalidEmail5.getEmail());
		}

	}

	/**
	 * Tests the setter for faculty password. Tests null, empty string,
	 * incorrect length, and valid sets.
	 */
	@Test
	public void testSetPassword() {

		// test valid password update
		User faculty = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		faculty.setPassword("newPassword");
		assertEquals("newPassword", faculty.getPassword());

		// test null password change
		User facultyNullPassword = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			facultyNullPassword.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, facultyNullPassword.getPassword());
		}

		// test empty String password change
		User facultyESPassword = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			facultyESPassword.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, facultyESPassword.getPassword());
		}

		// test password too short change
		User facultyShortPassword = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			facultyShortPassword.setPassword("ab");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, facultyShortPassword.getPassword());
		}

	}

	/**
	 * Tests setter for faculty max courses. Tests valid and invalid sets.
	 * Invalid sets include low, high, zero, and negative value sets.
	 */
	@Test
	public void testSetMaxCourses() {
		// test valid courses update
		Faculty faculty = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		faculty.setMaxCourses(1);
		assertEquals(1, faculty.getMaxCourses());

		// test invalid courses update, low
		Faculty facultyLowCourses = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			facultyLowCourses.setMaxCourses(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(MAX_COURSES, facultyLowCourses.getMaxCourses());
		}

		// test invalid courses update, high
		Faculty facultyHighCourses = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			facultyHighCourses.setMaxCourses(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(MAX_COURSES, facultyHighCourses.getMaxCourses());
		}

		// test invalid courses update, negative
		Faculty facultyNegativeCourses = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		try {
			facultyNegativeCourses.setMaxCourses(-5);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(MAX_COURSES, facultyNegativeCourses.getMaxCourses());
		}

	}

	/**
	 * Tests toString() method of faculty class.
	 */
	@Test
	public void testToString() {
		User faculty = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		String expected = FIRST + "," + LAST + "," + ID + "," + EMAIL + "," + PASSWORD + "," + MAX_COURSES;
		assertEquals(expected, faculty.toString());
	}

	/**
	 * Tests equals() method of faculty class. Compares for equality on every
	 * field, in both directions, as well as for Class and Object equality.
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty("DFirst", LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST, "DLast", ID, EMAIL, PASSWORD, MAX_COURSES);
		User s5 = new Faculty(FIRST, LAST, "DId", EMAIL, PASSWORD, MAX_COURSES);
		User s6 = new Faculty(FIRST, LAST, ID, "Demail@ncsu.edu", PASSWORD, MAX_COURSES);
		User s7 = new Faculty(FIRST, LAST, ID, EMAIL, "DPassword", MAX_COURSES);
		User s8 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, 1);

		// test equals for matching Faculty
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));

		// test for equality on all fields
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
		assertFalse(s1.equals(s8));

		// test for correct Class
		assertTrue(s1.equals(s1));

		// test for incorrect Class
		Object o = new Object();
		assertFalse(s1.equals(o));
	}

	/**
	 * Tests hashcode generation for Faculty class on all fields.
	 */
	@Test
	public void testHashCode() {
		User f1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		User f2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		User f3 = new Faculty("DFirst", LAST, ID, EMAIL, PASSWORD, MAX_COURSES);
		User f4 = new Faculty(FIRST, "DLast", ID, EMAIL, PASSWORD, MAX_COURSES);
		User f5 = new Faculty(FIRST, LAST, "DId", EMAIL, PASSWORD, MAX_COURSES);
		User f6 = new Faculty(FIRST, LAST, ID, "Demail@ncsu.edu", PASSWORD, MAX_COURSES);
		User f7 = new Faculty(FIRST, LAST, ID, EMAIL, "DPassword", MAX_COURSES);
		User f8 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, 3);

		// test hashcodes for matching Faculty
		assertEquals(f1.hashCode(), f2.hashCode());

		// test hashcodes for all other fields
		assertNotEquals(f1.hashCode(), f3.hashCode());
		assertNotEquals(f3.hashCode(), f1.hashCode());
		assertNotEquals(f1.hashCode(), f4.hashCode());
		assertNotEquals(f4.hashCode(), f1.hashCode());
		assertNotEquals(f1.hashCode(), f5.hashCode());
		assertNotEquals(f5.hashCode(), f1.hashCode());
		assertNotEquals(f1.hashCode(), f6.hashCode());
		assertNotEquals(f6.hashCode(), f1.hashCode());
		assertNotEquals(f1.hashCode(), f7.hashCode());
		assertNotEquals(f7.hashCode(), f1.hashCode());
		assertNotEquals(f1.hashCode(), f8.hashCode());
		assertNotEquals(f8.hashCode(), f1.hashCode());
	}

	/**
	 * Tests compareTo() method for Faculty on various combinations of Faculty
	 * ordering
	 */
	@Test
	public void testCompareTo() {
		Faculty f1 = new Faculty("Beth", "Davis", "bdavis", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f2 = new Faculty("Beth", "Davis", "bdavis", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f3 = new Faculty("Beth", "Woodson", "bdavis", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f4 = new Faculty("Liz", "Davis", "bdavis", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f5 = new Faculty("Beth", "Davis", "edavis", EMAIL, PASSWORD, MAX_COURSES);

		// Test that same Faculty returns 0
		assertEquals(0, f1.compareTo(f1));

		// Test that different Faculty with identical info returns 0 in both
		// directions
		assertEquals(0, f1.compareTo(f2));
		assertEquals(0, f2.compareTo(f1));

		// Test that Faculty with prior last name returns negative integer
		assertTrue(f1.compareTo(f3) < 0);

		// Test that Faculty with later last name returns positive integer
		assertTrue(f3.compareTo(f1) > 0);

		// Test that Faculty with same last name and prior first name returns
		// negative integer
		assertTrue(f1.compareTo(f4) < 0);

		// Test that Faculty with same last name and later first name returns
		// positive integer
		assertTrue(f4.compareTo(f1) > 0);

		// Test that Faculty with same last name, same first name, and prior id
		// returns negative integer
		assertTrue(f1.compareTo(f5) < 0);

		// Test that Faculty with same last name, same first name, and later id
		// returns positive integer
		assertTrue(f5.compareTo(f1) > 0);
	}

}
