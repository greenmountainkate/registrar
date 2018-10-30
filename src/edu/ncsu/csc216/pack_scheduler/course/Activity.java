package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface that outlines the information and behaviors common to all
 * Activities
 * 
 * @author kmbrown
 *
 */
public abstract class Activity implements Conflict {

	/** Maximum int value for minutes in military time */
	private static final int UPPER_HOUR = 60;
	/** Activity's title. */
	private String title;
	/** Activity's start time in 24 hour clock format */
	private int startTime;
	/** Activity's ending time */
	private int endTime;
	/** Activity's meeting time period */
	private String meetingTimes;

	/**
	 * Constructor for Activities that includes initializing the Activity's
	 * title, meeting days, and meeting times
	 *
	 * @param title
	 *            Activity's title
	 * @param startTime
	 *            the starting time for the Activity, in 24 hour clock format
	 * @param endTime
	 *            the ending time for the Activity, in 24 hour clock format
	 */
	public Activity(String title, int startTime, int endTime) {

		try {

			setTitle(title);
			setActivityTime(startTime, endTime);

		} catch (IllegalArgumentException e) {

			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Returns the Activity's title
	 * 
	 * @return String containing the title of the Activity
	 */
	public String getTitle() {

		return title;
	}

	/**
	 * Sets the Activity's title if the String passed is not null or empty
	 * 
	 * @param title
	 *            String containing the title to set
	 * @throws IllegalArgumentException
	 *             if the String parameter is null or empty
	 * 
	 */
	public void setTitle(String title) {

		if (title == null) {
			throw new IllegalArgumentException("Invalid title");
		}
		if (title.equals("")) {
			throw new IllegalArgumentException("Invalid title");
		}
		this.title = title;
	}

	/**
	 * Returns the String containing the days that the Activity meets
	 * 
	 * @return String containing the meeting days of the Activity
	 */
	public abstract String getMeetingDays();

	/**
	 * Returns the start time of the Activity
	 * 
	 * @return the start time of the Activity in 24 hour clock format
	 */
	public int getStartTime() {

		return startTime;
	}

	/**
	 * Returns the end time of the Activity
	 * 
	 * @return the end time of the Activity in 24 hour clock format
	 */
	public int getEndTime() {

		return endTime;
	}

	/**
	 * This method sets the starting and ending time for the Activity meetings.
	 * Checks start time and end time integers to ensure that they are valid
	 * clock times and compares start and end time for validity as a meeting
	 * period. Also checks that Course meeting times are not being set for an
	 * arranged Course.
	 * 
	 * @param startTime
	 *            an int passed as the starting time for the Activity
	 * @param endTime
	 *            an int passed as the ending time for the Activity
	 * @throws IllegalArgumentException
	 *             if times aren't valid clock times, the end time is before the
	 *             start time, or the Course is set as arranged
	 */
	public void setActivityTime(int startTime, int endTime) {

		if (validTime(startTime) && validTime(endTime) && (startTime <= endTime)) {

			this.startTime = startTime;
			this.endTime = endTime;
			return;

		} else {

			throw new IllegalArgumentException("Invalid meeting times");
		}

	}

	/**
	 * Generates a formatted String of the Activity's meeting days and times
	 * 
	 * @return the Activity's meeting days and times as a formatted String
	 */
	public String getMeetingString() {

		String arranged = "Arranged";

		if (getMeetingDays().equals("A")) {
			return arranged;
		}

		this.formatMeetingTimes();

		return getMeetingDays() + " " + this.meetingTimes;

	}

	/**
	 * Checks whether the int passed as a meeting time for the Activity is a
	 * valid clock time
	 * 
	 * @param time
	 *            the time to check in 24 hour clock format
	 * 
	 * @return true if time is a valid time
	 */
	public boolean validTime(int time) {
		if (time >= 0 && time < UPPER_HOUR) {
			return true;
		}
		if (time >= 100 && time <= 159) {
			return true;
		}
		if (time >= 200 && time <= 259) {
			return true;
		}
		if (time >= 300 && time <= 359) {
			return true;
		}
		if (time >= 400 && time <= 459) {
			return true;
		}
		if (time >= 500 && time <= 559) {
			return true;
		}
		if (time >= 600 && time <= 659) {
			return true;
		}
		if (time >= 700 && time <= 759) {
			return true;
		}
		if (time >= 800 && time <= 859) {
			return true;
		}
		if (time >= 900 && time <= 959) {
			return true;
		}
		if (time >= 1000 && time <= 1059) {
			return true;
		}
		if (time >= 1100 && time <= 1159) {
			return true;
		}
		if (time >= 1200 && time <= 1259) {
			return true;
		}
		if (time >= 1300 && time <= 1359) {
			return true;
		}
		if (time >= 1400 && time <= 1459) {
			return true;
		}
		if (time >= 1500 && time <= 1559) {
			return true;
		}
		if (time >= 1600 && time <= 1659) {
			return true;
		}
		if (time >= 1700 && time <= 1759) {
			return true;
		}
		if (time >= 1800 && time <= 1859) {
			return true;
		}
		if (time >= 1900 && time <= 1959) {
			return true;
		}
		if (time >= 2000 && time <= 2059) {
			return true;
		}
		if (time >= 2100 && time <= 2159) {
			return true;
		}
		if (time >= 2200 && time <= 2259) {
			return true;
		}
		if (time >= 2300 && time <= 2359) {
			return true;
		}
		// if(startTime == 2400){
		// this.startTime = 0;
		// return;
		// }//
		throw new IllegalArgumentException("Invalid Time.");

	}

	/**
	 * Formats the meeting times of the Activity by identifying whether the
	 * start or end time is AM or PM and calling appropriate formatting method,
	 * then combining formatted times into formatted String which is saved as
	 * meetingTimes
	 */
	private void formatMeetingTimes() {
		String standardStartTime = new String();
		String standardEndTime = new String();

		if (this.startTime < this.endTime) {
			if (this.startTime < 1200) {
				standardStartTime = convertToStandardAM(startTime);
			} else {
				standardStartTime = convertToStandardPM(startTime);
			}
			if (this.endTime < 1200) {
				standardEndTime = convertToStandardAM(endTime);
			} else {
				standardEndTime = convertToStandardPM(endTime);
			}
		}

		this.meetingTimes = standardStartTime + "-" + standardEndTime;

	}

	/**
	 * Converts 24hour clock time to 12hour clock time and formats String with
	 * AM
	 * 
	 * @param time
	 *            the time in a 24 hour clock format
	 * @return formatted time String in 12hour clock format
	 */
	private String convertToStandardAM(int time) {

		if (time < UPPER_HOUR) {
			time += 1200;
		}

		String standardAM = time + "";

		if (standardAM.length() == 4) {
			standardAM = standardAM.substring(0, 2) + ":" + standardAM.substring(2, standardAM.length());
		}

		if (standardAM.length() == 3) {
			standardAM = standardAM.substring(0, 1) + ":" + standardAM.substring(1, standardAM.length());
		}

		standardAM = standardAM + "AM";

		return standardAM;
	}

	/**
	 * Converts from 24hr clock to 12hr clock and formats String representation
	 * of time
	 * 
	 * @param time
	 *            integer containing time to format
	 * @return formatted time in 12hr clock style
	 */
	private String convertToStandardPM(int time) {
		if (time >= 1300) {
			time -= 1200;
		}
		String standardPM = time + "";
		if (standardPM.length() == 4) {
			standardPM = standardPM.substring(0, 2) + ":" + standardPM.substring(2, standardPM.length());
		}

		if (standardPM.length() == 3) {
			standardPM = standardPM.substring(0, 1) + ":" + standardPM.substring(1, standardPM.length());
		}

		standardPM = standardPM + "PM";
		return standardPM;

	}

	/**
	 * Generates Hashcode for Activity on all fields
	 *
	 * @return the hashcode for the Activity
	 *
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + startTime;
		result = prime * result + (/* (title == null) ? 0 : */ title.hashCode());
		return result;
	}

