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
package org.eclipse.reddeer.junit.configuration;

/**
 * Exception throws during configuration processing. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 2832066367510275146L;

	/**
	 * Instantiates a new red deer configuration exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RedDeerConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new red deer configuration exception.
	 *
	 * @param message the message
	 */
	public RedDeerConfigurationException(String message) {
		super(message);
	}
}
