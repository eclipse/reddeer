/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.wait;

import org.eclipse.reddeer.common.condition.WaitCondition;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;

/**
 * WaitWhile condition represents a wait condition waiting while specific
 * condition is met.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * @author jkopriva@redhat.com
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
	 * @param throwWaitTimeoutExpiredException whether exception
	 * should be thrown or not
	 */
	public WaitWhile(WaitCondition condition, boolean throwWaitTimeoutExpiredException) {
		super(condition, throwWaitTimeoutExpiredException);
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
