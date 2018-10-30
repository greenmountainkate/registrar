package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This class contains and maintains information related to Student objects,
 * including first name, last name, unity id, email address, password, and max
 * credit
 * 
 * @author kmbrown
 *
 */
public class Student extends User implements Comparable<Student> {

	/** Student's max credits */
	private int maxCredits;
	/** Student's max credits, static and final for all Students */
	public static final int MAX_CREDITS = 18;
	/** Student's schedule */
	private Schedule schedule;

	/**
	 * Creates a Student with the given information for first and last name, id,
	 * and email address, and sets the max credit equal to the class default.
	 * 
	 * @param firstName
	 *            Student's first name
	 * @param lastName
	 *            Student's last name
	 * @param id
	 *            Student's unity ID
	 * @param email
	 *            Student's email address
	 * @param hashPW
	 *            Student's password, passed as hashed value
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {

		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);

	}

	/**
	 * Creates a Student with the given information for first and last name, id,
	 * email address, and max credit.
	 * 
	 * @param firstName
	 *            Student's first name
	 * @param lastName
	 *            Student's last name
	 * @param id
	 *            Student's unity ID
	 * @param email
	 *            Student's email address in valid email format
	 * @param hashPW
	 *            Student's password, passed as hashed value and containing at
	 *            least 6 characters
	 * @param maxCredits
	 *            Student's max credits between 3 and 18
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {

		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		this.schedule = new Schedule();
	}

	/**
	 * This method gets the Student's max credits
	 * 
	 * @return Student's max credits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * This method sets the Student's maximum credits if the value is between 3
	 * and 18
	 * 
	 * @param maxCredits
	 *            the value to set for the Student's credit
	 * @throws IllegalArgumentException
	 *             if the value passed is outside the allowed range
	 */
	public void setMaxCredits(int maxCredits) {
		if (3 > maxCredits || 18 < maxCredits) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * This method returns a comma separated String of all Student fields
	 * 
	 * @return String representation of Student object
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCredits;
	}

	/**
	 * Compares Students for ordering, Last Name first compared for alphabetic
	 * order, then First Name compared for alphabetic order, then ID for
	 * alphabetic order
	 * 
	 * @param s
	 *            the Student to compare to the Student calling the compare
	 *            method
	 * @return negative integer if Student s should be earlier in a list of
	 *         Students than the Student calling the method, 0 if the Students
	 *         are equal, and a positive integer if the Student s should be
	 *         later in a list of Students than the Student calling the method
	 */
	public int compareTo(Student s) {
		int lastNameOrder = this.getLastName().toUpperCase().compareTo(s.getLastName().toUpperCase());
		int firstNameOrder = this.getFirstName().toUpperCase().compareTo(s.getFirstName().toUpperCase());
		int idOrder = this.getId().toUpperCase().compareTo(s.getId().toUpperCase());

		if (lastNameOrder == 0) {
			if (firstNameOrder == 0) {
				return idOrder;
			} else {
				return firstNameOrder;
			}
		} else {
			return lastNameOrder;
		}
	}

	/**
	 * Generates unique hashCode for Student objects
	 * @return the hashCode for the Student
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Tests Student objects for equality on all fields
	 * @return true if the Students are equal 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}

	/**
	 * Retrieves the Student's Schedule
	 * 
	 * @return the Student's current Schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Checks whether a Course can be added to a Student's schedule, which it
	 * can if it isn't null or would cause the Student's schedule to be over the
	 * Student's max credits
	 * 
	 * @param course
	 *            the Course to be examined for fitness for the Student's
	 *            schedule, cannot be null
	 * @return true if the Course may be added to the Student's schedule
	 */
	public boolean canAdd(Course course) {
		
		int currentCredits = schedule.getScheduleCredits() + course.getCredits();

		if (currentCredits > maxCredits) {
			return false;
		}

		return schedule.canAdd(course);
	}
}
