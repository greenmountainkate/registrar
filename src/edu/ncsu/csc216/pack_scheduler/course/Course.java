package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * This class contains and maintains information related to Course type
 * Activities, including name, title, section, credit value, instructor id,
 * meeting dates, and meeting times
 * 
 * @author kmbrown
 *
 */

public class Course extends Activity implements Comparable<Course> {

	/** Allowable length of Section identifier */
	private static final int SECTION_LENGTH = 3;
	/** Maximum allowable Course name length */
	private static final int MAX_NAME_LENGTH = 8;
	/** Minimum allowable Course name length */
	private static final int MIN_NAME_LENGTH = 4;
	/** Maximum allowable credit value */
	private static final int MAX_CREDITS = 5;
	/** Minimum allowable credit value */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's enrollment list */
	private CourseRoll roll;

	/**
	 * Creates a course with the given name, title, section, credit value,
	 * instructor id, meeting days, start time, and end time
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
	 * 
	 * @param enrollmentCap
	 *            the maximum enrollment in the Course
	 *
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) throws IllegalArgumentException {

		super(title, startTime, endTime);
		try {

			setName(name);
			setSection(section);
			setCredits(credits);
			setInstructorId(instructorId);
			setMeetingDays(meetingDays);
			roll = new CourseRoll(this, enrollmentCap);

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Creates a Course with given name, title, section, credit value,
	 * instructor id, and meeting days Used when meeting days are Arranged and
	 * meeting times are absent.
	 * 
	 * @param name
	 *            Course name, between 4 and 6 characters
	 * @param title
	 *            Course title
	 * @param section
	 *            Course section, three characters long
	 * @param credits
	 *            Course credit value, between 1 and 5, inclusive
	 * @param instructorId
	 *            Course instructor's unity id
	 * @param meetingDays
	 *            Course meeting days, in abbreviated format, one character/day
	 *            (M, T, W, H, F). Only weekdays permissible.
	 * 
	 * @param enrollmentCap
	 *            the maximum enrollment in the Course
	 * 
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {

		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);

	}

	/**
	 * Returns the Course's name
	 * 
	 * @return String containing the name of the Course
	 */
	public String getName() {

		return name;
	}

	/**
	 * Sets the Course's name from a passed String, which must not be null,
	 * empty, or outside permissible length
	 * 
	 * @param name
	 *            String containing the name to set
	 * 
	 * @throws IllegalArgumentException
	 *             if the String parameter is null or empty, shorter than 4
	 *             characters or longer than 6 characters
	 * 
	 */
	private void setName(String name) {

		if (name == null) {
			throw new IllegalArgumentException("Invalid name");
		}

		if (name.equals("")) {
			throw new IllegalArgumentException("Invalid name");
		}

		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid name");
		}

		CourseNameValidator cnv = new CourseNameValidator();
		boolean isValid = false;
		try {
			isValid = cnv.isValid(name);
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid name");
		}

