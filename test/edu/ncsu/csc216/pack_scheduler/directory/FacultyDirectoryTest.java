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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests the fields and methods of the FacultyDirectory 
 * @author kmbrown
 *
 */
public class FacultyDirectoryTest {

	/** Valid faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Non-existent file */
	private final String noSuchFile = "no such file";
	/** Incorrect permission file for Jenkins */
	private final String noPermissionFile = "/home/sesmith5/actual_faculty_records.txt";
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
	private static final int MAX_COURSES = 3;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception
	 *             if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_record.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		// Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		// Test that if there is Faculty in the directory, they
		// are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();

		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);

		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile().
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory fd = new FacultyDirectory();

		// Test valid file
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);

		FacultyDirectory fd1 = new FacultyDirectory();
		// Test invalid file
		try {
			fd1.loadFacultyFromFile(noSuchFile);
		} catch (IllegalArgumentException e) {

			assertEquals(0, fd1.getFacultyDirectory().length);

		}
	}

	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();

		// Test valid Faculty
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);

		FacultyDirectory fd2 = new FacultyDirectory();

		// Test invalid Faculty - empty first name
		try {

			fd2.addFaculty("", LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - null first name
		try {

			fd2.addFaculty(null, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - empty last name
		try {

			fd2.addFaculty(FIRST_NAME, "", ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - null last name
		try {

			fd2.addFaculty(FIRST_NAME, null, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - empty id
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - null id
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - null 1st password
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - null 2nd password
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - null both passwords
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, null, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - empty 1st password
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - empty 2nd password
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - empty both passwords
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", "", MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - mismatched passwords in both directions
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "mistype", PASSWORD, MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "mistype", MAX_COURSES);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - invalid max credits high
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 4);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - invalid max credits zero
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 0);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - invalid max credits negative
		try {

			fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, -42);

		} catch (IllegalArgumentException e) {
			String[][] facultyDirectory2 = fd2.getFacultyDirectory();
			assertEquals(0, facultyDirectory2.length);
		}

		// Test invalid Faculty - duplicate faculty

		fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String[][] facultyDirectory2 = fd2.getFacultyDirectory();
		assertEquals(1, facultyDirectory2.length);

		fd2.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		facultyDirectory2 = fd2.getFacultyDirectory();
		assertEquals(1, facultyDirectory2.length);

		// Test adding multiple Faculty

		fd2.addFaculty("first", "name", "fname", "fname@ncsu.edu", PASSWORD, PASSWORD, 2);
		facultyDirectory2 = fd2.getFacultyDirectory();
		assertEquals(2, facultyDirectory2.length);

	}

	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();

		// Add Faculty and remove
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		assertTrue(fd.removeFaculty("bbrewer"));
		String[][] facultyDirectory = fd.getFacultyDirectory();

		assertEquals(7, facultyDirectory.length);
		assertEquals("Elton", facultyDirectory[4][0]);
		assertEquals("Briggs", facultyDirectory[4][1]);
		assertEquals("ebriggs", facultyDirectory[4][2]);
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();

		// Add a Faculty member
		fd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 1);
		assertEquals(1, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_record.txt");
		checkFiles("test-files/predicted_faculty_record.txt", "test-files/actual_faculty_record.txt");

		// Test saving to an invalid file location
		int failCount = 0;
		// try {
		// fd.saveFacultyDirectory("test-files/read_only_file.txt");
		// } catch (IllegalArgumentException e) {
		//
		// failCount++;
		// }
		// assertEquals(1, failCount);

		// Test saving to file with incorrect permissions

		failCount = 0;
		try {
			fd.saveFacultyDirectory(noPermissionFile);
		} catch (IllegalArgumentException e) {

			failCount++;
		}
		assertEquals(1, failCount);

	}

	/**
	 * Tests the getFacultyFromId() method of the FacultyDirectory class
	 */
	@Test
	public void getFacultyFromIdTest() {
		FacultyDirectory fd = new FacultyDirectory();

		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		fd.addFaculty("Grace", "Hopper", "ghopper", "ghopper@ncsu.edu", "compiler", "compiler", 2);

		assertEquals(2, fd.getFacultyDirectory().length);

		// Check that faculty is correctly retrieved
		Faculty s = fd.getFacultyById("ghopper");

		assertEquals("Grace", s.getFirstName());
		assertEquals("Hopper", s.getLastName());
		assertEquals("ghopper", s.getId());
		assertEquals("ghopper@ncsu.edu", s.getEmail());
		assertEquals(2, s.getMaxCourses());

		// Check that faculty not in directory cannot be retrieved

		s = fd.getFacultyById("notanid");
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
