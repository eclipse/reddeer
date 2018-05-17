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

/**
 * Waiting types. Currently we have wait until and wait while.
 * 
 * @author mlabuda@redhat.com
 */
public enum WaitType {

	WHILE("WHILE"), UNTIL("UNTIL");
	
	private String type;
	
	private WaitType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets waiting type from its text representation
	 * 
	 * @param type type of waiting
	 * @return enum of waiting type
	 */
	public WaitType getType(String type) {
		if (type.toUpperCase().equals(WHILE)) {
			return WHILE;
		} else {
			return UNTIL;
		}
	}
	
	/**
	 * Gets text of waiting type
	 * @return string representing waiting type
	 */
	public String getTypeText() {
		return type;
	}
}
