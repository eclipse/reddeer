package org.jboss.reddeer.swt.wait;

import org.apache.log4j.Logger;
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
	public AbstractWait(WaitCondition condition, TimePeriod timeout,
			boolean throwRuntimeException) {
		this.timeout = timeout;
		this.throwWaitTimeoutExpiredException = throwRuntimeException;
		log.info("Waiting with condition: " + condition.description()
				+ "\n  Timeout=" + timeout.getSeconds() + " seconds"
				+ "\n  Delay= " + AbstractWait.WAIT_DELAY + " milliseconds"
				+ "\n  Throw WaitTimeoutExpiredException="
				+ throwWaitTimeoutExpiredException);
		wait(condition);
		log.info("Waiting finished");
	}

	protected abstract void wait(WaitCondition condition);

	protected abstract String description();

	protected TimePeriod getTimeout() {
		return timeout;
	}

	protected boolean isThrowWaitTimeoutExpiredException() {
		return throwWaitTimeoutExpiredException;
	}

	/**
	 * Sleeps for millis milliseconds
	 * 
	 * @param millis
	 *            the time in milliseconds to sleep
	 */
	protected static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}
}