		if (isValid) {
			this.name = name;
		}
	}

	/**
	 * Returns the Course's section
	 * 
	 * @return String containing the section
	 */
	public String getSection() {

		return section;
	}

	/**
	 * Sets the Course's section if a valid String containing the section
	 * information is passed
	 * 
	 * @param section
	 *            String containing the section information
	 * @throws IllegalArgumentException
	 *             if the String parameter is null, empty, contains non-digit
	 *             characters, or is incorrect length (3 characters)
	 */
	public void setSection(String section) {

		if (section == null) {
			throw new IllegalArgumentException();
		}

		if (section.equals("")) {
			throw new IllegalArgumentException("Invalid section");
		}

		if (section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section");
		}
		for (int i = 0; i < section.length(); i++) {
			char c = section.charAt(i);
			if (!Character.isDigit(c)) {
				throw new IllegalArgumentException("Invalid section");
			}

		}
		this.section = section;
	}

	/**
	 * Returns the credit value of the Course
	 * 
	 * @return the credits
	 */
	public int getCredits() {

		return credits;
	}

	/**
	 * Sets the credit value of the Course if valid value is passed
	 * 
	 * @param credits
	 *            integer number of credit hours
	 * @throws IllegalArgumentException
	 *             if credit hours value is outside of allowed minimum/maximum
	 *             (1 - 5, inclusive)
	 */
	public void setCredits(int credits) {

		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits");
		}
		this.credits = credits;
	}

	/**
	 * Returns the id of the Course instructor
	 * 
	 * @return the instructor id in String format
	 */
	public String getInstructorId() {

		return instructorId;

	}

	/**
	 * Sets the id of the Course instructor if a valid String is passed
	 * 
	 * @param instructorId
	 *            String containing the instructor id to set
	 * @throws IllegalArgumentException
	 *             if String parameter is empty
	 */
	public void setInstructorId(String instructorId) {
		
		if (instructorId != null && instructorId.equals("")) {
			throw new IllegalArgumentException("Invalid instructor id");
		}
		
		this.instructorId = instructorId;
	}

	/**
	 * Returns the String containing the days that the Activity meets
	 * 
	 * @return String containing the meeting days of the Activity
	 */
	public String getMeetingDays() {

		return meetingDays;
	}

	/**
	 * Sets the days that the Activity meets if the days to set are not null or
	 * an empty String. String is checked to ensure that it only contains
	 * abbreviations for weekday classes (M, T, W, H, F) or A for arranged.
	 * Presence of other characters will throw an exception. Also checks that
	 * the meeting days do not combine in an entry indicating an arranged class
	 * with other meeting days. Also rejects entries including multiple entries
	 * of the same meeting day, example MMM
	 * 
	 * @param meetingDays
	 *            String containing the meeting days of the Activity
	 * @throws IllegalArgumentException
	 *             if days to be set are null, empty, contains an incorrect
	 *             combination of characters, or contains invalid characters
	 */
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null) {
			throw new IllegalArgumentException("Invalid meeting days");
		}

		if (meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}

		meetingDays = meetingDays.toUpperCase();
		int m = 0;
		int t = 0;
		int w = 0;
		int h = 0;
		int f = 0;
		int a = 0;

		for (int i = 0; i < meetingDays.length(); i++) {

			if (meetingDays.charAt(i) == 'M') {
				m++;
			}
			if (meetingDays.charAt(i) == 'T') {

				t++;
			}
			if (meetingDays.charAt(i) == 'W') {

				w++;
			}
			if (meetingDays.charAt(i) == 'H') {

				h++;
			}
			if (meetingDays.charAt(i) == 'F') {

				f++;
			}
			if (meetingDays.charAt(i) == 'A') {

				a++;
			}
		}

		int total = a + t + w + h + f + m;

		if (total < meetingDays.length()) {
			throw new IllegalArgumentException();
		}

		if (a > 0 && a < total) {
			throw new IllegalArgumentException();
		}

		// Added if statements here to cover multiple entries
		// of other valid days, for example if MMM was entered, an exception
		// would be thrown
		if (m > 1) {
			throw new IllegalArgumentException();
		}
		if (t > 1) {
			throw new IllegalArgumentException();
		}
		if (w > 1) {
			throw new IllegalArgumentException();
		}
		if (h > 1) {
			throw new IllegalArgumentException();
		}
		if (f > 1) {
			throw new IllegalArgumentException();
		}

		this.meetingDays = meetingDays;

		if (this.meetingDays.equals("A")) {
			setActivityTime(0, 0);
		}
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course, comma separated with no
	 *         whitespace between fields
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
				+ roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Generates hashcode for Course objects
	 * 
	 * @return the hashcode for the Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + (/* (instructorId == null) ? 0 : */ instructorId.hashCode());
		result = prime * result + (/* (meetingDays == null) ? 0 : */ meetingDays.hashCode());
		result = prime * result + (/* (name == null) ? 0 : */ name.hashCode());
		result = prime * result + (/* (section == null) ? 0 : */ section.hashCode());
		return result;
	}

	/**
	 * Compares Course objects for equality on all fields
	 * 
	 * @return if Courses are equal with respect to all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		// if (getClass() != obj.getClass())
		// return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		 if (instructorId == null) {
		 if (other.instructorId != null)
		 return false;
		 } else
		if (!instructorId.equals(other.instructorId))
			return false;
		// if (meetingDays == null) {
		// if (other.meetingDays != null)
		// return false;
		// } else
		if (!meetingDays.equals(other.meetingDays))
			return false;
		// if (name == null) {
		// if (other.name != null)
		// return false;
		// } else
		if (!name.equals(other.name))
			return false;
		// if (section == null) {
		// if (other.section != null)
		// return false;
		// } else
		if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Creates and returns an array of Strings that contains the information
	 * needed for a short display in the WolfScheduler GUI, including name,
	 * section, title, and meeting days and times
	 * 
	 * @return a String array containing the Course's name, section, title, and
	 *         meeting days and times
	 */
	@Override
	public String[] getShortDisplayArray() {

		String[] shortDisplay = new String[5];

		shortDisplay[0] = name;
		shortDisplay[1] = section;
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		shortDisplay[4] = roll.getOpenSeats() + "";

		return shortDisplay;

	}

	/**
	 * Creates and returns an array of Strings that contains the information
	 * needed for a detailed display in the WolfScheduler GUI, including name,
	 * section, title, credits, instructor id, and meeting days and times
	 * 
	 * @return a String array containing the Course's name, section, title,
	 *         credits, instructor id, and meeting days and times
	 */
	@Override
	public String[] getLongDisplayArray() {

		String[] longDisplay = new String[7];

		longDisplay[0] = name;
		longDisplay[1] = section;
		longDisplay[2] = getTitle();
		longDisplay[3] = credits + "";
		longDisplay[4] = instructorId;
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";

		return longDisplay;
	}

	@Override
	public boolean isDuplicate(Activity activity) {

		Course activityIsACourse;

		if (activity instanceof Course) {

			activityIsACourse = (Course) activity;

		} else {

			return false;
		}

		if (activityIsACourse.getName().equals(this.getName())) {

			return true;

		}

		return false;
	}

	/**
	 * Sorts Courses by name and then section number
	 * 
	 * @param course
	 *            the Course to be compared to the Course calling the method
	 * @return an integer value that indicates the ordering of the Courses
	 */
	public int compareTo(Course course) {

		int nameOrder = this.getName().toUpperCase().compareTo(course.getName().toUpperCase());
		int sectionOrder = this.getSection().toUpperCase().compareTo(course.getSection().toUpperCase());

		if (nameOrder == 0) {

			return sectionOrder;

		} else {

			return nameOrder;

		}

	}

	/**
	 * Retrieves the CourseRoll for the Course
	 * 
	 * @return the CourseRoll of the Course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

}
