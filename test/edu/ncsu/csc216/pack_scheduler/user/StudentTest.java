package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests Student class by constructing Students and calling setter methods.
 * Getters are tested indirectly. setID() method is tested only through calling
 * the constructor, as there is no situation in which a Student ID would be
 * changed after it is initially defined.
 * 
 * @author kmbrown
 *
 */
public class StudentTest {

	/** Test Student First Name valid */
	public static final String FIRST = "first";
	/** Test Student Last Name valid */
	public static final String LAST = "last";
	/** Test Student ID valid */
	public static final String ID = "flast";
	/** Test Student email valid */
	public static final String EMAIL = "flast@ncsu.edu";
	/** Test Student password valid, but not hashed for testing */
	public static final String PASSWORD = "hashPassword";
	/** Test Student max credits valid */
	public static final int CREDITS = 9;

	/**
	 * Tests the Student constructor which does not accept a parameter for max
	 * credits
	 */
	@Test
	public void testStudentStringStringStringStringString() {

		// test valid student
		Student student = null;
		try {
			student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD);
		} catch (IllegalArgumentException e) {

			fail("Invalid input to constructor");
		}
		assertEquals("first", student.getFirstName());
		assertEquals("last", student.getLastName());
		assertEquals("flast", student.getId());
		assertEquals("flast@ncsu.edu", student.getEmail());
		assertEquals("hashPassword", student.getPassword());
		assertEquals(18, student.getMaxCredits());

		User studentNullFirstName = null;
		try {
			studentNullFirstName = new Student(null, LAST, ID, EMAIL, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullFirstName);
		}

