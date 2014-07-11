package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * WaitUntil condition represents a wait condition waiting until specific
 * condition is met.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public class WaitUntil extends AbstractWait {

	/**
	 * Waits until condition is met for default period. Throws
	 * WaitTimeoutExpiredException if condition is not met after expiration
	 * of default time period.
	 * 
	 * @param condition condition to wait until it is met
	 */
	public WaitUntil(WaitCondition condition) {
		super(condition);
	}

	/**
	 * Waits until condition is met for specified time period.
	 * WaitTimeoutExpiredException if condition is not met after expiration
	 * of specified time period.
	 * 
	 * @param condition condition to wait until it is met
	 * @param timeout period to wait for
	 */
	public WaitUntil(WaitCondition condition, TimePeriod timeout) {
		super(condition, timeout);
	}

	/**
	 * Waits until condition is met for specified time period. Can throw
	 * WaitTimeoutExpiredException if condition is not met after expiration
	 * of specified time period.
	 * 
	 * @param condition condition to wait until it is met
	 * @param timeout period to wait for
	 * @param throwWaitTimeoutExpiredException whether exception
	 * should be thrown or not
	 */
	public WaitUntil(WaitCondition condition, TimePeriod timeout,
			boolean throwWaitTimeoutExpiredException) {
		super(condition, timeout, throwWaitTimeoutExpiredException);
	}

	@Override
	protected boolean stopWaiting(WaitCondition condition) {
		return condition.test();
	}

	@Override
	protected String description() {
		return "Waiting until ";
	}
}
