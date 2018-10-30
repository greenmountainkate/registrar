package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;

/**
 * Tests the StudentRecordIO class
 * 
 * @author kmbrown
 *
 */
public class StudentRecordIOTest {
	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/** File containing valid Student records */
	private final String validTestFile = "test-files/student_record.txt";
	/** File containing invalid Student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	/** File containing combination of valid and invalid Student records */
	private final String mixedTestFile = "test-files/mixed_valid_and_invalid_student_records.txt";
	/** Non-existent filename */
	private final String fakeTestFile = "test-files/not_a_file.txt";
	/** File containing duplicate Student records */
	private final String duplicatesTestFile = "test-files/student_records_with_duplicates.txt";
	/**
	 * File containing valid Student Records and lines of non Student Record
	 * data
	 */
	private final String errorTestFile = "test-files/nse_exception_student_records.txt";
	/** File containing only numeric information */
	private final String incorrectDataTestFile = "test-files/numeric.txt";
	// /** Read only file used to test writeStudentRecords() */
	// private final String readOnlyFile = "test-files/read_only_file.txt";
	/** Incorrect Permissions file to test on Jenkins */
	private final String noPermissionFile = "/home/sesmith5/actual_student_records.txt";

	/** Counter that increments when a non-existent file is located */
	private int foundNEFile = 0;

	/** Valid Student Record */
	private final String validStudent7 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Valid Student Record */
	private final String validStudent9 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Valid Student Record */
	private final String validStudent5 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Valid Student Record */
	private final String validStudent1 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** Valid Student Record */
	private final String validStudent3 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** Valid Student Record */
	private final String validStudent4 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Valid Student Record */
	private final String validStudent2 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** Valid Student Record */
	private final String validStudent10 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/** Valid Student Record */
	private final String validStudent6 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Valid Student Record */
	private final String validStudent8 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/**
	 * Array that holds valid Student Records as formatted Strings for
	 * comparison purposes
	 */
	private String[] validStudents = { validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
			validStudent6, validStudent7, validStudent8, validStudent9, validStudent10 };

	/**
	 * Establishes requirements for clean test run, including hashing passwords
	 * in validStudents array and resetting foundNEFile counter to zero.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             if password cannot be hashed
	 */
	@Before
	public void setUp() throws NoSuchAlgorithmException {
		// Hashes passwords in ValidStudentArray
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
		foundNEFile = 0;
	}

	/**
	 * Tests ReadStudentRecords() method in StudentRecordIO by checking various
	 * file inputs, including valid files, invalid files, empty files,
	 * nonexistent files
	 * 
	 * @throws IOException
	 *             if file cannot be processed
	 */
	@Test
	public void testReadStudentRecords() throws IOException {

		// test reading valid file
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());

			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}

		// test reading file with duplicates of Student Records that should be
		// disregarded, as well as partial duplicates that should be logged
		try {

			SortedList<Student> students = StudentRecordIO.readStudentRecords(duplicatesTestFile);
			assertEquals(12, students.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + duplicatesTestFile);
		}

		// test reading mixed file containing valid and invalid Student records
		try {

			SortedList<Student> students2 = StudentRecordIO.readStudentRecords(mixedTestFile);
			assertEquals(4, students2.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + mixedTestFile);
		}

		// test reading file containing invalid Student records
		try {

			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + invalidTestFile);
		}

		// test trying to read a non-existent file
		try {

			SortedList<Student> students = StudentRecordIO.readStudentRecords(fakeTestFile);
			assertEquals(0, students.size());

		} catch (FileNotFoundException e) {

			foundNEFile++;
		}
		assertEquals(1, foundNEFile);

		// test reading a file with error lines interspersed with lines
		// containing valid Student records
		try {

			SortedList<Student> students = StudentRecordIO.readStudentRecords(errorTestFile);
			assertEquals(5, students.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + errorTestFile);
		}

		// test reading a file with random numeric data in lieu of Student
		// Records
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(incorrectDataTestFile);
			assertEquals(0, students.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading numeric.txt");
		}
	}

	/**
	 * Tests writing to files using writeStudentRecords() method in
	 * StudentRecordIO. Checks writing to valid and invalid files.
	 */
	@Test
	public void testWriteStudentRecords() {

		// creates SortedList of valid Student records to test write to file
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		students.add(new Student("Cassandra", "Schwartz", "cschwartz", "semper@imperdietornare.co.uk", hashPW, 4));
		students.add(new Student("Shannon", "Hansen", "shansen", "convallis.est.vitae@arcu.ca", hashPW, 14));
		students.add(new Student("Demetrius", "Austin", "daustin", "Curabitur.egestas.nunc@placeratorcilacus.co.uk",
				hashPW, 18));
		students.add(new Student("Raymond", "Brennan", "rbrennan", "litora.torquent@pellentesquemassalobortis.ca",
				hashPW, 12));
		students.add(new Student("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", hashPW, 3));
		students.add(new Student("Lane", "Berg", "lberg", "sociis@non.org", hashPW, 14));
		students.add(new Student("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", hashPW, 17));
		students.add(new Student("Althea", "Hicks", "ahicks", "Phasellus.dapibus@luctusfelis.com", hashPW, 11));
		students.add(new Student("Dylan", "Nolan", "dnolan", "placerat.Cras.dictum@dictum.net", hashPW, 5));

		// Test writing to a valid file and check for correct output
		try {
			StudentRecordIO.writeStudentRecords("test-files/student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}

		checkFiles("test-files/student_records.txt", "test-files/student_records.txt");

		// // Test writing to a read only file
		// foundNEFile = 0;
		//
		// try {
		// StudentRecordIO.writeStudentRecords(readOnlyFile, students);
		// } catch (IOException e) {
		// foundNEFile++;
		// }
		// assertEquals(1, foundNEFile);

		// Test writing to an incorrect permissions file
		foundNEFile = 0;
		try {
			StudentRecordIO.writeStudentRecords(noPermissionFile, students);
		} catch (IOException e) {
			foundNEFile++;
		}
		assertEquals(1, foundNEFile);
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
			Scanner expScanner = new Scanner(new File(expFile));
			Scanner actScanner = new Scanner(new File(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * Tests whether StudentRecordIO class can be instantiated. For purposes of
	 * achieving 100% line coverage on StudentRecordIO class.
	 */
	@Test
	public void testStudentRecordIOObject() {
		StudentRecordIO studentRecordIOObject = new StudentRecordIO();
		assertTrue(studentRecordIOObject instanceof StudentRecordIO);
	}

}
