package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the constructors of the InvalidTransitionException class
 * @author kmbrown
 *
 */
public class InvalidTransitionExceptionTest {
	
	/**
	 * Test method for InvalidTransitionException(String) constructor
	 */
	@Test
	public void invalidTransitionExceptionStringTest(){
		InvalidTransitionException ie = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", ie.getMessage());
	}

	/**
	 * Test method for null constructor of InvalidTransitionException
	 */
	@Test
	public void invalidTransitionExceptionTest(){
		InvalidTransitionException ie = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ie.getMessage());
	}
	
	
}
