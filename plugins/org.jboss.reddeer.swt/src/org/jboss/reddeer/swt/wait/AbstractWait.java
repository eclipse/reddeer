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
	// Wait delay in milliseconds
	protected static long WAIT_DELAY = 500;

	protected final Logger log = Logger.getLogger(this.getClass());

	private TimePeriod timeout;

	private boolean throwWaitTimeoutExpiredException = true;
	/**
	 * Waits with condition and default timeout
	 * Throws WaitTimeoutExpiredException when timeout had expired
	 * @param condition
	 */
	public AbstractWait(WaitCondition condition) {
		this(condition, TimePeriod.NORMAL);
	}
	/**
	 * Waits with condition and timeout
	 * Throws WaitTimeoutExpiredException when timeout had expired
	 * @param condition
	 * @param timeout
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod) {
		this(condition, timePeriod, true);
	}
	/**
	 * Waits with condition and timeout
	 * Throws WaitTimeoutExpiredException when timeout had expired
	 * and throwWaitTimeoutExpiredException is true
	 * @param condition
	 * @param timeout
	 * @param throwWaitTimeoutExpiredException
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timeout, boolean throwRuntimeException) {
		this.timeout = timeout;
		this.throwWaitTimeoutExpiredException = throwRuntimeException;
		
		log.debug(this.description()  + condition.description() + "...");
		if (wait(condition)) {
			log.debug(this.description()  + condition.description() + " finished successfully");
		}
	}

	protected abstract boolean wait(WaitCondition condition);

	protected abstract String description();

	protected TimePeriod getTimeout() {
		return timeout;
	}

	protected boolean isThrowWaitTimeoutExpiredException() {
		return throwWaitTimeoutExpiredException;
	}

	
	/** 
	 * Sleeps for given time period
	 * @param timePeriod given time period for thread will sleep
	 */
	public static void sleep(TimePeriod timePeriod) {
		try {
			Thread.sleep(timePeriod.getSeconds() * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}
	
	/**
	 * Sleeps for millis milliseconds
	 * 
	 * @param millis the time in milliseconds to sleep
	 * @deprecated use sleep(TimePeriod timePeriod) instead, will be removed in 0.6
	 */
	public static void sleep(long millis) {
		sleep(TimePeriod.getCustom(millis / 1000));
	}
}
