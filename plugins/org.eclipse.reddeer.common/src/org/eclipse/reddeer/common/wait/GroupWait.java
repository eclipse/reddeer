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

import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;

/**
 * Group wait consists of various waits in a sequence execution with a common timeout.
 * Group wait can be used for various processes/actions as a complex wait consisting
 * of several waitings with 1 shared(decreasing) timeout. If timeout time outs and 
 * not all waitings passed yet, {@link WaitTimeoutExpiredException} is thrown.
 *  
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public class GroupWait {

	private TimePeriod remainingTimeout;
	
	/**
	 * Creates a new group wait with progressive timeout (decreasing) and various wrapped waits.
	 * 
	 * @param timeout shared timeout for all waitings
	 * @param waitings waitings to wait
	 */
	public GroupWait(TimePeriod timeout, WaitWrapper... waitings) {
		remainingTimeout = timeout;
		if (waitings == null || waitings.length == 0) {
			throw new IllegalArgumentException("There are no waiting to wait for, pass one or more waitings "
					+ "as a constructor arguments to start waiting");
		}
		for (WaitWrapper waitWrapper: waitings) {
			long startTimeOfWait = System.currentTimeMillis();
			if (WaitType.WHILE.equals(waitWrapper.getWaitType())) {
				new WaitWhile(waitWrapper.getWaitCondition(), remainingTimeout, waitWrapper.throwRuntimeException());
				
			} else if (WaitType.UNTIL.equals(waitWrapper.getWaitType())) {
				new WaitUntil(waitWrapper.getWaitCondition(), remainingTimeout, waitWrapper.throwRuntimeException());
			}
			remainingTimeout = getRemainingTimeoutPeriod(remainingTimeout, startTimeOfWait);
		}
	}
	
	/**
	 * Creates a new group wait with progressive TimePeriod.NORMAL timeout (decreasing) and
	 * various wrapped waits.
	 *  
	 * @param waitings waiting to wait
	 */
	public GroupWait(WaitWrapper... waitings) {
		this(TimePeriod.DEFAULT, waitings);
	}
	
	/**
	 * Gets timeout which remained after all waitings.
	 * 
	 * @return remaining timeout
	 */
	public TimePeriod getRemainingTimeout() {
		return remainingTimeout;
	}
	
	private TimePeriod getRemainingTimeoutPeriod(TimePeriod oldTimeout, long startTimeOfWait) {
		long diffTime = Math.round((System.currentTimeMillis() - startTimeOfWait) / 1000);
		long remainingTimeout = oldTimeout.getSeconds() - diffTime;
		if (remainingTimeout <= 0) {
			return TimePeriod.NONE;
		}
		return TimePeriod.getCustom(remainingTimeout);
	}
}
