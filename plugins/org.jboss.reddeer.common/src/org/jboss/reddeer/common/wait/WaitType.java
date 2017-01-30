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
