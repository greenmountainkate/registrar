package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the functionality of the CourseNameValidatorFSM
 * 
 * @author kmbrown
 *
 */
public class CourseNameValidatorFSMTest {

	/**
	 * Test CourseNameValidatorFSM
	 */
	@Test
	public void isValidTest() {
		CourseNameValidatorFSM cnvfsm = new CourseNameValidatorFSM();

		// Test valid 3 letter 3 number no suffix course name
		try {
			assertTrue(cnvfsm.isValid("csc116"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Test valid 4 letter 3 number no suffix course name
		try {
			assertTrue(cnvfsm.isValid("csca116"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Test valid 3 letter 3 number with suffix course name
		try {
			assertTrue(cnvfsm.isValid("csc116a"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Test valid 4 letter 3 number with suffix course name
		try {
			assertTrue(cnvfsm.isValid("csca116a"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Test valid 1 letter 3 number no suffix course name
		try {
			assertTrue(cnvfsm.isValid("c116"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		// Test valid 1 letter 3 number suffix course name
		try {
			assertTrue(cnvfsm.isValid("c116a"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		// Test valid 2 letter 3 number no suffix course name
		try {
			assertTrue(cnvfsm.isValid("cs116"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		// Test valid 2 letter 3 number suffix course name
		try {
			assertTrue(cnvfsm.isValid("cs116a"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		// Test not valid 1st character - numeric
		try {
			assertFalse(cnvfsm.isValid("1csc116"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}

		// Test not valid 1st character - other
		try {
			assertFalse(cnvfsm.isValid(".csc116"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 2nd character - other
		try {
			assertFalse(cnvfsm.isValid("c$c116"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 3rd character - other
		try {
			assertFalse(cnvfsm.isValid("cs@116"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 4th character - other
		try {
			assertFalse(cnvfsm.isValid("csc@116"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 5th character, 4 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csca.16"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 5th character, 4 letter course name - alphabetic
		try {
			assertFalse(cnvfsm.isValid("cscaa116"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}

		// Test nv 6th character, 4 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csca1!6"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 6th character, 4 letter course name - alphabetic
		try {
			assertFalse(cnvfsm.isValid("csca1I6"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}

		// Test nv 7th character, 4 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csca11^"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 7th character, 4 letter course name - alphabetic
		try {
			assertFalse(cnvfsm.isValid("csca11S"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}

		// Test nv suffix, 4 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csca116!"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv suffix, 4 letter course name - numeric
		try {
			assertFalse(cnvfsm.isValid("csca1166"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}

		// Test nv suffix, 4 letter course name - extra letter
		try {
			assertFalse(cnvfsm.isValid("csca116aa"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}

		// Test nv suffix, 4 letter course name - extra number
		try {
			assertFalse(cnvfsm.isValid("csca116a6"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}

		// Test nv suffix, 4 letter course name - extra other
		try {
			assertFalse(cnvfsm.isValid("csca116a@"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 4th character, 3 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csc.16"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 5th character, 3 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csc1!6"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 5th character, 3 letter course name - alphabetic
		try {
			assertFalse(cnvfsm.isValid("csc1I6"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}

		// Test nv 6th character, 3 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csc11^"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv 6th character, 3 letter course name - alphabetic
		try {
			assertFalse(cnvfsm.isValid("csc11S"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}

		// Test nv suffix, 3 letter course name - other
		try {
			assertFalse(cnvfsm.isValid("csc116!"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}

		// Test nv suffix, 3 letter course name - numeric
		try {
			assertFalse(cnvfsm.isValid("csc1166"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}

		// Test nv suffix, 3 letter course name - extra letter
		try {
			assertFalse(cnvfsm.isValid("csc116aa"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}

		// Test nv suffix, 3 letter course name - extra number
		try {
			assertFalse(cnvfsm.isValid("csc116a6"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}

		// Test nv suffix, 3 letter course name - extra other
		try {
			assertFalse(cnvfsm.isValid("csc116a@"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());

		}

		// Test 2 number course name
		try {

			assertFalse(cnvfsm.isValid("csc11"));

		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());

		}

		// Test 1 number course name
		try {

			assertFalse(cnvfsm.isValid("csc1"));

		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());

		}

	}

}
