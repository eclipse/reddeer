package org.jboss.reddeer.swt.condition;

/**
 * Interface WaitCondition offers API for wait conditions. 
 * 
 * @author mlabuda
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.common.condition.WaitCondition}
 */
@Deprecated
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
}
