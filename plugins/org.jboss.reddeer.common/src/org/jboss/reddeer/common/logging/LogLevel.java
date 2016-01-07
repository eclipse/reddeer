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
package org.jboss.reddeer.common.logging;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Log level enumeration. Values are set according to convention of log4j
 * @author Jiri Peterka
 *
 */
public enum LogLevel {

	OFF(0),FATAL(100),ERROR(200), DUMP(250),STEP(250), WARN(300),INFO(400),DEBUG(500),TRACE(600),ALL(Integer.MAX_VALUE);
	
	private final int value;

	/**
	 * Initialize value of LogLevel from int.
	 *
	 * @param value given int value
	 */
	LogLevel(int value) {
		this.value = value;
	}
	
	/**
	 * Return value.
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Return log level value as a string.
	 *
	 * @return the string
	 */
	public String toString() {
		return new Integer(value).toString();
	}
	
	/**
	 * Return LogLevel from string value .
	 *
	 * @param string log level
	 * @return LogLevel based on string
	 */
	public LogLevel fromString(String string) {
		String upper = string.toUpperCase();
		
		switch (upper) {
			case "OFF": return OFF;
			case "FATAL": return FATAL;
			case "ERROR" : return ERROR;
			case "STEP" : return STEP;
			case "WARN" : return WARN;
			case "INFO" : return INFO;
			case "DEBUG" : return DEBUG;
			case "TRACE" : return TRACE;
			case "ALL" : return ALL;
			default: throw new RedDeerException("Unsupported log level value" + upper);
		}
	}
}
