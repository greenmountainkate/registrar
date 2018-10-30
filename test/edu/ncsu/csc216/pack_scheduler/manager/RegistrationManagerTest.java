package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests the functionality of the RegistrationManager class
 * 
 * @author kmbrown
 *
 */
public class RegistrationManagerTest {

	private RegistrationManager manager;

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception
	 *             if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
		manager.logout();
	}

	/**
	 * Tests whether the current CourseCatalog is correctly retrieved by the
	 * RegistrationManager
	 * 
	 * @throws IllegalArgumentException
	 *             if Course is in invalid format
	 */
	@Test
	public void testGetCourseCatalog() throws IllegalArgumentException {
		CourseCatalog c;

		// test whether CourseCatalog can be retrieved
		c = manager.getCourseCatalog();
		assertFalse(c == null);
		assertTrue(c instanceof CourseCatalog);
		assertEquals(0, c.getCourseCatalog().length);

		// test whether retrieved CourseCatalog can be used
		c.addCourseToCatalog("CSC116", "Intro to Java", "001", 3, "ghopper", 10, "MWF", 1000, 1100);
		assertEquals(1, c.getCourseCatalog().length);

	}

	/**
	 * Tests whether the current StudentDirectory is correctly retrieved by the
	 * RegistrationManager
	 */
	@Test
	public void testGetStudentDirectory() {
		StudentDirectory sd;

		// Test whether StudentDirectory can be retrieved
		sd = manager.getStudentDirectory();
		assertFalse(sd == null);
		assertTrue(sd instanceof StudentDirectory);
		assertEquals(0, sd.getStudentDirectory().length);

		// Test whether retrieved StudentDirectory can be used
		sd.addStudent("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 15);
		assertEquals(1, sd.getStudentDirectory().length);
	}

	/**
	 * Tests whether the FacultyDirectory can be correctly retrieved
	 */
	@Test
	public void testGetFacultyDirectory() {
		FacultyDirectory fd;

		// Test whether the FacultyDirectory can be retrieved
		fd = manager.getFacultyDirectory();
		fd.newFacultyDirectory();
		assertFalse(fd == null);
		assertTrue(fd instanceof FacultyDirectory);
		assertEquals(0, fd.getFacultyDirectory().length);

		// Test whether the retrieved FacultyDirectory can be used
		fd.addFaculty("Prof", "Last", "plast", "plast@ncsu.edu", "teach", "teach", 2);
		assertEquals(1, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests whether various user types can be logged in to the
	 * RegistrationManager system
	 */
	@Test
	public void testLogin() {

		// Test valid registrar login
		assertTrue(manager.login("registrar", "Regi5tr@r"));
		manager.logout();

		// Test invalid registrar login
		try {
			manager.login("registrar", "Not the password");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(manager.getCurrentUser() == null);
		}

		// Test valid student login
		StudentDirectory sd;
		sd = manager.getStudentDirectory();
		sd.addStudent("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 15);
		assertEquals(1, sd.getStudentDirectory().length);

		try {

			assertTrue(manager.login("ghopper", "compiler"));

		} catch (IllegalArgumentException e) {
			fail();
		}

		manager.logout();

		// try logging in with valid user and wrong password
		try {

			manager.login("ghopper", "notthepassword");
			fail();

		} catch (IllegalArgumentException e) {
			assertEquals(null, manager.getCurrentUser());
		}

		manager.logout();

		// Test invalid student login
		try {
			manager.login("bmcclintock", "jumpinggenes");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, manager.getCurrentUser());
		}

		manager.logout();
		
		// Test valid faculty login
		manager.getFacultyDirectory().addFaculty("Sarah", "Smith", "sesmith5", "sesmith5@ncsu.edu", "password", "password", 3);
		try {

			assertTrue(manager.login("sesmith5", "password"));
			
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		manager.logout();
		
		//Test invalid faculty login - wrong password
		try {
			manager.login("sesmith5", "jumpinggenes");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, manager.getCurrentUser());
		}
		

	}

	/**
	 * Tests whether the current user can be logged out of the
	 * RegistrationManager system
	 */
	@Test
	public void testLogout() {

		StudentDirectory sd;
		sd = manager.getStudentDirectory();
		sd.addStudent("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 15);
		assertEquals(1, sd.getStudentDirectory().length);

		try {

			assertTrue(manager.login("ghopper", "compiler"));

		} catch (IllegalArgumentException e) {
			fail();
		}

		manager.logout();
		assertEquals(null, manager.getCurrentUser());
	}

	/**
	 * Tests whether the current User of the RegistrationManager system is
	 * returned properly
	 */
	@Test
	public void testGetCurrentUser() {
		StudentDirectory sd;
		sd = manager.getStudentDirectory();
		sd.addStudent("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 15);
		assertEquals(1, sd.getStudentDirectory().length);

		try {

			assertTrue(manager.login("ghopper", "compiler"));

		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(manager.getCurrentUser() instanceof Student);
		assertEquals("Grace", manager.getCurrentUser().getFirstName());

		manager.logout();

		manager.login("registrar", "Regi5tr@r");

		assertEquals("Wolf", manager.getCurrentUser().getFirstName());

	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		FacultyDirectory fDirectory = manager.getFacultyDirectory();
		fDirectory.loadFacultyFromFile("test-files/matching_faculty.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length);
		assertEquals("CSC226", scheduleFrostArray[0][0]);
		assertEquals("001", scheduleFrostArray[0][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
		assertEquals("9", scheduleFrostArray[0][4]);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("8", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		FacultyDirectory fDirectory = manager.getFacultyDirectory();
		fDirectory.loadFacultyFromFile("test-files/matching_faculty.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		assertFalse(
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(7, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC116", scheduleHicksArray[1][0]);
		assertEquals("003", scheduleHicksArray[1][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);

		assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length);
		assertEquals("CSC116", scheduleHicksArray[0][0]);
		assertEquals("003", scheduleHicksArray[0][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		FacultyDirectory fDirectory = manager.getFacultyDirectory();
		fDirectory.loadFacultyFromFile("test-files/matching_faculty.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests whether Faculty can be added to a Course
	 */
	@Test
	public void testAddFacultyToCourse() {
		manager.getFacultyDirectory().newFacultyDirectory();

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.login("registrar", "Regi5tr@r");
		Faculty faculty = new Faculty("Sarah", "Smith", "sesmith5", "sesmith5@ncsu.edu", "pw", 3);
		Course course = catalog.getCourseFromCatalog("CSC216", "001");
		assertEquals(null, course.getInstructorId());
		// valid add
		try {
			manager.addFacultyToCourse(course, faculty);
			assertEquals(1, faculty.getSchedule().getScheduledCourses().length);
			assertEquals("sesmith5", course.getInstructorId());
		} catch (Exception e) {
			fail();
		}
		
		//invalid add - not registrar
		manager.getFacultyDirectory().addFaculty("John", "Smith", "jsmith5", "jsmith5@ncsu.edu", "pw", "pw", 3);
		manager.logout();
		manager.login("jsmith5", "pw");
		try{
			manager.addFacultyToCourse(course, faculty);
			fail();
		} catch (IllegalArgumentException e){
			assertEquals(1, faculty.getSchedule().getScheduledCourses().length);
		}
		
		manager.logout();

	}
	
	/**
	 * Tests the ability to remove a Faculty Member from a Course
	 */
	@Test
	public void testRemoveFacultyFromCourse(){
		
		manager.getFacultyDirectory().newFacultyDirectory();

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.login("registrar", "Regi5tr@r");
		Faculty faculty = new Faculty("Sarah", "Smith", "sesmith5", "sesmith5@ncsu.edu", "pw", 3);

		Course course = catalog.getCourseFromCatalog("CSC216", "001");
		assertEquals(null, course.getInstructorId());
		
		manager.addFacultyToCourse(course, faculty);
		
		//valid remove
		try{
			manager.removeFacultyFromCourse(course, faculty);
			assertEquals(0, faculty.getSchedule().getScheduledCourses().length);
		} catch (Exception e){
			fail();
		}
		
		//invalid remove - not manager
		//invalid add - not registrar
				manager.getFacultyDirectory().addFaculty("John", "Smith", "jsmith5", "jsmith5@ncsu.edu", "pw", "pw", 3);
				manager.logout();
				manager.login("jsmith5", "pw");
				try{
					manager.removeFacultyFromCourse(course, faculty);
			fail();
				} catch (IllegalArgumentException e){
					assertTrue(manager.getCurrentUser() instanceof Faculty);
				}
				
				manager.logout();
		
	}
	
	/**
	 * Tests the ability to clear the Faculty member's schedule
	 */
	@Test 
	public void testResetFacultySchedule(){
		manager.getFacultyDirectory().newFacultyDirectory();

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.login("registrar", "Regi5tr@r");
		Faculty faculty = new Faculty("Sarah", "Smith", "sesmith5", "sesmith5@ncsu.edu", "password", 3);

		Course course = catalog.getCourseFromCatalog("CSC216", "001");
		assertEquals(null, course.getInstructorId());
		
		manager.addFacultyToCourse(course, faculty);
		assertEquals(1, faculty.getSchedule().getScheduledCourses().length);
		
		try{
			manager.resetFacultySchedule(faculty);
			assertEquals(0, faculty.getSchedule().getScheduledCourses().length);
		} catch (Exception e){
			fail();
		}
		
	}
	
	/**
	 * Resets information and logs out final user
	 */
	@After
	public void tearDown(){
		manager.logout();
		manager.clearData();
		
	}
}