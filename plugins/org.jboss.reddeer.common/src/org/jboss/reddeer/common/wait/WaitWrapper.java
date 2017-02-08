/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
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

/**
 * Wrapper for waits which looks like implementation of abstract wait,
 * but it does not have any waiting included.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class WaitWrapper extends AbstractWait {
	
	private WaitCondition waitCondition;
	private WaitType waitType;
	private boolean throwRuntimeException;
	
	/**
	 * Constructs a new wait wrapper. By default,
	 * runtime exception of waiting is thrown.
	 * 
	 * @param waitCondition wait condition to wrap
	 * @param waitType wait type
	 */
	public WaitWrapper(WaitCondition waitCondition, WaitType waitType) {
		this(waitCondition, waitType, true);
	}
	
	/**
	 * Constructs a new wait wrapper.
	 * 
	 * @param waitCondition wait condition to wrap
	 * @param waitType wait type
	 * @param throwRuntimeException whether a wait timeout expired exception should be thrown or not
	 */
	public WaitWrapper(WaitCondition waitCondition, WaitType waitType, boolean throwRuntimeException) {
		super(waitCondition);
		this.waitType = waitType;
		this.waitCondition = waitCondition;
		this.throwRuntimeException = throwRuntimeException;
	}
	
	/**
	 * Gets wrapped waiting condition
	 * 
	 * @return wrapped waiting condition
	 */
	public WaitCondition getWaitCondition() {
		return waitCondition;
	}
	
	/**
	 * Gets type of a wait (UNTIL|WHILE).
	 * @return type of a waiting
	 */
	public WaitType getWaitType() {
		return waitType;
	}
	
	/**
	 * Finds out whether a runtime exception should be thrown or not if wait time outs.
	 * 
	 * @return true if exception should be thrown, false otherwise.
	 */
	public boolean throwRuntimeException() {
		return throwRuntimeException;
	}
	
	@Override
	public boolean stopWaiting(WaitCondition condition) {
		return true;
	}

	@Override
	public String description() {
		return "";
	}
	
	@Override
	public void wait(WaitCondition condition, long testPeriod) {
		// NOTHING TO WAIT FOR
	}
}
