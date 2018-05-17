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
package org.eclipse.reddeer.junit.requirement;

/**
 * Exception thrown during requirements processing. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementException extends RuntimeException {

	private static final long serialVersionUID = 8655257087643600787L;

	/**
	 * Instantiates a new requirement exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RequirementException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new requirement exception.
	 *
	 * @param message the message
	 */
	public RequirementException(String message) {
		super(message);
	}
}
