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

public class WaitProvider {
	
	/**
	 * Gets wrapped wait with wait while flag.
	 * 
	 * @param waitCondition condition to wait while
	 * @return wrapped wait
	 */
	public static WaitWrapper waitWhile(WaitCondition waitCondition) {
		return new WaitWrapper(waitCondition, WaitType.WHILE);
	}
	
	/**
	 * Gets wrapped wait with wait until flag.
	 * 
	 * @param waitCondition condition to wait until
	 * @return wrapped wait
	 */
	public static WaitWrapper waitUntil(WaitCondition waitCondition) {
		return new WaitWrapper(waitCondition, WaitType.UNTIL);
	}
}
