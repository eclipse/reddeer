/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.common.wait;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.util.Display;

/**
 * Common ancestor for waiting classes. Contains abstract
 * {@link #stopWaiting(WaitCondition)} method that is called in the constructor.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public abstract class AbstractWait {
	
	/**
	 * Wait logger.
	 */
	private static final Logger log = Logger.getLogger(AbstractWait.class);

	private TimePeriod timeout;

	private boolean throwTimeoutException = true;

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
	 * @throws WaitTimeoutExpiredException the wait timeout expired exception
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod,
			boolean throwRuntimeException) {
		this(condition, timePeriod, throwRuntimeException, TimePeriod.SHORT);
	}
	
	/**
	 * Waits till condition is met for specified timeout period. There is a
	 * possibility to turn on/off throwing a exception. This constructor also 
	 * allows to set custom test period - time elapsed before another execution
	 * of a wait condition is performed.
	 *
	 * @param condition wait condition to met
	 * @param timePeriod time period to wait
	 * @param throwRuntimeException whether exception should be thrown after
	 * expiration of the period
	 * @param testPeriod time to wait before another testing of a wait
	 * condition is performed
	 * @throws WaitTimeoutExpiredException the wait timeout expired exception
	 */
	public AbstractWait(WaitCondition condition, TimePeriod timePeriod, 
			boolean throwRuntimeException, TimePeriod testPeriod) {
		if(condition == null) {
			throw new IllegalArgumentException("condition can't be null");
		}
		if(timePeriod == null) {
			throw new IllegalArgumentException("timePeriod can't be null");
		}
		if (testPeriod == null) {
			throw new IllegalArgumentException("testPeriod cannot be null.");
		}
		this.timeout = timePeriod;
		this.throwTimeoutException = throwRuntimeException;
		wait(condition, testPeriod);
	}

	/**
	 * Condition if the waiting should stop. 
	 * 
	 * @param condition wait condition to met
	 * @return true if the while loop should continue, false otherwise
	 */
	protected abstract boolean stopWaiting(WaitCondition condition);

	/**
	 * Gets description of specific wait condition. Description should
	 * provide information about nature of wait condition followed by space
	 * character. Purpose of this method is logging.
	 * 
	 * @return description of specific wait condition
	 */
	protected abstract String description();
	
	private void wait(WaitCondition condition, TimePeriod testPeriod) {
		log.debug(this.description() + condition.description() + "...");
		
		long limit;
		if((Long.MAX_VALUE - System.currentTimeMillis()) / 1000 > getTimeout().getSeconds()){
			limit = System.currentTimeMillis() + getTimeout().getSeconds() * 1000;
		} else {
			limit = Long.MAX_VALUE;
		}

		while (true) {
			if (stopWaiting(condition)){
				break;
			}
			
			if (timeoutExceeded(condition, limit)){
				return;
			}
			
			sleep(testPeriod);
		}
		
		log.debug(this.description() + condition.description()
				+ " finished successfully");
	}

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
	protected boolean throwTimeoutException() {
		return throwTimeoutException;
	}

	/**
	 * Sleeps for specified time period.
	 * 
	 * @param timePeriod time period to sleep
	 */
	public static void sleep(TimePeriod timePeriod) {
		if(Thread.currentThread().equals(Display.getDisplay().getThread())) {
			throw new RuntimeException("Tried to execute sleep in UI thread!");
		}
		try {
			Thread.sleep(timePeriod.getSeconds() * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}

	private boolean timeoutExceeded(WaitCondition condition, long limit) {
		if (System.currentTimeMillis() > limit) {
			if (throwTimeoutException()) {
				log.debug(this.description()  + condition.description() + " failed, an exception will be thrown");
				throw new WaitTimeoutExpiredException("Timeout after: "
						+ timeout.getSeconds() + " s.: " + condition.errorMessage());
			} else {
				log.debug(this.description()  + condition.description() + " failed, NO exception will be thrown");
				return true;
			}
		}
		return false;
	}
}