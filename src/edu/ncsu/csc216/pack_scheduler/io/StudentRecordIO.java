package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * This class processes files containing Student Records. Text files can be read
 * and Student objects created from the information contained in the file. Text
 * files containing a list of student records can also be created. If a file is
 * unreadable, a FileNotFoundException will be thrown
 * 
 * @author kmbrown
 *
 */
public class StudentRecordIO {

	/**
	 * Takes in the name of a text file containing student records, reads the
	 * file to access the records, processes those records to create Student
	 * objects, and returns an SortedList of those Students. Throws
	 * FileNotFoundException if the file is unreadable.
	 * 
	 * @param fileName
	 *            The name of the text file containing a list of student records
	 * @return an SortedList containing Student objects created from the
	 *         information in the file
	 * @throws FileNotFoundException
	 *             if file cannot be located or read
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {

		File studentFile = new File(fileName);

		if (studentFile.canRead()) {
			FileInputStream studentFileStream = new FileInputStream(fileName);
			Scanner studentFileReader = new Scanner(studentFileStream);
			SortedList<Student> studentRecords = new SortedList<Student>();
			while (studentFileReader.hasNextLine()) {
				try {
					Student student = processStudent(studentFileReader.nextLine());
					boolean duplicate = false;
					for (int i = 0; i < studentRecords.size(); i++) {
						User s = studentRecords.get(i);
						if (student.getFirstName().equals(s.getFirstName())
								&& student.getLastName().equals(s.getLastName()) && student.getId().equals(s.getId())) {
							// it's a duplicate
							duplicate = true;
						}
					}
					if (!duplicate) {
						studentRecords.add(student);
					}
				} catch (IllegalArgumentException e) {

					// Not sure what to do here. Would like to log the failed
					// line somehow, but unclear how best to do that without
					// altering the method signature to include a return int
					// that counts instances.
					System.out.println("Skipped line from file due to incorrect formatting.");
				}
			}

			studentFileReader.close();
			return studentRecords;
		}
		throw new FileNotFoundException();
	}

	/**
	 * Writes to a text file the information contained in the Student Directory
	 * 
	 * @param fileName
	 *            name of the text file intended to receive the Student
	 *            Directory information
	 * @param studentDirectory
	 *            the SortedList which contains the Student Directory list
	 * @throws IOException
	 *             if the file cannot be written to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();
	}

	/**
	 * Accepts an individual line of information from the file and processes it
	 * into a new Student // * @param studentRecord the line of info containing
	 * the student's name, id, email, password, and max credits
	 * 
	 * @param studentRecord
	 *            String containing the line from the file to process as a
	 *            Student
	 * @return the new Student object
	 * @throws IllegalArgumentException
	 *             if information is not in correct format
	 */
	private static Student processStudent(String studentRecord) {
		// FileInputStream studentRecordInput = new
		// FileInputStream(studentRecord);
		Scanner studentRecordReader = new Scanner(studentRecord);
		studentRecordReader.useDelimiter(",");
		String firstName;
		String lastName;
		String id;
		String email;
		String password;
		int maxCredits = 0;

		// while (studentRecordReader.hasNext()) {
		try {
			firstName = studentRecordReader.next();
			lastName = studentRecordReader.next();
			id = studentRecordReader.next();
			email = studentRecordReader.next();
			password = studentRecordReader.next();

			if (studentRecordReader.hasNextInt()) {
				maxCredits = studentRecordReader.nextInt();
			}

			if (maxCredits == 0) {
				Student newStudent = new Student(firstName, lastName, id, email, password);
				studentRecordReader.close();
				return newStudent;
			} else {
				Student newStudent = new Student(firstName, lastName, id, email, password, maxCredits);
				studentRecordReader.close();
				return newStudent;
			}

		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}
	// studentRecordReader.close();
	// return null;

}