		User studentESFirstName = null;
		try {
			studentESFirstName = new Student("", LAST, ID, EMAIL, PASSWORD);
			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESFirstName);
		}

		User studentNullLastName = null;
		try {
			studentNullLastName = new Student(FIRST, null, ID, EMAIL, PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullLastName);
		}

		User studentESLastName = null;
		try {
			studentESLastName = new Student(FIRST, "", ID, EMAIL, PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESLastName);
		}

		User studentNullID = null;
		try {
			studentNullID = new Student(FIRST, LAST, null, EMAIL, PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullID);
		}

		User studentESID = null;
		try {
			studentESID = new Student(FIRST, LAST, "", EMAIL, PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESID);
		}

		User studentNullEmail = null;
		try {
			studentNullEmail = new Student(FIRST, LAST, ID, null, PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullEmail);
		}

		User studentESEmail = null;
		try {
			studentESEmail = new Student(FIRST, LAST, ID, "", PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESEmail);
		}

		User studentInvalidEmail1 = null;
		try {
			studentInvalidEmail1 = new Student(FIRST, LAST, ID, "not an email", PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidEmail1);
		}

		User studentInvalidEmail2 = null;
		try {
			studentInvalidEmail2 = new Student(FIRST, LAST, ID, "email@blahblahDOTcom", PASSWORD);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidEmail2);
		}

		User studentNullHPassword = null;
		try {
			studentNullHPassword = new Student(FIRST, LAST, ID, EMAIL, null);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullHPassword);
		}

		User studentESHPassword = null;
		try {
			studentESHPassword = new Student(FIRST, LAST, ID, EMAIL, "");

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESHPassword);
		}

		User studentShortPassword = null;
		try {
			studentShortPassword = new Student(FIRST, LAST, ID, EMAIL, "ab");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(studentShortPassword);
		}
	}

	/**
	 * Tests the Student constructor on all fields
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {

		Student student = null;
		try {
			student = new Student(FIRST, LAST, ID, EMAIL, "hashPassword", CREDITS);
		} catch (IllegalArgumentException e) {

			fail("Invalid input to constructor");
		}
		assertEquals("first", student.getFirstName());
		assertEquals("last", student.getLastName());
		assertEquals("flast", student.getId());
		assertEquals("flast@ncsu.edu", student.getEmail());
		assertEquals("hashPassword", student.getPassword());
		assertEquals(9, student.getMaxCredits());

		User studentNullFirstName = null;
		try {
			studentNullFirstName = new Student(null, LAST, ID, EMAIL, PASSWORD, CREDITS);
			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullFirstName);
		}

		User studentESFirstName = null;
		try {
			studentESFirstName = new Student("", LAST, ID, EMAIL, PASSWORD, CREDITS);
			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESFirstName);
		}

		User studentNullLastName = null;
		try {
			studentNullLastName = new Student(FIRST, null, ID, EMAIL, PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullLastName);
		}

		User studentESLastName = null;
		try {
			studentESLastName = new Student(FIRST, "", ID, EMAIL, PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESLastName);
		}

		User studentNullID = null;
		try {
			studentNullID = new Student(FIRST, LAST, null, EMAIL, PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullID);
		}

		User studentESID = null;
		try {
			studentESID = new Student(FIRST, LAST, "", EMAIL, PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESID);
		}

		User studentNullEmail = null;
		try {
			studentNullEmail = new Student(FIRST, LAST, ID, null, PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullEmail);
		}

		User studentESEmail = null;
		try {
			studentESEmail = new Student(FIRST, LAST, ID, "", PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESEmail);
		}

		User studentInvalidEmail1 = null;
		try {
			studentInvalidEmail1 = new Student(FIRST, LAST, ID, "not an email", PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidEmail1);
		}

		User studentInvalidEmail2 = null;
		try {
			studentInvalidEmail2 = new Student(FIRST, LAST, ID, "email@blahblahDOTcom", PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidEmail2);
		}

		User studentNullHPassword = null;
		try {
			studentNullHPassword = new Student(FIRST, LAST, ID, EMAIL, null, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentNullHPassword);
		}

		User studentESHPassword = null;
		try {
			studentESHPassword = new Student(FIRST, LAST, ID, EMAIL, "", CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentESHPassword);
		}

		User studentShortPassword = null;
		try {
			studentShortPassword = new Student(FIRST, LAST, ID, EMAIL, "ab", CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(studentShortPassword);
		}

		User studentInvalidCreditsLow = null;
		try {
			studentInvalidCreditsLow = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, 2);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidCreditsLow);
		}

		User studentInvalidCreditsHigh = null;
		try {
			studentInvalidCreditsHigh = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, 25);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidCreditsHigh);
		}

		User studentInvalidCreditsNegative = null;
		try {
			studentInvalidCreditsNegative = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, -2);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidCreditsNegative);
		}

		User studentInvalidCreditsZero = null;
		try {
			studentInvalidCreditsZero = new Student("first", LAST, ID, EMAIL, PASSWORD, 0);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(studentInvalidCreditsZero);
		}
	}

	/**
	 * Tests the setter for Student first name. Tests null and empty string
	 * conditions, as well as a valid set
	 */
	@Test
	public void testSetFirstName() {

		// Test valid name change
		User student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		student.setFirstName("New");
		assertEquals("New", student.getFirstName());

		// Test change to Null
		User student1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student1.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST, student1.getFirstName());
		}

		// Test change to Empty String
		User student2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student2.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST, student2.getFirstName());
		}
	}

	/**
	 * Tests the setter for Student last name. Tests null, empty string, and
	 * valid sets.
	 */
	@Test
	public void testSetLastName() {

		// Test valid name change
		User student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		student.setLastName("New");
		assertEquals("New", student.getLastName());

		// Test change to Null
		User student1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student1.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(LAST, student1.getLastName());
		}

		// Test change to Empty String
		User student2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student2.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(LAST, student2.getLastName());
		}
	}

	/**
	 * Tests the setter for Student email. Tests null, empty string, valid, and
	 * several invalid sets. Invalid sets include missing characters, incorrect
	 * character combinations, and multiple special character situations.
	 */
	@Test
	public void testSetEmail() {
		// test valid email change
		User student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		student.setEmail("newemail@ncsu.edu");
		assertEquals("newemail@ncsu.edu", student.getEmail());

		// test change to null string
		User studentNullEmail = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail(null);
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentNullEmail.getEmail());
		}

		// test change to empty string
		User studentESEmail = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail("");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentESEmail.getEmail());
		}

		// test change to invalid format (all alphanumeric)
		User studentInvalidEmail1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail("abcde123ATncsuDOTedu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentInvalidEmail1.getEmail());
		}

		// test change to invalid format (no @)
		User studentInvalidEmail2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail("abcdefgATncsu.edu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentInvalidEmail2.getEmail());
		}

		// test change to invalid format (no . after @)
		User studentInvalidEmail3 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail("abcdefg@ncsuDOTedu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentInvalidEmail3.getEmail());
		}

		// test change to invalid format (2 @)
		User studentInvalidEmail4 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail("@bcdefg@ncsu.edu");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentInvalidEmail4.getEmail());
		}

		// test change to invalid format (@ after final .)
		User studentInvalidEmail5 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			student.setEmail("abcdefg.ncsu@edu");
		} catch (IllegalArgumentException e) {
			assertEquals(EMAIL, studentInvalidEmail5.getEmail());
		}

	}

	/**
	 * Tests the setter for Student password. Tests null, empty string,
	 * incorrect length, and valid sets.
	 */
	@Test
	public void testSetPassword() {

		// test valid password update
		User student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		student.setPassword("newPassword");
		assertEquals("newPassword", student.getPassword());

		// test null password change
		User studentNullPassword = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentNullPassword.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, studentNullPassword.getPassword());
		}

		// test empty String password change
		User studentESPassword = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentESPassword.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, studentESPassword.getPassword());
		}

		// test password too short change
		User studentShortPassword = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentShortPassword.setPassword("ab");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(PASSWORD, studentShortPassword.getPassword());
		}

	}

	/**
	 * Tests setter for Student max credits. Tests valid and invalid sets.
	 * Invalid sets include low, high, zero, and negative value sets.
	 */
	@Test
	public void testSetMaxCredits() {
		// test valid credit update
		Student student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		student.setMaxCredits(7);
		assertEquals(7, student.getMaxCredits());

		// test invalid credit update, low
		Student studentLowCredits = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentLowCredits.setMaxCredits(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(CREDITS, studentLowCredits.getMaxCredits());
		}

		// test invalid credit update, high
		Student studentHighCredits = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentHighCredits.setMaxCredits(45);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(CREDITS, studentHighCredits.getMaxCredits());
		}

		// test invalid credit update, zero
		Student studentZeroCredits = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentZeroCredits.setMaxCredits(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(CREDITS, studentZeroCredits.getMaxCredits());
		}

		// test invalid credit update, negative
		Student studentNegativeCredits = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		try {
			studentNegativeCredits.setMaxCredits(-5);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(CREDITS, studentNegativeCredits.getMaxCredits());
		}

		// //test invalid credit update, non int (alphabetic)
		// Student studentAlphabetCredits = new Student(FIRST, LAST, ID, EMAIL,
		// PASSWORD, CREDITS);
		// try{
		// studentAlphabetCredits.setMaxCredits(A);
		// fail;
		// }
		// //test invalid credit update, non int (character)
		// //test invalid credit update, int plus alphabetic
		// //test invalid credit update, int plus character
	}

	/**
	 * Tests toString() method of Student class.
	 */
	@Test
	public void testToString() {
		User student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		String expected = FIRST + "," + LAST + "," + ID + "," + EMAIL + "," + PASSWORD + "," + CREDITS;
		assertEquals(expected, student.toString());
	}

	/**
	 * Tests equals() method of Student class. Compares for equality on every
	 * field, in both directions, as well as for Class and Object equality.
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s3 = new Student("DFirst", LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s4 = new Student(FIRST, "DLast", ID, EMAIL, PASSWORD, CREDITS);
		User s5 = new Student(FIRST, LAST, "DId", EMAIL, PASSWORD, CREDITS);
		User s6 = new Student(FIRST, LAST, ID, "Demail@ncsu.edu", PASSWORD, CREDITS);
		User s7 = new Student(FIRST, LAST, ID, EMAIL, "DPassword", CREDITS);
		User s8 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, 5);

		// test equals for matching Students
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
	 * Tests hashcode generation for Student class on all fields.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s3 = new Student("DFirst", LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s4 = new Student(FIRST, "DLast", ID, EMAIL, PASSWORD, CREDITS);
		User s5 = new Student(FIRST, LAST, "DId", EMAIL, PASSWORD, CREDITS);
		User s6 = new Student(FIRST, LAST, ID, "Demail@ncsu.edu", PASSWORD, CREDITS);
		User s7 = new Student(FIRST, LAST, ID, EMAIL, "DPassword", CREDITS);
		User s8 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, 5);

		// test hashcodes for matching Students
		assertEquals(s1.hashCode(), s2.hashCode());

		// test hashcodes for all other fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s3.hashCode(), s1.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s4.hashCode(), s1.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s5.hashCode(), s1.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s6.hashCode(), s1.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s7.hashCode(), s1.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());
		assertNotEquals(s8.hashCode(), s1.hashCode());
	}

	/**
	 * Tests compareTo() method for Student on various combinations of Student
	 * ordering
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student("Beth", "Davis", "bdavis", EMAIL, PASSWORD, CREDITS);
		Student s2 = new Student("Beth", "Davis", "bdavis", EMAIL, PASSWORD, CREDITS);
		Student s3 = new Student("Beth", "Woodson", "bdavis", EMAIL, PASSWORD, CREDITS);
		Student s4 = new Student("Liz", "Davis", "bdavis", EMAIL, PASSWORD, CREDITS);
		Student s5 = new Student("Beth", "Davis", "edavis", EMAIL, PASSWORD, CREDITS);

		// Test that same student returns 0
		assertEquals(0, s1.compareTo(s1));

		// Test that different student with identical info returns 0 in both
		// directions
		assertEquals(0, s1.compareTo(s2));
		assertEquals(0, s2.compareTo(s1));

		// Test that student with prior last name returns negative integer
		assertTrue(s1.compareTo(s3) < 0);

		// Test that student with later last name returns positive integer
		assertTrue(s3.compareTo(s1) > 0);

		// Test that student with same last name and prior first name returns
		// negative integer
		assertTrue(s1.compareTo(s4) < 0);

		// Test that student with same last name and later first name returns
		// positive integer
		assertTrue(s4.compareTo(s1) > 0);

		// Test that student with same last name, same first name, and prior id
		// returns negative integer
		assertTrue(s1.compareTo(s5) < 0);

		// Test that student with same last name, same first name, and later id
		// returns positive integer
		assertTrue(s5.compareTo(s1) > 0);
	}

	/**
	 * Tests canAdd method, checks valid and invalid potential Course additions
	 * to Student's Schedule
	 */
	@Test
	public void canAddTest() {
		Student student = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, 5);

		Course a = new Course("CSC216", "Java Programming", "001", 3, "ghopper", 10, "MWF", 1000, 1100);
		Course b = new Course("CSC226", "Discrete Math", "001", 3, "lpence", 10, "TH", 100, 150);
		// test valid add
		assertTrue(student.canAdd(a));
		student.getSchedule().addCourseToSchedule(a);
		// test invalid add - over max credits
		assertFalse(student.canAdd(b));
	}

}
