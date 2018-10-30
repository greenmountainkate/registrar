package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Maintains information and methods related to Faculty
 * @author kmbrown
 *
 */
public class Faculty extends User implements Comparable<Faculty> {

	/**The maximum number of courses the Faculty member can teach in a semester*/
	private int maxCourses;
	/**The minimum number of courses a Faculty member can teach in a semester*/
	public static final int MIN_COURSES = 1;
	/**The maximum number of courses any Faculty member can teach in a semester*/
	public static final int MAX_COURSES = 3;
	/**The schedule of Courses being taught by the Faculty member*/
	private FacultySchedule schedule;

	/**
	 * Constructs a new Faculty object
	 * 
	 * @param firstName
	 *            First name, cannot be null or empty
	 * @param lastName
	 *            Last name, cannot be null or empty
	 * @param id
	 *            Unique identifier, cannot be null or empty
	 * @param email
	 *            Faculty email address, must be in correct email format
	 * @param hashPW
	 *            Password, hashed for security
	 * @param maxCourses
	 *            The maximum number of Courses that the Faculty member may
	 *            teach each semester, between 1 and 3 inclusive
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {

		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(getId());

	}

	/**
	 * Sets the maximum number of Courses that may be taught by the Faculty in a
	 * semester
	 * 
	 * @param maxCourses
	 *            the maximum number of Courses that may be taught in a
	 *            semester, between 1 and 3 inclusive
	 */
	public void setMaxCourses(int maxCourses) {
		if (MIN_COURSES > maxCourses || MAX_COURSES < maxCourses) {
			throw new IllegalArgumentException("Invalid max courses");
		}

		this.maxCourses = maxCourses;
	}

	/**
	 * Retrieves the maximum number of Courses that the Faculty member can teach
	 * in a semester
	 * 
	 * @return the maximum number of Courses that the Faculty can teach in a
	 *         semester
	 */
	public int getMaxCourses() {

		return maxCourses;
	}
	
	/**
	 * Retrieves the schedule of Courses being taught by the Faculty member
	 * @return the schedule of Courses
	 */
	public FacultySchedule getSchedule(){
		return schedule;
	}
	
	/**
	 * Checks whether the Faculty schedule contains more courses than the Faculty is permitted to teach in a semester
	 * @return true if there are more Courses on the FacultySchedule than the Faculty can teach in a semester
	 */
	public boolean isOverloaded(){
		
		return schedule.getNumScheduledCourses() > getMaxCourses();
	}

	/**
	 * Retrieves a comma separated String of all Faculty fields
	 * 
	 * @return String representation of the Faculty object
	 */
	public String toString() {
		
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCourses;
	}

	/**
	 * Compares Faculty for ordering, Last Name first compared for alphabetic
	 * order, then First Name compared for alphabetic order, then ID for
	 * alphabetic order
	 * 
	 * @param f the Faculty to compare to the Faculty calling the compare method
	 * 
	 * @return negative integer if Faculty f should be earlier in a list of
	 *         Faculty than the Faculty calling the method, 0 if the Faculty are
	 *         equal, and a positive integer if the Faculty f should be later in
	 *         a list of Faculty than the Faculty calling the method
	 */
	@Override
	public int compareTo(Faculty f) {
		int lastNameOrder = this.getLastName().toUpperCase().compareTo(f.getLastName().toUpperCase());
		int firstNameOrder = this.getFirstName().toUpperCase().compareTo(f.getFirstName().toUpperCase());
		int idOrder = this.getId().toUpperCase().compareTo(f.getId().toUpperCase());

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
	 * Generates unique hashcode for Faculty objects
	 * @return the hashcode for the Faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Tests Faculty objects for equality on all fields
	 * @return true if the Faculty are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}
	
	
}
