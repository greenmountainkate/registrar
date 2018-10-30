package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * Manages the data and methods of the CourseRoll object, which maintains an
 * enrollment list of Students who are registered in a Course
 * 
 * @author kmbrown
 *
 */
public class CourseRoll {

	/** a custom LinkedAbstractList of Students */
	private LinkedAbstractList<Student> roll;
	/** roll's enrollment capacity */
	private int enrollmentCap;
	/** Smallest possible class size */
	private static final int MIN_ENROLLMENT = 10;
	/** Largest possible class size */
	private static final int MAX_ENROLLMENT = 250;
	/** Maximum capacity on waitlist */
	private static final int WAITLIST_SIZE = 10;
	/** Waitlist for the Course */
	private LinkedQueue<Student> waitlist;
	/** The Course */
	Course c;

	/**
	 * Constructor for CourseRoll objects
	 * 
	 * @param enrollmentCap
	 *            the maximum number of seats in the Course
	 * @param c
	 *            the Course the CourseRoll belongs to
	 * @throws IllegalArgumentException
	 *             if the Course parameter is null
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		setEnrollmentCap(enrollmentCap);
		roll = new LinkedAbstractList<Student>(this.enrollmentCap);
		waitlist = new LinkedQueue<>(WAITLIST_SIZE);
		this.c = c;

	}

	/**
	 * Retrieves the maximum number of seats on a CourseRoll
	 * 
	 * @return the enrollment cap, which is the maximum number of Students that
	 *         may be enrolled in a Course
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollment cap for the CourseRoll
	 * 
	 * @param enrollmentCap
	 *            the number of seats available for enrollment in a Course
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		// Check for valid enrollment cap size
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		// If roll is null, set enrollment cap
		if (roll == null) {
			this.enrollmentCap = enrollmentCap;
		} else {
			// If enrollment cap is greater than current capacity, increase
			// capacity
			if (enrollmentCap > roll.getCapacity()) {
				roll.setCapacity(enrollmentCap);
			}
			// If roll is constructed, check that new enrollment cap is not less
			// than number of Students already enrolled in the Course
			if (enrollmentCap >= roll.size()) {
				this.enrollmentCap = enrollmentCap;
			} else if (enrollmentCap < roll.size()) {
				throw new IllegalArgumentException();
			}
		}

	}

	/**
	 * Enrolls a Student in a Course, by adding the Student to the CourseRoll
	 * 
	 * @param student
	 *            the Student to enroll in the Course
	 */
	public void enroll(Student student) {
		if (student == null) {
			throw new IllegalArgumentException();
		}
		if (!canEnroll(student)) {
			throw new IllegalArgumentException("Course cannot be added to schedule.");
		}
		// check if there are seats available on the CourseRoll
		if (!(getOpenSeats() > 0)) {
			// if not, check if there is space on the Waitlist
			if (waitlist.size() < 10) {
				try {
					waitlist.enqueue(student);
				} catch (Exception e) {
					throw new IllegalArgumentException();
				}
			} else {
				// if there is no room on CourseRoll or Waitlist
				throw new IllegalArgumentException("The course cannot be added");
			}
		} else {
			// try adding to the CourseRoll
			try {
				roll.add(roll.size(), student);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Removes a Student from the CourseRoll
	 * 
	 * @param student
	 *            the student to be removed, if the Student is present on the
	 *            list of enrolled Students in the Course
	 */
	public void drop(Student student) {

		// check that student isn't null
		if (student == null) {
			throw new IllegalArgumentException();
		}

		// remove Student from roll, if present
		try {

			for (int i = 0; i < roll.size(); i++) {
				if (student.equals(roll.get(i))) {
					roll.remove(i);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		// remove Student from waitlist, if present
		if (waitlist.size() > 0) {

			try {
				for (int i = 0; i < waitlist.size(); i++) {
					if (student.equals(waitlist.getData(i))) {

						waitlist.remove(i);

					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
		// check if there are other Students on the waitlist & check for room on
		// CourseRoll
		if (getNumberOnWaitlist() > 0 && getOpenSeats() > 0) {

			// remove first Student from waitlist and add to
			// CourseRoll
			Student studentToAdd = waitlist.dequeue();

			if (studentToAdd.canAdd(c)) {
				enroll(studentToAdd);
				studentToAdd.getSchedule().addCourseToSchedule(c);
			}

		}

	}

	/**
	 * Retrieves the number of seats available for enrollment on a CourseRoll
	 * 
	 * @return the number of available seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Checks whether a given Student can be added to the CourseRoll
	 * 
	 * @param student
	 *            the Student whose eligibility to be enrolled in the Course is
	 *            being checked
	 * @return true if the Student is eligible to enroll in the Course
	 */
	public boolean canEnroll(Student student) {

		// check that the Student isn't already on the CourseRoll

		for (int i = 0; i < roll.size(); i++) {
			if (student.equals(roll.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Retrieves the number of Students on the Waitlist
	 * 
	 * @return the number of Students currently on the Waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

	/**
	 * Retrieves an Array of the Students on the CourseRoll
	 * 
	 * @return 2d String Array of the Students on the CourseRoll, including First Name, Last Name, and ID
	 */
	public String[][] getStudentsOnRoll() {

		String[][] studentRoll = new String[roll.size()][3];
		for (int i = 0; i < roll.size(); i++) {
			User s = roll.get(i);
			studentRoll[i][0] = s.getFirstName();
			studentRoll[i][1] = s.getLastName();
			studentRoll[i][2] = s.getId();
		}

		return studentRoll;

	}
}
