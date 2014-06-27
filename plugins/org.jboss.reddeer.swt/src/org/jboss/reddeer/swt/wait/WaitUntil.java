package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;

/**
 * WaitUntil condition represent a wait condition waiting until specific
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
	protected boolean wait(WaitCondition condition) {
		final long timeout = getTimeout().getSeconds() * 1000;
		if (timeout < 0) {
			throw new SWTLayerException("timeout value is negative");
		}
		if (AbstractWait.WAIT_DELAY < 0) {
			throw new SWTLayerException("wait delay value is negative");
		}
		long limit = System.currentTimeMillis() + timeout;
		boolean continueSleep = true;
		while (continueSleep) {
			try {
				if (condition.test()) {
					return true;
				}
			} catch (Throwable e) {
				log.warn("Error during evaluating wait condition "
						+ condition.description() + " " + e);
			}
			sleep(TimePeriod.SHORT);
			if (System.currentTimeMillis() > limit) {
				continueSleep = false;
				if (isThrowWaitTimeoutExpiredException()) {
					log.debug(this.description() + condition.description()
							+ " failed, an exception will be thrown");
					throw new WaitTimeoutExpiredException("Timeout after: "
							+ timeout + " ms.: " + condition.description());
				} else {
					log.debug(this.description() + condition.description()
							+ " failed, an exception will not be thrown");
				}
			}
		}
		return false;
	}

	@Override
	protected String description() {
		return "Waiting until ";
	}
}
