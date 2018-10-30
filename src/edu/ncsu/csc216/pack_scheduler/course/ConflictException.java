package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Defines the custom checked exception ConflictException and contains two
 * constructors, void and parameterized
 * 
 * @author kmbrown
 *
 */
public class ConflictException extends Exception {

	/**
	 * ID used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs ConflictException object with a specific message passed as a
	 * String to the method
	 * 
	 * @param message
	 *            Specific error message
	 */
	public ConflictException(String message) {

		super(message);
	}

	/**
	 * Constructs a ConflictException object using the default error message
	 */
	public ConflictException() {

		this("Schedule conflict.");

	}

}
