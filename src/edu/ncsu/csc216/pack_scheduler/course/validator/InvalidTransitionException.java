package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Defines the custom checked exception InvalidTransitionException and contains
 * two constructors, void and parameterized
 * 
 * @author kmbrown
 *
 */
public class InvalidTransitionException extends Exception {

	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs custom InvalidTransitionException with message passed to the
	 * constructor as a String
	 * 
	 * @param message
	 *            error message to be passed to the user
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructs an InvalidTransitionException object using the default error
	 * message
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");

	}

}
