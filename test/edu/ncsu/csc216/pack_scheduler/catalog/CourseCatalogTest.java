package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;

/**
 * Tests the CourseCatalog class
 * 
 * @author kmbrown
 *
 */
public class CourseCatalogTest {

	/** Valid course records */
	String validTestFile = "test-files/course_records.txt";
	/** File to test save functionality */
	String saveToFile = "test-files/save_course_catalog.txt";
	/** Non-existent file */
	private final String noSuchFile = "no such file";
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	/** Course enrollment cap */
	private static final int CAP = 150;
	/** Instance of RegistrationManager for testing*/
	RegistrationManager manager;
	
	/**
	 * Makes sure that Courses load properly because FacultyDirectory is loaded properly
	 */
	@Before
	public void setUp(){
		manager = RegistrationManager.getInstance();
		manager.getFacultyDirectory().loadFacultyFromFile("test-files/matching_faculty.txt");
	}

	/**
	 * Tests the Course Catalog null constructor
	 */
	@Test
	public void courseCatalogTest() {

		// Test that CourseCatalog is initialized as an empty list
		CourseCatalog courses = new CourseCatalog();

		assertFalse(courses.removeCourseFromCatalog(NAME, SECTION));
		assertEquals(0, courses.getCourseCatalog().length);
	}
	

	/**
	 * Tests the emptyCourseCatalog() method
	 * 
	 * 
	 * @throws IllegalArgumentException
	 *             if file contains invalid file records
	 */
	@Test
	public void newCourseCatalogTest() throws IllegalArgumentException {

		// Tests that if there are Courses in Catalog, they are emptied after
		// calling newCourseCatalog()
		CourseCatalog courses = new CourseCatalog();
		courses.loadCoursesFromFile(validTestFile);

		assertEquals(8, courses.getCourseCatalog().length);

		courses.newCourseCatalog();

		assertEquals(0, courses.getCourseCatalog().length);

	}

