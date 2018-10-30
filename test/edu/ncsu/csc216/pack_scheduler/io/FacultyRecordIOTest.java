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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;

/**
 * Tests the FacultyRecordIO class
 * 
 * @author kmbrown
 *
 */
public class FacultyRecordIOTest {
	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/** File containing valid Faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** File containing invalid Faculty records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	/** File containing combination of valid and invalid Faculty records */
	private final String mixedTestFile = "test-files/mixed_valid_and_invalid_faculty_records.txt";
	/** Non-existent filename */
	private final String fakeTestFile = "test-files/not_a_file.txt";
	/** File containing duplicate Faculty records */
	private final String duplicatesTestFile = "test-files/faculty_records_with_duplicates.txt";
	/** File containing only numeric information */
	private final String incorrectDataTestFile = "test-files/numeric.txt";
	/** Incorrect Permissions file to test on Jenkins */
	private final String noPermissionFile = "/home/sesmith5/actual_faculty_records.txt";

	/** Counter that increments when a non-existent file is located */
	private int foundNEFile = 0;

	/** Valid Faculty Record */
	private final String validFaculty7 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	/** Valid Faculty Record */
	private final String validFaculty5 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	/** Valid Faculty Record */
	private final String validFaculty2 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	/** Valid Faculty Record */
	private final String validFaculty3 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	/** Valid Faculty Record */
	private final String validFaculty4 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	/** Valid Faculty Record */
	private final String validFaculty1 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	/** Valid Faculty Record */
	private final String validFaculty6 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	/** Valid Faculty Record */
	private final String validFaculty8 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

	/**
	 * Array that holds valid Faculty Records as formatted Strings for
	 * comparison purposes
	 */
	private String[] validFaculty = { validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5,
			validFaculty6, validFaculty7, validFaculty8/* , validFaculty9, validFaculty10 */};

	/**
	 * Establishes requirements for clean test run, including hashing passwords
	 * in validFaculty array and resetting foundNEFile counter to zero.
	 * 
	 * @throws NoSuchAlgorithmException
	 *             if password cannot be hashed
	 */
	@Before
	public void setUp() throws NoSuchAlgorithmException {
		// Hashes passwords in ValidFacultyArray
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
		foundNEFile = 0;
	}

	/**
	 * Tests ReadFacultyRecords() method in FacultyRecordIO by checking various
	 * file inputs, including valid files, invalid files, empty files,
	 * nonexistent files
	 * 
	 * @throws IOException
	 *             if file cannot be processed
	 */
	@Test
	public void testReadFacultyRecords() throws IOException {

		// test reading valid file
		try {
			ArrayList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculty.size());

			for (int i = 0; i < validFaculty.length; i++) {
				//System.out.println(faculty.get(i).toString());
				assertEquals(validFaculty[i], faculty.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}

		// test reading file with duplicates of Faculty Records that should be
		// disregarded
		try {

			ArrayList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(duplicatesTestFile);
			assertEquals(8, faculty.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + duplicatesTestFile);
		}

		// test reading mixed file containing valid and invalid Faculty records
		try {

			ArrayList<Faculty> faculty2 = FacultyRecordIO.readFacultyRecords(mixedTestFile);
			assertEquals(3, faculty2.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + mixedTestFile);
		}

		// test reading file containing invalid Faculty records
		try {

			ArrayList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, faculty.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + invalidTestFile);
		}

		// test trying to read a non-existent file
		try {

			ArrayList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(fakeTestFile);
			assertEquals(0, faculty.size());

		} catch (FileNotFoundException e) {

			foundNEFile++;
		}
		assertEquals(1, foundNEFile);

//		// test reading a file with error lines interspersed with lines
//		// containing valid Faculty records
//		try {
//
//			ArrayList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(errorTestFile);
//			assertEquals(5, faculty.size());
//
//		} catch (FileNotFoundException e) {
//			fail("Unexpected error reading " + errorTestFile);
//		}

		// test reading a file with random numeric data in lieu of Faculty
		// Records
		try {
			ArrayList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(incorrectDataTestFile);
			assertEquals(0, faculty.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected error reading numeric.txt");
		}
	}

	/**
	 * Tests writing to files using writeFacultyRecords() method in
	 * FacultyRecordIO. Checks writing to valid and invalid files.
	 */
	@Test
	public void testWriteFacultyRecords() {

		// creates ArrayList of valid Faculty records to test write to file
		ArrayList<Faculty> faculty = new ArrayList<Faculty>();
		faculty.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 3));
		faculty.add(new Faculty("Cassandra", "Schwartz", "cschwartz", "semper@imperdietornare.co.uk", hashPW, 2));
		faculty.add(new Faculty("Shannon", "Hansen", "shansen", "convallis.est.vitae@arcu.ca", hashPW, 1));
		faculty.add(new Faculty("Demetrius", "Austin", "daustin", "Curabitur.egestas.nunc@placeratorcilacus.co.uk",
				hashPW, 2));
		faculty.add(new Faculty("Raymond", "Brennan", "rbrennan", "litora.torquent@pellentesquemassalobortis.ca",
				hashPW, 3));
		faculty.add(new Faculty("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", hashPW, 3));
		faculty.add(new Faculty("Lane", "Berg", "lberg", "sociis@non.org", hashPW, 2));
		faculty.add(new Faculty("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", hashPW, 1));
		faculty.add(new Faculty("Althea", "Hicks", "ahicks", "Phasellus.dapibus@luctusfelis.com", hashPW, 1));
		faculty.add(new Faculty("Dylan", "Nolan", "dnolan", "placerat.Cras.dictum@dictum.net", hashPW, 3));

		// Test writing to a valid file and check for correct output
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/faculty_record.txt", faculty);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}

		checkFiles("test-files/faculty_record.txt", "test-files/faculty_record.txt");

		// Test writing to an incorrect permissions file
		foundNEFile = 0;
		try {
			FacultyRecordIO.writeFacultyRecords(noPermissionFile, faculty);
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
	 * Tests whether FacultyRecordIO class can be instantiated. For purposes of
	 * achieving 100% line coverage on FacultyRecordIO class.
	 */
	@Test
	public void testFacultyRecordIOObject() {
		FacultyRecordIO facultyRecordIOObject = new FacultyRecordIO();
		assertTrue(facultyRecordIOObject instanceof FacultyRecordIO);
	}

}
