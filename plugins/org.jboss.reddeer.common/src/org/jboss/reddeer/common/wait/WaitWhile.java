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
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * WaitWhile condition represents a wait condition waiting while specific
 * condition is met.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * @contributor jkopriva@redhat.com
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

	/**
	 * Waits while condition is fulfilled for specified time period. Can throw
	 * WaitTimeoutExpiredException if condition is still met and time period expires.
	 * This constructor also allows to set custom test period - time elapsed before 
	 * another execution of a wait condition is performed.
	 * 
	 * @param condition condition to be met while waiting
	 * @param timeout time period to wait for
	 * @param throwWaitTimeoutExpiredException whether exception
	 * should be thrown or not
	 * @param testPeriod time to wait before another testing of a wait
	 * condition is performed
	 */
	public WaitWhile(WaitCondition condition, TimePeriod timeout,
			boolean throwWaitTimeoutExpiredException, long testPeriod) {
		super(condition, timeout, throwWaitTimeoutExpiredException, testPeriod);
	}
	
	@Override
	public boolean stopWaiting(WaitCondition condition) {
		return !condition.test();
	}

	@Override
	public String description() {
		return "Waiting while ";
	}
	
	/**
	 * Throws an exception, if timeout exceeded and creates error message. 
	 * Calls errorMessageWhile of condition.
	 * 
	 * @param timeout
	 *            exceeded timeout
	 * @param condition
	 *            failed wait condition
	 */
	@Override
	protected void throwWaitTimeOutException(TimePeriod timeout, WaitCondition condition) {
		throw new WaitTimeoutExpiredException(
				"Timeout after: " + timeout.getSeconds() + " s.: " + condition.errorMessageWhile());
	}
}
