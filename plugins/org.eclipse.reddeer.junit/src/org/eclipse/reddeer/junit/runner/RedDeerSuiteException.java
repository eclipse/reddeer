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
package org.eclipse.reddeer.junit.runner;

/**
 * Exception thrown during RedDeer test suite processing. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerSuiteException extends RuntimeException {

	private static final long serialVersionUID = 4399019522052308797L;

	/**
	 * Instantiates a new red deer suite exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RedDeerSuiteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new red deer suite exception.
	 *
	 * @param message the message
	 */
	public RedDeerSuiteException(String message) {
		super(message);
	}
}
