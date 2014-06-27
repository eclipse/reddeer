package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Common ancestor for waiting classes. Contains abstract
 * {@link #wait(WaitCondition)} method that is called in the constructor.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public abstract class AbstractWait {
	
	/**
	 * Wait delay in milliseconds.
	 */
	protected static long WAIT_DELAY = 500;

	/**
	 * Wait logger.
	 */
	protected final Logger log = Logger.getLogger(this.getClass());

	private TimePeriod timeout;

	private boolean throwWaitTimeoutExpiredException = true;

	/**
	 * Waits till condition is met for default time period. Throws 
	 * WaitTimeoutExpiredException after waiting for specified time period
	 * and wait condition is not met.
	 * 
	 * @param condition wait condition to met
	 */
	public AbstractWait(WaitCondition condition) {
		this(condition, TimePeriod.NORMAL);
	}

	/**
	 * Waits till condition is met for specified timeout period. Throws 
	 * WaitTimeoutExpiredException after waiting for specified time period
	 * and wait condition is not met.
	 * 
	 * @param condition wait condition to met
	 * @param timePeriod time period to wait
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod) {
		this(condition, timePeriod, true);
	}

	/**
	 * Waits till condition is met for specified timeout period. There is a
	 * possibility to turn on/off throwing a exception.
	 * 
	 * @param condition wait condition to met
	 * @param timePeriod time period to wait
	 * @param throwRuntimeException whether exception should be thrown after
	 * expiration of the period
	 * @throws WaitTimeoutExpiredException
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod,
			boolean throwRuntimeException) {
		this.timeout = timePeriod;
		this.throwWaitTimeoutExpiredException = throwRuntimeException;

		log.debug(this.description() + condition.description() + "...");
		if (wait(condition)) {
			log.debug(this.description() + condition.description()
					+ " finished successfully");
		}
	}

	/**
	 * Waits till condition is met.
	 * 
	 * @param condition wait condition to met
	 * @return true if condition if met, false otherwise
	 */
	protected abstract boolean wait(WaitCondition condition);

	/**
	 * Gets description of specific wait condition. Description should
	 * provide information about nature of wait condition followed by space
	 * character. Purpose of this method is logging.
	 * 
	 * @return description of specific wait condition
	 */
	protected abstract String description();

	/**
	 * Gets time period of timeout.
	 * 
	 * @return time period of timeout
	 */
	protected TimePeriod getTimeout() {
		return timeout;
	}

	/**
	 * Finds out whether exception should be thrown is wait condition is not met
	 * after expiration of specified time period.
	 * 
	 * @return true if exception should be thrown, false otherwise
	 */
	protected boolean isThrowWaitTimeoutExpiredException() {
		return throwWaitTimeoutExpiredException;
	}

	/**
	 * Sleeps for specified time period.
	 * 
	 * @param timePeriod time period to sleep
	 */
	public static void sleep(TimePeriod timePeriod) {
		try {
			Thread.sleep(timePeriod.getSeconds() * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}

	/**
	 * Sleeps for period defined by specified time in milliseconds.
	 * 
	 * @param millis time in milliseconds to sleep
	 * @deprecated use sleep(TimePeriod timePeriod) instead, will be removed in 0.6
	 */
	public static void sleep(long millis) {
		sleep(TimePeriod.getCustom(millis / 1000));
	}
}
