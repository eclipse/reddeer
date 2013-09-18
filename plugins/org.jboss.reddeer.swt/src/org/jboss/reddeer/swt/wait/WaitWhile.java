package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;

/**
 * Waits while condition is fulfilled
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public class WaitWhile extends AbstractWait {
	/**
	 * Waits while condition is fulfilled with timeout Throws
	 * WaitTimeoutExpiredException when timeout had expired
	 * 
	 * @param condition
	 */
	public WaitWhile(WaitCondition condition) {
		super(condition);
	}

	/**
	 * Waits while condition is fulfilled with default timeout Throws
	 * WaitTimeoutExpiredException when timeout had expired
	 * 
	 * @param condition
	 * @param timeout
	 */
	public WaitWhile(WaitCondition condition, TimePeriod timeout) {
		super(condition, timeout);
	}

	/**
	 * Waits while condition is fulfilled with timeout Throws
	 * WaitTimeoutExpiredException when timeout had expired and
	 * throwWaitTimeoutExpiredException is true
	 * 
	 * @param condition
	 * @param timeout
	 * @param throwWaitTimeoutExpiredException
	 */
	public WaitWhile(WaitCondition condition, TimePeriod timeout,
			boolean throwWaitTimeoutExpiredException) {
		super(condition, timeout, throwWaitTimeoutExpiredException);
	}

	/**
	 * Waits while condition is fulfilled with timeout Throws
	 * WaitTimeoutExpiredException when timeout had expired and
	 * throwWaitTimeoutExpiredException is true
	 * 
	 * @param condition
	 * @param timeout
	 * @param throwWaitTimeoutExpiredException
	 * @return returns true when waiting finished successfully
	 */
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
				if (!condition.test())
					return true;
			} catch (Throwable e) {
				log.warn("Error during evaluating wait condition " + condition.description() 
						+ " " + e);
			}
			sleep(AbstractWait.WAIT_DELAY);
			if (System.currentTimeMillis() > limit) {
				continueSleep = false;
				if (isThrowWaitTimeoutExpiredException()) {
					throw new WaitTimeoutExpiredException("Timeout after: "
							+ timeout + " ms.: " + condition.description());
				}
			}
		}
		return false;
	}

	@Override
	protected String description() {
		return "waiting while: ";
	}
}
