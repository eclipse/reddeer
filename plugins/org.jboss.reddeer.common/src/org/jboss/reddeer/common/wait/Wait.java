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
