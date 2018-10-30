package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;

/**
 * Maintains information and behavior of Schedule objects
 * 
 * @author kmbrown
 *
 */
public class Schedule {

	/** The title of the Schedule */
	private String title;
	/** The custom ArrayList of Courses */
	private ArrayList<Course> schedule;

	/**
	 * The null constructor for Schedule objects
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");

	}

	/**
	 * Adds Course to the Schedule, if
	 * 
	 * @param course
	 *            the Course to be added to the Schedule
	 * 
	 * @return true if the Course was added to the Schedule
	 */
	public boolean addCourseToSchedule(Course course) {

		for (int i = 0; i < schedule.size(); i++) {
			if (course.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You are already enrolled in " + course.getName());
			}
		}

		try {
			for (int i = 0; i < schedule.size(); i++) {
				course.checkConflict(schedule.get(i));
			}
		} catch (ConflictException e) {
			throw new IllegalArgumentException("The course cannot be added due to a conflict.");
		}

		if (course != null) {
			schedule.add(course);
			return true;
		}

		return false;
	}

	/**
	 * Removes Course from the Schedule
	 * 
	 * @param course
	 *            Course to be removed from the Schedule, if the Course is
	 *            present on the Schedule
	 * 
	 * @return true if the Course was removed from the Schedule
	 */
	public boolean removeCourseFromSchedule(Course course) {

		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).equals(course)) {

				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Empties the Schedule of all Courses
	 * 
	 */
	public void resetSchedule() {
		ArrayList<Course> emptySchedule = new ArrayList<Course>();
		schedule = emptySchedule;

	}

	/**
	 * Retrieves a 2d String Array of Courses on the Schedule
	 * 
	 * @return the Courses on the Schedule organized into an Array
	 */
	public String[][] getScheduledCourses() {

		if (schedule.size() == 0) {

			String[][] emptySchedule = new String[0][0];
			return emptySchedule;

		} else {

			String[][] scheduleArray = new String[schedule.size()][4];

			for (int i = 0; i < scheduleArray.length; i++) {

				scheduleArray[i] = schedule.get(i).getShortDisplayArray();

			}

			return scheduleArray;
		}

	}

	/**
	 * Sets the title of the Schedule
	 * 
	 * @param title
	 *            set as title if not null or empty
	 */
	public void setTitle(String title) {

		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}

		this.title = title;

	}

	/**
	 * Retrieves the title of the Schedule
	 * 
	 * @return the title of the Schedule
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Retrieves the number of credits on the Schedule
	 * 
	 * @return the number of credits currently on the Schedule
	 */
	public int getScheduleCredits() {
		int scheduleCredits = 0;

		for (int i = 0; i < schedule.size(); i++) {
			scheduleCredits += schedule.get(i).getCredits();
		}

		return scheduleCredits;
	}

	/**
	 * Determines whether a Course can be added to the Schedule
	 * 
	 * @param course
	 *            the Course being examined to see if it can be added to the
	 *            Schedule
	 * @return true if the Course can be added
	 */
	public boolean canAdd(Course course) {
		// check that course is not null
		if (course == null) {
			return false;
		}
		// check that course is not duplicate
		for (int i = 0; i < schedule.size(); i++) {
			if (course.isDuplicate(schedule.get(i))) {
				return false;
			}
		}
		// check that course is not in conflict with another course
		try {
			for (int i = 0; i < schedule.size(); i++) {
				course.checkConflict(schedule.get(i));
			}
		} catch (ConflictException e) {
			return false;
		}

		// if doesn't fail any checks
		return true;

	}

}