	/**
	 * Checks equality for Activities on all fields
	 * 
	 * @return true if Activities are equal on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (startTime != other.startTime)
			return false;
		// if (title == null) {
		// if (other.title != null)
		// return false;
		// } else
		if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Creates and returns an array of Strings that contains the information
	 * needed for a short display in the WolfScheduler GUI, including name,
	 * section, title, and meeting days and times
	 * 
	 * @return String array containing name, section, title, and meeting days
	 *         and times
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Creates and returns an array of Strings that contains the information
	 * needed for a detailed display in the WolfScheduler GUI, including name,
	 * section, title, credits, instructor id, meeting days and times, and/or
	 * event details
	 * 
	 * @return String array containing the name, section, title, credits,
	 *         instructor id, meeting days and times and/or event details
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Checks whether an Activity is a duplicate of an Activity already on the
	 * Student's schedule
	 * 
	 * @param activity
	 *            The Activity to be compared to the Activities already present
	 *            on the schedule
	 * @return true if the Activity is already present on the Schedule
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Checks whether two Activities are potentially in schedule conflict, which
	 * is defined as having any overlap in meeting time between the Activity
	 * calling the method and the Activity passed as the method parameter
	 * 
	 * @param possibleConflictingActivity
	 *            one of the Activities which must be checked to ensure that
	 *            meeting times are not in conflict
	 * @throws ConflictException
	 *             if there is any overlap in meeting time
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {

		// check for meeting days the same
		String meetingDays1 = possibleConflictingActivity.getMeetingDays().toUpperCase();

		String meetingDays2 = this.getMeetingDays().toUpperCase();

		if ((meetingDays1.indexOf('M') >= 0 && meetingDays2.indexOf('M') >= 0)
				|| (meetingDays1.indexOf('T') >= 0 && meetingDays2.indexOf('T') >= 0)
				|| (meetingDays1.indexOf('W') >= 0 && meetingDays2.indexOf('W') >= 0)
				|| (meetingDays1.indexOf('H') >= 0 && meetingDays2.indexOf('H') >= 0)
				|| (meetingDays1.indexOf('F') >= 0 && meetingDays2.indexOf('F') >= 0)
				|| (meetingDays1.indexOf('S') >= 0 && meetingDays2.indexOf('S') >= 0)
				|| (meetingDays1.indexOf('U') >= 0 && meetingDays2.indexOf('U') >= 0)) {

			// check if meeting times are equal or possibleConflictingActivity
			// is contained within this Activity
			if (this.getStartTime() <= possibleConflictingActivity.getStartTime()
					&& this.getEndTime() >= possibleConflictingActivity.getEndTime()) {

				throw new ConflictException();
			}

			// check whether there is an overlap between
			// possibleConflictingActivity and the end of this Activity
			if (this.getStartTime() <= possibleConflictingActivity.getStartTime()
					&& this.getEndTime() <= possibleConflictingActivity.getEndTime()
					&& possibleConflictingActivity.getStartTime() <= this.getEndTime()) {

				throw new ConflictException();
			}

			// check whether there is an overlap between
			// possibleConflictingActivity and the start of this Activity
			if (this.getStartTime() >= possibleConflictingActivity.getStartTime()
					&& this.getEndTime() >= possibleConflictingActivity.getEndTime()
					&& this.getStartTime() <= possibleConflictingActivity.getEndTime()) {

				throw new ConflictException();
			}

			// check if meeting times of this Activity are contained within the
			// meeting times of possibleConflictingActivity
			if (this.getStartTime() >= possibleConflictingActivity.getStartTime()
					&& this.getEndTime() <= possibleConflictingActivity.getEndTime()) {

				throw new ConflictException();
			}

		}

	}

}
