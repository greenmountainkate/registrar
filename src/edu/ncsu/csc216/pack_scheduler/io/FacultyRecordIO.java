package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * This class processes files containing Faculty Records. Text files can be read
 * and Faculty objects created from the information contained in the file. Text
 * files containing a list of student records can also be created. If a file is
 * unreadable, a FileNotFoundException will be thrown
 * @author kmbrown
 *
 */
public class FacultyRecordIO {

	/**
	 * Takes in the name of a text file containing faculty records, reads the
	 * file to access the records, processes those records to create Faculty
	 * objects, and returns an SortedList of those Faculty. Throws
	 * FileNotFoundException if the file is unreadable.
	 * 
	 * @param fileName
	 *            The name of the text file containing a list of faculty records
	 * @return an SortedList containing Faculty objects created from the
	 *         information in the file
	 * @throws FileNotFoundException
	 *             if file cannot be located or read
	 */
	public static ArrayList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {

		File facultyFile = new File(fileName);

		if (facultyFile.canRead()) {
			FileInputStream facultyFileStream = new FileInputStream(fileName);
			Scanner facultyFileReader = new Scanner(facultyFileStream);
			ArrayList<Faculty> facultyRecords = new ArrayList<Faculty>();
			while (facultyFileReader.hasNextLine()) {
				try {
					Faculty faculty = processFaculty(facultyFileReader.nextLine());
					boolean duplicate = false;
					for (int i = 0; i < facultyRecords.size(); i++) {
						User s = facultyRecords.get(i);
						if (faculty.getFirstName().equals(s.getFirstName())
								&& faculty.getLastName().equals(s.getLastName()) && faculty.getId().equals(s.getId())) {
							// it's a duplicate
							duplicate = true;
						}
					}
					if (!duplicate) {
						facultyRecords.add(faculty);
					}
				} catch (IllegalArgumentException e) {

					// Not sure what to do here. Would like to log the failed
					// line somehow, but unclear how best to do that without
					// altering the method signature to include a return int
					// that counts instances.
					System.out.println("Skipped line from file due to incorrect formatting.");
				}
			}

			facultyFileReader.close();
			return facultyRecords;
		}
		throw new FileNotFoundException();
	}
	
	/**
	 * Accepts an individual line of information from the file and processes it
	 * into a new Faculty 
	 * 
	 * @param facultyRecord the line of info containing
	 * the faculty's name, id, email, password, and max credits
	 * 
	 * @param facultyRecord
	 *            String containing the line from the file to process as 
	 *            Faculty
	 * @return the new Faculty object
	 * @throws IllegalArgumentException
	 *             if information is not in correct format
	 */
	private static Faculty processFaculty(String facultyRecord) {
		
		Scanner facultyRecordReader = new Scanner(facultyRecord);
		facultyRecordReader.useDelimiter(",");
		String firstName;
		String lastName;
		String id;
		String email;
		String password;
		int maxCourses = 0;

		try {
			firstName = facultyRecordReader.next();
			lastName = facultyRecordReader.next();
			id = facultyRecordReader.next();
			email = facultyRecordReader.next();
			password = facultyRecordReader.next();

			if (facultyRecordReader.hasNextInt()) {
				maxCourses = facultyRecordReader.nextInt();
			}

			Faculty newFaculty = new Faculty(firstName, lastName, id, email, password, maxCourses);
			facultyRecordReader.close();
			return newFaculty;
			

		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Writes to a text file the information contained in the Faculty Directory
	 * 
	 * @param fileName
	 *            name of the text file intended to receive the Faculty
	 *            Directory information
	 * @param facultyDirectory
	 *            the SortedList which contains the Faculty Directory list
	 * @throws IOException
	 *             if the file cannot be written to
	 */
	public static void writeFacultyRecords(String fileName, ArrayList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}

		fileWriter.close();
	}
	

}
