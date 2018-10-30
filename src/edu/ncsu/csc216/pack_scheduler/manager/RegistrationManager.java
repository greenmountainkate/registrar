package edu.ncsu.csc216.pack_scheduler.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Handles the registration functionality for the Packscheduler system and
 * includes a sub-class, Registrar which extends the User class and has
 * administrative authority over the Packscheduler system. Singleton class.
 * 
 * @author kmbrown
 *
 */
public class RegistrationManager {

	/** The solitary instance of the RegistrationManager class */
	private static RegistrationManager instance;
	/** The CourseCatalog used by the RegistrationManager system */
	private CourseCatalog courseCatalog;
	/** The StudentDirectory used by the RegistrationManager system */
	private StudentDirectory studentDirectory;
	/** The administrative user of the RegistrationManager system */
	private User registrar;
	/** The user currently logged in to the RegistrationManager system */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** Hard-coded password for the Registrar user */
	private static final String PW = "Regi5tr@r";
	/** Receptacle for the hashed user password */
	private static String hashPW;
	/** Directory of Faculty used by the RegistrationManager */
	private FacultyDirectory facultyDirectory;

	// Static code block for hashing the registrar user's password
	{
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(PW.getBytes());
			hashPW = new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	private RegistrationManager() {
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
		registrar = new Registrar();
		facultyDirectory = new FacultyDirectory();
		currentUser = null;

	}

	/**
	 * Generates the only instance of the singleton class, RegistrationManager
	 * 
	 * @return the instance of the RegistrationManager to be used
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Retrieves the current CourseCatalog
	 * 
	 * @return the Course Catalog that is currently being manipulated
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Retrieves the StudentDirectory currently being modified by the
	 * RegistrationManager
	 * 
	 * @return The StudentDirectory in current use
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Retrieves the FacultyDirectory currently being used by the
	 * RegistrationManager
	 * 
	 * @return the FacultyDirectory being used currently
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Logs the user into the RegistrationManager system
	 * 
	 * @param id
	 *            The id of the User
	 * @param password
	 *            The password of the user
	 * @return true if the User was able to be logged into the system
	 */
	public boolean login(String id, String password) {

		if (currentUser == null) {
			if (registrar.getId().equals(id)) {
				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());
					if (registrar.getPassword().equals(localHashPW)) {
						currentUser = registrar;
						return true;
					}
					if (!registrar.getPassword().equals(localHashPW)) {
						throw new IllegalArgumentException("Invalid id or password");
					}

				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}

			}

			Student s = studentDirectory.getStudentById(id);
			if (s != null) {
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());
					if (s != null) {
						if (s.getPassword().equals(localHashPW)) {
							currentUser = s;
							return true;
						}
						if (!s.getPassword().equals(localHashPW)) {
							throw new IllegalArgumentException("Invalid id or password");
						}
					}

				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}
			}

			Faculty f = facultyDirectory.getFacultyById(id);

			if (f == null) {

				throw new IllegalArgumentException("User doesn't exist.");
			}

			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (f != null) {
					if (f.getPassword().equals(localHashPW)) {
						currentUser = f;
						return true;
					}
					if (!f.getPassword().equals(localHashPW)) {
						throw new IllegalArgumentException("Invalid id or password");
					}
				}

			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}
		return false;
	}

	/**
	 * Logs the current user out of the RegistrationManager system.
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Retrieves the currently logged in user of the RegistrationManager system
	 * 
	 * @return the User of the RegistrationManager
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Empties both the CourseCatalog and the StudentDirectory being used by the
	 * RegistrationManager system
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c
	 *            Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s) && roll.getOpenSeats() > 0) {
				// add to CourseRoll
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			} else if (s.canAdd(c) && roll.canEnroll(s) && roll.getNumberOnWaitlist() < 10) {
				// try to add to waitlist
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c
	 *            Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every
	 * course and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * Adds a Course to a FacultySchedule, if the Registrar is currently logged
	 * in
	 * 
	 * @param course
	 *            the Course to add to the Faculty Schedule
	 * @param faculty
	 *            the Faculty member whose schedule should include the Course
	 * @return true if the Course is added to the Faculty schedule
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {

		if (currentUser != null && currentUser instanceof Registrar) {
			return faculty.getSchedule().addCourseToSchedule(course);
		} else if (currentUser != null && !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		} else {
			return false;
		}
	}

	/**
	 * Removes a given Course from the FacultySchedule, must be called by
	 * Registrar user
	 * 
	 * @param course
	 *            the Course to remove from the FacultySchedule
	 * @param faculty
	 *            the Faculty member from whose schedule the Course should be
	 *            removed
	 * @return true if the Course has been removed
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {

		if (currentUser != null && currentUser instanceof Registrar) {
			return faculty.getSchedule().removeCourseFromSchedule(course);
		} else if (currentUser != null && !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		} else {
			return false;
		}
	}

	/**
	 * Resets the FacultySchedule - all Courses removed
	 * 
	 * @param faculty
	 *            the Faculty member whose schedule should be cleared
	 */
	public void resetFacultySchedule(Faculty faculty) {

		if (currentUser != null && currentUser instanceof Registrar) {
			faculty.getSchedule().resetSchedule();
		} else if (currentUser != null && !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Holds the information and functionality of the Registrar type of User
	 * 
	 * @author kmbrown
	 *
	 */
	private static class Registrar extends User {

		private static final String FIRST_NAME = "Wolf";
		private static final String LAST_NAME = "Scheduler";
		private static final String ID = "registrar";
		private static final String EMAIL = "registrar@ncsu.edu";

		/**
		 * Create a registrar user with the user id of registrar and password of
		 * Regi5tr@r. Note that hard coding passwords in a project is HORRIBLY
		 * INSECURE, but it simplifies testing here. This should NEVER be done
		 * in practice!
		 */
		public Registrar() {
			super(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		}
	}

}