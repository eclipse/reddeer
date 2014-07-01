package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;

/**
 * WaitWhile condition represent a wait condition waiting while specific
 * condition is met.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public class WaitWhile extends AbstractWait {
	
	/**
	 * Waits while condition is fulfilled for a default time period.Throws
	 * WaitTimeoutExpiredException if condition is still met and time period expires.
	 * 
	 * @param condition condition to be met while waiting
	 */
	public WaitWhile(WaitCondition condition) {
		super(condition);
	}

	/**
	 * Waits while condition is fulfilled for specified time period.Throws
	 * WaitTimeoutExpiredException if condition is still met and time period expires.
	 * 
	 * @param condition condition to be met while waiting
	 * @param timeout time period to wait for
	 */
	public WaitWhile(WaitCondition condition, TimePeriod timeout) {
		super(condition, timeout);
	}

	/**
	 * Waits while condition is fulfilled for specified time period. Can throw
	 * WaitTimeoutExpiredException if condition is still met and time period expires.
	 * 
	 * @param condition condition to be met while waiting
	 * @param timeout time period to wait for
	 * @param throwWaitTimeoutExpiredException whether exception
	 * should be thrown or not
	 */
	public WaitWhile(WaitCondition condition, TimePeriod timeout,
			boolean throwWaitTimeoutExpiredException) {
		super(condition, timeout, throwWaitTimeoutExpiredException);
	}

	@Override
	protected boolean wait(WaitCondition condition) {
		final long timeout = getTimeout().getSeconds() * 1000;
		if (timeout < 0) {
		    log.warn("timeout value is negative, wait might never ends");
		}
		if (AbstractWait.WAIT_DELAY < 0) {
			throw new SWTLayerException("wait delay value is negative");
		}
		long limit = System.currentTimeMillis() + timeout;
		boolean continueSleep = true;
		while (continueSleep) {
			try {
				if (!condition.test()) {
					return true;
				}
			} catch (Throwable e) {
				log.warn("Error during evaluating wait condition " + condition.description() 
						+ " " + e);
			}
			sleep(TimePeriod.SHORT);
			if (timeout >= 0 && System.currentTimeMillis() > limit) {
				continueSleep = false;
				if (isThrowWaitTimeoutExpiredException()) {
					log.debug(this.description()  + condition.description() + " failed, an exception will be thrown");
					throw new WaitTimeoutExpiredException("Timeout after: "
							+ timeout + " ms.: " + condition.description());
				} else {
					log.debug(this.description()  + condition.description() + " failed, an exception will not be thrown");
				}
			}
		}
		return false;
	}

	@Override
	protected String description() {
		return "Waiting while ";
	}
}
