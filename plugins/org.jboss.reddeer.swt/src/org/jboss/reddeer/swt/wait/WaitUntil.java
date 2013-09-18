package org.jboss.reddeer.swt.wait;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;

/**
 * Waits until condition is fulfilled
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public class WaitUntil extends AbstractWait {
	private final Logger log = Logger.getLogger(this.getClass());
	/**
	 * Waits until condition is fulfilled with default timeout
	 * Throws WaitTimeoutExpiredException when timeout had expired
	 * @param condition
	 */
	public WaitUntil(WaitCondition condition) {
		super(condition);
	}
	/**
	 * Waits until condition is fulfilled with timeout
	 * Throws WaitTimeoutExpiredException when timeout had expired 
	 * @param condition
	 * @param timeout
	 */
	public WaitUntil(WaitCondition condition, TimePeriod timeout) {
		super(condition, timeout);
	}
	/**
	 * Waits until condition is fulfilled with timeout
	 * Throws WaitTimeoutExpiredException when timeout had expired
	 * and throwWaitTimeoutExpiredException is true
	 * @param condition
	 * @param timeout
	 * @param throwWaitTimeoutExpiredException
	 * @return returns true when waiting finished successfully
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
				if (condition.test())
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
		return "waiting until: ";
	}
}
