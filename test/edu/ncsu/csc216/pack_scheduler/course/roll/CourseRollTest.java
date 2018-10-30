package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the methods of the CourseRoll class
 * 
 * @author kmbrown
 *
 */
public class CourseRollTest {
	/** Student Directory to retrieve Students for testing */
	private StudentDirectory sd;
	/** Course Catalog to retrieve Courses for testing */
	private CourseCatalog catalog;
	/** Instance of RegistrationManager for testing*/
	private RegistrationManager manager;

	/**
	 * Sets up for testing
	 */
	@Before
	public void before() {
		sd = new StudentDirectory();
		sd.loadStudentsFromFile("test-files/student_records.txt");
		sd.addStudent("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 12); // 11
		sd.addStudent("Barbara", "McClintock", "bmcclintock", "bmcclintock@ncsu.edu", "jumpinggenes", "jumpinggenes",
				12); // 12
		sd.addStudent("Dorothy", "Hodgkin", "dhodgkin", "dhodgkin@ncsu.edu", "protein", "protein", 10); // 13
		sd.addStudent("Ada", "Lovelace", "alovelace", "alovelace@ncsu.edu", "compute", "compute", 15); // 14
		sd.addStudent("Sally", "Ride", "sride", "sride@ncsu.edu", "flyhigh", "flyhigh", 15); // 15
		sd.addStudent("Rachel", "Carson", "rcarson", "rcarson@ncsu.edu", "nesting", "nesting", 13); // 16
		sd.addStudent("Jane", "Goodall", "jgoodall", "jgoodall@ncsu.edu", "apes", "apes", 14); // 17
		sd.addStudent("Ruzena", "Bajcsy", "rbajcsy", "rbajcsy@ncsu.edu", "sensors", "sensors", 12); // 18
		sd.addStudent("Elizabeth", "Blackburn", "eblackburn", "eblackburn@ncsu.edu", "telomeres", "telomeres", 11); // 19
		sd.addStudent("Esther", "Conwell", "econwell", "econwell@ncsu.edu", "electric", "electric", 15); // 20
		sd.addStudent("Gretchen", "Daily", "gdaily", "gdaily@ncsu.edu", "biodiversity", "biodiversity", 14); // 21

		manager = RegistrationManager.getInstance();
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/matching_faculty.txt");
		
		catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
	}

	/**
	 * Tests the constructor of CourseRoll objects
	 */
	@Test
	public void testCourseRoll() {
		// Test valid CourseRoll
		try {
			CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 25);
			assertEquals(25, cr.getEnrollmentCap());
			assertEquals(25, cr.getOpenSeats());
		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests the retrieval of the enrollment cap for a particular CourseRoll
	 */
	@Test
	public void testGetEnrollmentCap() {
		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 25);
		assertEquals(25, cr.getEnrollmentCap());
	}

	/**
	 * Tests the ability to set the enrollment cap, both initially and later
	 */
	@Test
	public void testSetEnrollmentCap() {
		// Test initial set
		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 35);
		assertEquals(35, cr.getEnrollmentCap());
		// Test valid reset
		cr.setEnrollmentCap(12);
		assertEquals(12, cr.getEnrollmentCap());
		// Test invalid reset - below mins
		try {
			cr.setEnrollmentCap(6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(12, cr.getEnrollmentCap());
		}
		// Test invalid reset - above max
		try {
			cr.setEnrollmentCap(300);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(12, cr.getEnrollmentCap());
		}

		// Add Students and test invalid reset below current roll.size()
		try {
			cr.setEnrollmentCap(20);
			assertEquals(20, cr.getEnrollmentCap());
			cr.enroll(sd.getStudentById("daustin")); // 1
			cr.enroll(sd.getStudentById("lberg")); // 2
			cr.enroll(sd.getStudentById("rbrennan")); // 3
			cr.enroll(sd.getStudentById("efrost")); // 4
			cr.enroll(sd.getStudentById("shansen")); // 5
			cr.enroll(sd.getStudentById("ahicks")); // 6
			cr.enroll(sd.getStudentById("zking")); // 7
			cr.enroll(sd.getStudentById("dnolan")); // 8
			cr.enroll(sd.getStudentById("cschwartz")); // 9
			cr.enroll(sd.getStudentById("gstone")); // 10
			cr.enroll(sd.getStudentById("ghopper")); // 11
			cr.enroll(sd.getStudentById("bmcclintock")); // 12
			cr.enroll(sd.getStudentById("dhodgkin")); // 13
			cr.enroll(sd.getStudentById("alovelace")); // 14

			cr.setEnrollmentCap(12);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(20, cr.getEnrollmentCap());

		}
	}

	/**
	 * Tests the enroll method of CourseRoll
	 */
	@Test
	public void testEnroll() {
		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 10);
		// test valid enroll
		cr.enroll(sd.getStudentById("daustin")); // 1
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(9, cr.getOpenSeats());
		// test duplicate enroll
		try {
			cr.enroll(sd.getStudentById("daustin"));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(9, cr.getOpenSeats());
		}
		// test enroll null
		Student nullStudent = null;
		try {
			cr.enroll(nullStudent);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(9, cr.getOpenSeats());
		}

		// test enroll - no room in Course, add to Waitlist
		cr.enroll(sd.getStudentById("lberg")); // 2
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(8, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("rbrennan")); // 3
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(7, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("efrost")); // 4
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(6, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("shansen")); // 5
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(5, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("ahicks")); // 6
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(4, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("zking")); // 7
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(3, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("dnolan")); // 8
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(2, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("cschwartz")); // 9
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(1, cr.getOpenSeats());

		cr.enroll(sd.getStudentById("gstone")); // 10
		assertEquals(10, cr.getEnrollmentCap());
		assertEquals(0, cr.getOpenSeats());

		// add first to waitlist
		try {
			cr.enroll(sd.getStudentById("dhodgkin")); // 11
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(1, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		// continue adding to waitlist
		try {
			cr.enroll(sd.getStudentById("ghopper")); // 12
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(2, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("bmcclintock")); // 13
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(3, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("alovelace")); // 14
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(4, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("sride")); // 15
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(5, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("rcarson")); // 16
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(6, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("jgoodall")); // 17
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(7, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("rbajcsy")); // 18
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(8, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("eblackburn")); // 19
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(9, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("econwell")); // 20
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(10, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		// try to add to Courseroll beyond capacity and beyond waitlist capacity
		try {
			cr.enroll(sd.getStudentById("gdaily")); // 21
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(10, cr.getNumberOnWaitlist());

		}

		// test removing Student from full CourseRoll and adding Student from
		// Waitlist
		try {
			cr.drop(sd.getStudentById("efrost"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(9, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		// Continue dropping and replacing with Waitlisted Students
		try {
			cr.drop(sd.getStudentById("daustin"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(8, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("lberg"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(7, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("rbrennan"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(6, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("shansen"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(5, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("ahicks"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(4, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("zking"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(3, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("dnolan"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(2, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("cschwartz"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(1, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.drop(sd.getStudentById("gstone"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(0, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		// drop back to open seats and then re-add to waitlist
		try {
			cr.drop(sd.getStudentById("alovelace"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(1, cr.getOpenSeats());
			assertEquals(0, cr.getNumberOnWaitlist());

		} catch (Exception e) {
			fail();
		}

		try {
			cr.enroll(sd.getStudentById("alovelace"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(0, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		try {
			cr.enroll(sd.getStudentById("gstone"));
			assertEquals(10, cr.getEnrollmentCap());
			assertEquals(0, cr.getOpenSeats());
			assertEquals(1, cr.getNumberOnWaitlist());

		} catch (IllegalArgumentException e) {
			fail();

		}

		// set enrollmentCap greater than than list capacity
		try {
			cr.setEnrollmentCap(25);
			assertEquals(25, cr.getEnrollmentCap());
			assertEquals(15, cr.getOpenSeats());

		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests the ability to remove Students from the CourseRoll
	 */
	@Test
	public void testDrop() {

		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 10);

		// try to drop from empty roll
		try {
			cr.drop(sd.getStudentById("lberg"));
			assertEquals(10, cr.getOpenSeats());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		assertEquals(10, cr.getEnrollmentCap());
		cr.enroll(sd.getStudentById("daustin")); // 1
		cr.enroll(sd.getStudentById("lberg")); // 2
		cr.enroll(sd.getStudentById("rbrennan")); // 3
		cr.enroll(sd.getStudentById("efrost")); // 4
		cr.enroll(sd.getStudentById("shansen")); // 5
		cr.enroll(sd.getStudentById("ahicks")); // 6
		cr.enroll(sd.getStudentById("zking")); // 7
		cr.enroll(sd.getStudentById("dnolan")); // 8
		cr.enroll(sd.getStudentById("cschwartz")); // 9
		cr.enroll(sd.getStudentById("gstone")); // 10
		// valid remove
		try {
			cr.drop(sd.getStudentById("lberg")); // -1
			assertEquals(1, cr.getOpenSeats());
			assertEquals(0, cr.getNumberOnWaitlist());
		} catch (IllegalArgumentException e) {
			fail();
		}

		cr.enroll(sd.getStudentById("ghopper")); // 10
		cr.enroll(sd.getStudentById("bmcclintock")); // 11
		cr.enroll(sd.getStudentById("dhodgkin")); // 12
		cr.enroll(sd.getStudentById("alovelace")); // 13

		assertEquals(0, cr.getOpenSeats());
		assertEquals(3, cr.getNumberOnWaitlist());
		
		// valid remove from waitlist
		try {
			cr.drop(sd.getStudentById("alovelace"));
			assertEquals(0, cr.getOpenSeats());
			assertEquals(2, cr.getNumberOnWaitlist());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// invalid remove - null
		Student nullStudent = null;
		try {
			cr.drop(nullStudent);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, cr.getOpenSeats());
		}

	}

	/**
	 * Tests the ability to correctly retrieve the number of empty seats on a
	 * CourseRoll
	 */
	@Test
	public void testGetOpenSeats() {
		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 20);

		assertEquals(20, cr.getEnrollmentCap());
		assertEquals(20, cr.getOpenSeats());
		cr.enroll(sd.getStudentById("daustin")); // 1
		assertEquals(19, cr.getOpenSeats());
		cr.enroll(sd.getStudentById("lberg")); // 2
		assertEquals(18, cr.getOpenSeats());
		cr.enroll(sd.getStudentById("rbrennan")); // 3
		assertEquals(17, cr.getOpenSeats());

	}

	/**
	 * Tests canEnroll method, which is also tested as helper method for other
	 * tests in this class
	 */
	@Test
	public void testCanEnroll() {
		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 20);
		assertTrue(cr.canEnroll(sd.getStudentById("ghopper")));
		cr.enroll(sd.getStudentById("ghopper"));
		assertFalse(cr.canEnroll(sd.getStudentById("ghopper")));

	}
	
	/**
	 * Tests the ability to accurately retrieve an Array of the Students on the CourseRoll
	 */
	@Test
	public void testGetStudents(){
		
		CourseRoll cr = new CourseRoll(catalog.getCourseFromCatalog("CSC116", "001"), 20);
		cr.enroll(sd.getStudentById("ghopper"));
		cr.enroll(sd.getStudentById("alovelace"));
		cr.enroll(sd.getStudentById("ahicks"));
		cr.enroll(sd.getStudentById("efrost"));
		
		String[][] studentArray = cr.getStudentsOnRoll();
		assertEquals("Grace", studentArray[0][0]);
		
		
	}

	/**
	 * Resets RegistrationManager after tests
	 */
	@After
	public void tearDown(){
		manager.clearData();
	}

}
