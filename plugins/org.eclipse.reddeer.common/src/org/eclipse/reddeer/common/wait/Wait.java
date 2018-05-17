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

/**
 * Wait interface defining behavior of waitings.
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * @author mlabuda@redhat.com
 * 
 */
public interface Wait {
	
	/**
	 * Waits for a wait condition to met.
	 * 
	 * @param condition wait condition to met 
	 * @param testPeriod test tick period in milliseconds
	 */
	public void wait(WaitCondition condition, long testPeriod);
	
	/**
	 * Gets info whether a condition should stop waiting or not.
	 * 
	 * @param condition
	 *            wait condition to met
	 * @return true if the while loop should continue, false otherwise
	 */
	public boolean stopWaiting(WaitCondition condition);
	
	/**
	 * Gets description of specific wait condition. Description should provide
	 * information about nature of wait condition followed by space character.
	 * Purpose of this method is logging.
	 * 
	 * @return description of specific wait condition
	 */
	public String description();
}
