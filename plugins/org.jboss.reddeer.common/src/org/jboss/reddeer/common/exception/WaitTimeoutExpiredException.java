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
package org.jboss.reddeer.common.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * WaitTimeoutExpiredException indicates reaching timeout time period.
 * 
 * @author Vlado Pakan
 * 
 */
public class WaitTimeoutExpiredException extends RedDeerException {

	private static final long serialVersionUID = 5905873761753380173L;
	
	/**
	 * Creates WaitTimeoutExpiredException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public WaitTimeoutExpiredException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new WaitTimeoutExpiredException with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public WaitTimeoutExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
