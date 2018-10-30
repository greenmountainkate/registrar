package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 * @author kmbrown
 */
public class StudentDirectoryTest {

	/** Valid student records */
	private final String validTestFile = "test-files/student_record.txt";
	/** Non-existent file */
	private final String noSuchFile = "no such file";
	/** Incorrect permission file for Jenkins */
	private final String noPermissionFile = "/home/sesmith5/actual_student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception
	 *             if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_record.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();

		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		StudentDirectory sd1 = new StudentDirectory();
		// Test invalid file
		try {
			sd1.loadStudentsFromFile(noSuchFile);
		} catch (IllegalArgumentException e) {

			assertEquals(0, sd1.getStudentDirectory().length);

		}
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);

		StudentDirectory sd2 = new StudentDirectory();

		// Test invalid Student - empty first name
		try {

			sd2.addStudent("", LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - null first name
		try {

			sd2.addStudent(null, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - empty last name
		try {

			sd2.addStudent(FIRST_NAME, "", ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - null last name
		try {

			sd2.addStudent(FIRST_NAME, null, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - empty id
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - null id
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - null 1st password
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - null 2nd password
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - null both passwords
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, null, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - empty 1st password
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - empty 2nd password
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - empty both passwords
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "", "", MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - mismatched passwords in both directions
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "mistype", PASSWORD, MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "mistype", MAX_CREDITS);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - invalid max credits high
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 42);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - invalid max credits low
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 2);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - invalid max credits zero
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 0);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - invalid max credits negative
		try {

			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, -42);

		} catch (IllegalArgumentException e) {
			String[][] studentDirectory2 = sd2.getStudentDirectory();
			assertEquals(0, studentDirectory2.length);
		}

		// Test invalid Student - duplicate student

		sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory2 = sd2.getStudentDirectory();
		assertEquals(1, studentDirectory2.length);

		sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		studentDirectory2 = sd2.getStudentDirectory();
		assertEquals(1, studentDirectory2.length);

		// Test adding multiple students

		sd2.addStudent("first", "name", "fname", "fname@ncsu.edu", PASSWORD, PASSWORD, 9);
		studentDirectory2 = sd2.getStudentDirectory();
		assertEquals(2, studentDirectory2.length);

	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String[][] studentDirectory = sd.getStudentDirectory();

		assertEquals(9, studentDirectory.length);
		assertEquals("Zahir", studentDirectory[5][0]);
		assertEquals("King", studentDirectory[5][1]);
		assertEquals("zking", studentDirectory[5][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();

		// Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");

		// Test saving to an invalid file location
		int failCount = 0;
		// try {
		// sd.saveStudentDirectory("test-files/read_only_file.txt");
		// } catch (IllegalArgumentException e) {
		//
		// failCount++;
		// }
		// assertEquals(1, failCount);

		// Test saving to file with incorrect permissions

		failCount = 0;
		try {
			sd.saveStudentDirectory(noPermissionFile);
		} catch (IllegalArgumentException e) {

			failCount++;
		}
		assertEquals(1, failCount);

	}

	/**
	 * Tests the getStudentFromId() method of the StudentDirectory class
	 */
	@Test
	public void getStudentFromIdTest() {
		StudentDirectory sd = new StudentDirectory();

		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		sd.addStudent("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 11);

		assertEquals(2, sd.getStudentDirectory().length);

		// Check that student is correctly retrieved
		Student s = sd.getStudentById("ghopper");

		assertEquals("Grace", s.getFirstName());
		assertEquals("Hopper", s.getLastName());
		assertEquals("ghopper", s.getId());
		assertEquals("ghopper@ncsu.edu", s.getEmail());
		assertEquals(11, s.getMaxCredits());

		// Check that student not in directory cannot be retrieved

		s = sd.getStudentById("notanid");
		assertTrue(s == null);

	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile
	 *            expected output
	 * @param actFile
	 *            actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
