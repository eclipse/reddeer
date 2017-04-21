/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.requirement.inject;

/**
 * Exception thrown during RedDeer requirements injection processing. 
 * 
 * @author jjankovi
 *
 */
public class RequirementInjectionException extends RuntimeException {

	private static final long serialVersionUID = -2739126612283805953L;

	/**
	 * Instantiates a new requirement injection exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RequirementInjectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new requirement injection exception.
	 *
	 * @param message the message
	 */
	public RequirementInjectionException(String message) {
		super(message);
	}

}
