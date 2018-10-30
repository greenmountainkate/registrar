package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Tests whether a Course name is valid, defined as consisting of 1-4 letters,
 * followed by exactly 3 numbers, and possibly including a single alphabetic
 * suffix
 * 
 * @author kmbrown
 *
 */
public class CourseNameValidator {

	/** True if Course name is valid, false otherwise */
	private boolean validEndState;
	/** Holds the number of letters in the Course name */
	private int letterCount;
	/** Holds the number of digits in the Course name */
	private int digitCount;
	/** Holds the number of characters in the Course name suffix */
	private int suffixCount;

	/** State of being in the alphabetic segment of the Course name */
	State letterState;
	/** State of being in the numeric segment of the Course name */
	State numberState;
	/** State of being in the suffix segment of the Course name */
	State suffixState;
	/**
	 * The initial state of testing the Course name, before any characters have
	 * been read in
	 */
	State initialState;
	/** Holds the current state of the CourseNameValidator */
	State state;

	/**
	 * Constructs a CourseNameValidator object
	 */
	public CourseNameValidator() {
		initialState = new InitialState();
		letterState = new LetterState();
		numberState = new NumberState();
		suffixState = new SuffixState();

		state = initialState;
	}

	/**
	 * Method that determines whether a particular Course name is valid, which
	 * is defined as following particular parameters
	 * 
	 * @param courseName
	 *            the name to be checked to see if it follows the permitted
	 *            pattern for Course names, 1-4 letters, followed by exactly 3
	 *            numbers, possibly followed by a single alphabetic suffix
	 * @return true if the Course name is valid
	 * @throws InvalidTransitionException
	 *             if at any point there is an invalid transition between states
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {

		letterCount = 0;
		digitCount = 0;
		suffixCount = 0;
		validEndState = false;
		state = initialState;

		// Create a variable to track the current character index
		int charIndex = 0;

		// Variable to keep track of the current input character being examined
		char c;

		// Iterate through the ID, examining one character at a time
		while (charIndex < courseName.length()) {

			// Set the current character being examined
			c = courseName.charAt(charIndex);

			// call onOther() if non-alphanumeric character
			if (!Character.isLetter(c) && !Character.isDigit(c)) {
				state.onOther();
			}

			// call onLetter() if alphabetic character
			if (Character.isLetter(c)) {
				state.onLetter();
			}

			// call onDigit() if numeric character
			if (Character.isDigit(c)) {
				state.onDigit();
			}

			charIndex++;
		}
		if (digitCount != 3) {
			
			throw new InvalidTransitionException("Course name must have 3 digits.");
			/*return false;*/
		}
		return validEndState;
	}

	/**
	 * Abstract class that handles the outline for how the state for the
	 * CourseNameValidator should be managed
	 * 
	 * @author kmbrown
	 *
	 */
	public abstract class State {

		/**
		 * Used to process alphabetic characters in Course name
		 * 
		 * @throws InvalidTransitionException
		 *             if the Course name has an invalid change between states
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
		 * Used to process numeric characters in Course name
		 * 
		 * @throws InvalidTransitionException
		 *             if the Course name has an invalid change between states
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Used to process non-alphanumeric characters in Course name
		 * 
		 * @throws InvalidTransitionException
		 *             if the Course name has an invalid change between states
		 */
		public void onOther() throws InvalidTransitionException {

			throw new InvalidTransitionException("Course name can only contain letters and digits.");

		}

	}

	/**
	 * The starting state for the CourseNameValidator, before reading in a word.
	 * 
	 * @author kmbrown
	 *
	 */
	public class InitialState extends State {

		/** Null constructor for Initial State */
		private InitialState() {

		}

		/**
		 * Processes alphabetic characters in Course name
		 */
		public void onLetter() {
			letterCount++;
			state = letterState;

		}

		/**
		 * Processes numeric characters in Course name
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}

	/**
	 * Manages the state of being on an alphabetic character in the
	 * CourseNameValidator
	 * 
	 * @author kmbrown
	 *
	 */
	public class LetterState extends State {

		/** Holds the maximum number of allowable Course name prefix letters */
		private static final int MAX_PREFIX_LETTERS = 4;

		/** Null constructor for LetterState */
		private LetterState() {
		}

		/**
		 * Processes alphabetic characters in Course name
		 * 
		 * @throws InvalidTransitionException
		 *             if letter count is greater than max number of prefix
		 *             letteres
		 */
		public void onLetter() throws InvalidTransitionException {

			letterCount++;
			if (letterCount > MAX_PREFIX_LETTERS) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}

		}

		/**
		 * Processes numeric characters in Course name
		 */
		public void onDigit() {
			digitCount++;
			state = numberState;

		}

	}

	/**
	 * Handles the numeric state of a Course name
	 * 
	 * @author kmbrown
	 *
	 */
	public class NumberState extends State {

		/** Maximum digits in Course name */
		private static final int COURSE_NUMBER_LENGTH = 3;

		/**
		 * Null constructor for NumberState
		 */
		private NumberState() {

		}

		/**
		 * Processes alphabetic characters of Course name
		 */
		public void onLetter() throws InvalidTransitionException {

			if (digitCount != COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}

			suffixCount++;
			state = suffixState;
			validEndState = true;

		}

		/**
		 * Processes numeric characters of Course name
		 */
		public void onDigit() throws InvalidTransitionException {

			if (digitCount >= COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}

			digitCount++;

			if (digitCount == COURSE_NUMBER_LENGTH) {
				validEndState = true;
			}

		}
	}

	/**
	 * Handles the state of being in the Suffix segment of the
	 * CourseNameValidator
	 * 
	 * @author kmbrown
	 *
	 */
	public class SuffixState extends State {

		/** Holds the maximum number of characters in the Course name suffix */
		private static final int SUFFIX_LENGTH = 1;

		/**
		 * Null constructor for SuffixState
		 */
		private SuffixState() {

		}

		/**
		 * Processes alphabetic characters in Course name
		 * 
		 * @throws InvalidTransitionException
		 *             if a suffix contains more than a single alphabetic
		 *             character
		 */
		public void onLetter() throws InvalidTransitionException {

			if (suffixCount >= SUFFIX_LENGTH) {
				validEndState = false;
				throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
			}

		}

		/**
		 * Processes numeric characters in Course name
		 * 
		 * @throws InvalidTransitionException
		 *             if there is a numeric character in the Course name suffix
		 */
		public void onDigit() throws InvalidTransitionException {
			validEndState = false;
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}

	}

}
