package org.jboss.reddeer.common.condition;

/**
 * Interface WaitCondition offers API for wait conditions. 
 * 
 * @author mlabuda
 */
public interface WaitCondition {

	/**
	 * Tests whether condition is met or not.
	 * 
	 * @return true if condition is met, false otherwise
	 */
	boolean test();
	
	/**
	 * Gets description of specific condition. This is 
	 * useful in logging.
	 * 
	 * @return description of specific wait condition
	 */
	String description();
	

	/**
	 * Gets error message if the condition fails. This is 
	 * useful in logging.
	 * 
	 * @return description of specific wait condition
	 */
	String errorMessage();
}
