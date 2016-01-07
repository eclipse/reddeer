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
package org.jboss.reddeer.requirements.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Thrown when some error had appeared on requirements layer. Typically when
 * requirement couldn't be fulfilled (even though canFulfill returned true)
 * 
 * @author rhopp
 */
public class RequirementsLayerException extends RedDeerException {

	private static final long serialVersionUID = 6490745570893239529L;
	
	/**
	 * Constructs a new requirements layer exception with the specified detail
	 * message.
	 * 
	 * @param message the specified detail message
	 * 
	 * @see RuntimeException#RuntimeException(String)
	 */
	public RequirementsLayerException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new requirements layer exception with the specified detail
	 * message and cause.
	 * 
	 * @param message the specified detail message
	 * @param cause the cause
	 * 
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public RequirementsLayerException(String message, Throwable cause) {
		super(message, cause);
	}

}
