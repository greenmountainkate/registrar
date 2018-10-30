/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface that outlines the method for checking whether Activities are in
 * schedule conflict, which is defined as meeting during the same time period
 * 
 * @author kmbrown
 *
 */
public interface Conflict {

	/**
	 * Checks whether Activities are in potential schedule conflict. Checks
	 * whether the parameter instance of Activity is in schedule conflict (meets
	 * during the same time period) with the current instance of Activity
	 * 
	 * @param possibleConflictingActivity
	 *            the instance of Activity which needs to be checked to see if
	 *            it conflicts with the Activity calling the method
	 * @throws ConflictException
	 *             if the Activities have an overlap in meeting times
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
