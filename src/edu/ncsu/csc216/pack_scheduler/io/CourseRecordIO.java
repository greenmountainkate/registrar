
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;


/**
 * Manages the reading and writing of Course records to and from files
 * 
 * @author kmbrown
 *
 */
public class CourseRecordIO {

	/**
	 * Accepts file and reads course records from it. Disregards invalid Courses
	 * and returns a list of valid Course records. Throws a File
	 * NotFoundException if file can't be read or located.
	 * 
	 * @param fileName
	 *            file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException
	 *             if file cannot be found or read
	 * 
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException{
		File courseFile = new File(fileName);
		if (courseFile.canRead()) {
			Scanner fileReader = new Scanner(courseFile);
			SortedList<Course> courses = new SortedList<Course>();
			while (fileReader.hasNextLine()) {
				try {
					Course course = readCourse(fileReader.nextLine());
					boolean duplicate = false;
					for (int i = 0; i < courses.size(); i++) {
						Course c = courses.get(i);
						if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
							// it's a duplicate
							duplicate = true;
						}
					}
					if (!duplicate) {
						courses.add(course);
					}
				} catch (IllegalArgumentException e) {

					if (fileReader.hasNextLine()) {
						fileReader.nextLine();
					}
				}
			}
			fileReader.close();
			return courses;
		}
		throw new FileNotFoundException();
	}

	/**
	 * Reads and processes individual Course objects line-by-line from provided
	 * file by accepting Strings that are provided as parameters to the Course
	 * constructor called by the method
	 * 
	 * @param nextLine
	 *            String containing the line from the file being read which
	 *            needs to be processed
	 * @return Course constructed from information containing in line of file
	 *         provided to the method
	 * @throws IllegalArgumentException
	 *             if the line can't be read or if the line contains an invalid
	 *             Course record
	 * 
	 */
	private static Course readCourse(String nextLine) throws IllegalArgumentException{
		Scanner scan = new Scanner(nextLine);
		scan.useDelimiter(",");
		String name;
		String title;
		String section;
		int credits;
		String instructorID;
		int enrollmentCap;
		String meetingDays;
		int startTime;
		int endTime;

		try {

			name = scan.next();
			title = scan.next();
			section = scan.next();
			credits = scan.nextInt();
			instructorID = scan.next();
			enrollmentCap = scan.nextInt();
			meetingDays = scan.next();

			if (scan.hasNextInt()) {
				if (!meetingDays.equals("A")) {
					startTime = scan.nextInt();
					endTime = scan.nextInt();
					try {
						Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime,
								endTime);
						scan.close();
						
						if(RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorID) != null){
							RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorID).getSchedule().addCourseToSchedule(course);
						}
						
						return course;
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException();
					}
				} else {
					scan.close();
					throw new IllegalArgumentException();
				}
			}

		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}

		try {
			Course courseArranged = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
			if(RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorID) != null){
				RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorID).getSchedule().addCourseToSchedule(courseArranged);
			}
			scan.close();
			return courseArranged;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Writes a list of Course records to a file
	 * 
	 * @param fileName
	 *            String containing the name of the file where the course
	 *            records are to be saved
	 * @param courses
	 *            set of Courses to be recorded in the file
	 * @throws IOException
	 *             if file cannot be written to
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}
