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
package org.eclipse.reddeer.common.exception;

/**
 * RedDeer adaptable exception for handling exceptional situation when using RedDeer
 * adaptable objects.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public class AdaptableException extends RedDeerException {
	
	private static final long serialVersionUID = 12345685213545L;
	
	/**
	 * Creates AdaptableException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public AdaptableException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new AdaptableException with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public AdaptableException(String message, Throwable cause) {
		super(message, cause);
	}
}
