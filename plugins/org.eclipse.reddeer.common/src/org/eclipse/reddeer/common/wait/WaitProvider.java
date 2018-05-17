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