	/**
	 * Tests loadCoursesFromFile() method of CourseCatalog class
	 * 
	 * @throws IllegalArgumentException
	 *             if Course records are invalid in format
	 */
	@Test
	public void loadCoursesFromFileTest() throws IllegalArgumentException {
		RegistrationManager.getInstance().getFacultyDirectory().loadFacultyFromFile("test-files/matching_faculty.txt");
		CourseCatalog courses = new CourseCatalog();

		// tests valid file
		courses.loadCoursesFromFile(validTestFile);
		assertEquals(8, courses.getCourseCatalog().length);

		CourseCatalog courses1 = new CourseCatalog();
		// tests invalid file
		try {
			courses1.loadCoursesFromFile(noSuchFile);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, courses1.getCourseCatalog().length);
		}
	}

	/**
	 * Tests addCourseToCatalog() method of CourseCatalog class
	 * 
	 * @throws IllegalArgumentException
	 *             if course added is in incorrect format
	 */
	@Test
	public void addCourseToCatalogTest() throws IllegalArgumentException {
		CourseCatalog courses = new CourseCatalog();

		// Test add valid Course
		courses.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
				END_TIME);
		String[][] courseCatalog = courses.getCourseCatalog();

		assertEquals(1, courseCatalog.length);
		assertEquals(NAME, courseCatalog[0][0]);
		assertEquals(SECTION, courseCatalog[0][1]);
		assertEquals(TITLE, courseCatalog[0][2]);
		assertEquals(courses.getCourseFromCatalog(NAME, SECTION).getMeetingString(), courseCatalog[0][3]);

		// Test add duplicate course, same name and section
		assertFalse(courses.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS,
				START_TIME, END_TIME));
		courseCatalog = courses.getCourseCatalog();
		assertEquals(1, courseCatalog.length);

		// Test add partial duplicate, same name
		assertTrue(courses.addCourseToCatalog(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
				END_TIME));
		courseCatalog = courses.getCourseCatalog();
		assertEquals(2, courseCatalog.length);

		// Test, add partial duplicate, same section
		assertTrue(courses.addCourseToCatalog("CSC116", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS,
				START_TIME, END_TIME));
		courseCatalog = courses.getCourseCatalog();
		assertEquals(3, courseCatalog.length);

		CourseCatalog c1 = new CourseCatalog();
		// Test add invalid Course - empty name
		try {
			c1.addCourseToCatalog("", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - null name
		try {
			c1.addCourseToCatalog(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
					END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - too long name
		try {
			c1.addCourseToCatalog("Really Long Course Name", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS,
					START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);
		}
		// Test add invalid Course - too short name
		try {
			c1.addCourseToCatalog("a", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);
		}
		// Test add invalid Course - empty title
		try {
			c1.addCourseToCatalog(NAME, "", SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - null title
		try {
			c1.addCourseToCatalog(NAME, null, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - empty section
		try {
			c1.addCourseToCatalog(NAME, TITLE, "", CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - null section
		try {
			c1.addCourseToCatalog(NAME, TITLE, null, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid section, long
		try {
			c1.addCourseToCatalog(NAME, TITLE, "00004", CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
					END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid section, short
		try {
			c1.addCourseToCatalog(NAME, TITLE, "04", CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid credits low
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, 0, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid credits high
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, 6, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - empty instructor id
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, "", CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		
		// Test add invalid Course - empty meeting days
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, "", START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - null meeting days
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, null, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid meeting days
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, "meeting days", START_TIME,
					END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid start time
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, 5000, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
		// Test add invalid Course - invalid end time
		try {
			c1.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, 5000);
			fail();
		} catch (IllegalArgumentException e) {

			String[][] courseCatalog1 = c1.getCourseCatalog();
			assertEquals(0, courseCatalog1.length);

		}
	}

	/**
	 * Test getCourseFromCatalog() method of CourseCatalog
	 * 
	 * @throws IllegalArgumentException
	 *             if Course is in incorrect format
	 */
	@Test
	public void getCourseFromCatalogTest() throws IllegalArgumentException {
		RegistrationManager.getInstance().getFacultyDirectory().loadFacultyFromFile("test-files/matching_faculty.txt");
		CourseCatalog courses = new CourseCatalog();
		courses.loadCoursesFromFile(validTestFile);

		// Attempt to get a course that doesn't exist
		assertNull(courses.getCourseFromCatalog("CSC492", "001"));
		assertNull(courses.getCourseFromCatalog("CSC500", "222"));
		assertNull(courses.getCourseFromCatalog("CSC216", "333"));

		// Attempt to get a course that does exist
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(c, courses.getCourseFromCatalog("CSC216", "001"));
	}

	/**
	 * Tests removing Courses from the CourseCatalog
	 * 
	 * @throws IllegalArgumentException
	 *             if Course is in incorrect format
	 */
	@Test
	public void removeCourseFromCatalogTest() throws IllegalArgumentException {

		
		CourseCatalog courses = new CourseCatalog();
		// Test attempt to remove from empty schedule
		assertFalse(courses.removeCourseFromCatalog(NAME, SECTION));

		// Add Courses for later removal
		assertEquals(0, courses.getCourseCatalog().length);

		assertTrue(courses.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS,
				START_TIME, END_TIME));
		assertTrue(courses.addCourseToCatalog(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
				END_TIME));
		assertTrue(courses.addCourseToCatalog("CSC116", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS,
				START_TIME, END_TIME));
		assertEquals(3, courses.getCourseCatalog().length);

		// Check that removing non-existent Course when Catalog has Courses
		// doesn't break anything
		assertFalse(courses.removeCourseFromCatalog("CSC400", "500"));
		assertEquals(3, courses.getCourseCatalog().length);
		assertFalse(courses.removeCourseFromCatalog("CSC216", "700"));
		assertEquals(3, courses.getCourseCatalog().length);

		// Check that Courses can be removed
		assertTrue(courses.removeCourseFromCatalog(NAME, SECTION));
		assertEquals(2, courses.getCourseCatalog().length);

		assertTrue(courses.removeCourseFromCatalog(NAME, "002"));
		assertEquals(1, courses.getCourseCatalog().length);

		assertTrue(courses.removeCourseFromCatalog("CSC116", SECTION));
		assertEquals(0, courses.getCourseCatalog().length);

		// Test that Courses can be re-added after removing all Courses from
		// Catalog
		assertTrue(courses.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS,
				START_TIME, END_TIME));
		assertEquals(1, courses.getCourseCatalog().length);
	}

	/**
	 * Tests the saveCourseCatalog functionality
	 * 
	 * @throws IllegalArgumentException
	 *             if Course is in incorrect format
	 */
	@Test
	public void testSaveCourseCatalog() throws IllegalArgumentException {
		CourseCatalog courses = new CourseCatalog();
		courses.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
				END_TIME);
		courses.addCourseToCatalog("CSC116", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME,
				END_TIME);
		courses.addCourseToCatalog(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(3, courses.getCourseCatalog().length);

		courses.saveCourseCatalog(saveToFile);

		CourseCatalog coursesFromFile = new CourseCatalog();
		coursesFromFile.loadCoursesFromFile(saveToFile);

		// Compare courses to coursesFromFile for equality
		// assert
	}

	/**
	 * Resets information in RegistrationManager after Tests are complete
	 */
	@After
	public void tearDown(){
		manager.clearData();
				
	}
}
