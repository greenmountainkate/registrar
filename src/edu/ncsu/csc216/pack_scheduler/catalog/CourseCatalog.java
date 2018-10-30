package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;
import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Maintains a Catalog of all Courses available. Each Course has a unique Name
 * and Section String.
 * 
 * @author kmbrown
 *
 */
public class CourseCatalog {

	/** List of Courses in the Catalog */
	private SortedList<Course> catalog;

	/**
	 * Creates an empty Course Catalog
	 */
	public CourseCatalog() {

		newCourseCatalog();
	}

	/**
	 * Creates an empty Course Catalog. All Courses in previous list are lost
	 * unless saved by user.
	 */
	public void newCourseCatalog() {

		catalog = new SortedList<Course>();
	}

	/**
	 * Constructs CourseCatalog object by reading in information from file
	 * 
	 * @param fileName
	 *            String containing the name of the text file containing the
	 *            courses to be added to Course catalog
	 * @throws IllegalArgumentException
	 *             if the text file can't be found or opened
	 * 
	 * 
	 */
	public void loadCoursesFromFile(String fileName) throws IllegalArgumentException {

		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {

			throw new IllegalArgumentException("Cannot find file.");
		}
	}

	/**
	 * Adds a new Course to the Course Catalog if it is not a duplicate of a
	 * Course already contained by the Catalog
	 * 
	 * @param name
	 *            Course name, between 4 and 6 characters
	 * @param title
	 *            Course title
	 * @param section
	 *            Course section, 3 characters long
	 * @param credits
	 *            Course credit value, between 1 and 5 inclusive
	 * @param instructorId
	 *            Course instructor's unity id
	 * @param meetingDays
	 *            Course meeting days abbreviated to one character/day (M, T, W,
	 *            H, F), only weekdays permissible
	 * @param startTime
	 *            Course start time in 24 hour clock format
	 * @param endTime
	 *            Course end time in 24 hour clock format
	 * @param enrollmentCap
	 *            the maximum enrollment in the Course
	 *
	 * @return true if the Course is added to the Catalog
	 * 
	 * 
	 * @throws IllegalArgumentException
	 *             if the Course is unable to be constructed from the parameters
	 *             passed
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime)
			throws IllegalArgumentException /*
											 * throws IllegalArgumentException
											 */ {

		Course courseToAdd = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays,
				startTime, endTime);

		for (int i = 0; i < catalog.size(); i++) {

			Course c = catalog.get(i);

			if (c.getName().equals(courseToAdd.getName()) && c.getSection().equals(courseToAdd.getSection())) {

				return false;
			}
		}
		return catalog.add(courseToAdd);

	}

	/**
	 * Removes the Course from the Catalog if it is present in the Catalog
	 * 
	 * @param name
	 *            Name of the Course to be removed, between 4 and 6 characters
	 *            in length
	 * @param section
	 *            Section of the Course to be removed, 3 characters in length
	 * @return true if the Course is removed from the Catalog
	 */
	public boolean removeCourseFromCatalog(String name, String section) {

		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);

			if (c.getName().equals(name) && c.getSection().equals(section)) {

				catalog.remove(i);
				return true;
			}
		}

		return false;

	}

	/**
	 * Retrieves a Course from the Course Catalog if it is present in the
	 * Catalog
	 * 
	 * @param name
	 *            Name of the Course, between 4 and 6 characters inclusive
	 * @param section
	 *            Section of the Course, 3 characters in length
	 * @return The Course object that was pulled from the Catalog, null if
	 *         Course requested isn't present in the Catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {

		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {

				return catalog.get(i);

			}
		}
		return null;
	}

	/**
	 * Returns a list of all Courses in the Catalog, with a columns for name,
	 * section, title, and meeting information
	 * 
	 * @return an Array containing the name, section, title, and meeting
	 *         information for all Courses in the Catalog
	 */
	public String[][] getCourseCatalog() {

		String[][] courseCatalog = new String[catalog.size()][5];

		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			courseCatalog[i][0] = c.getName();
			courseCatalog[i][1] = c.getSection();
			courseCatalog[i][2] = c.getTitle();
			courseCatalog[i][3] = c.getMeetingString();
			courseCatalog[i][4] = c.getCourseRoll().getOpenSeats() + "";
			
		}

		return courseCatalog;

	}

	/**
	 * Saves the Course Catalog to a file
	 * 
	 * @param fileName
	 *            Name of the file where the Course Catalog should be saved
	 * @throws IllegalArgumentException
	 *             if the file is unable to be found or written to
	 */
	public void saveCourseCatalog(String fileName) throws IllegalArgumentException {

		try {

			CourseRecordIO.writeCourseRecords(fileName, catalog);

		} catch (IOException e) {

			throw new IllegalArgumentException("The file cannot be saved.");
		}

	}
}
