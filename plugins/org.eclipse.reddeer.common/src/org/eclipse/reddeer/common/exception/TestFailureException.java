/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
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
 * TestFailureException is for failing tests
 * 
 * @author jkopriva@redhat.com
 */
public class TestFailureException extends RedDeerException {
	
	private static final long serialVersionUID = 12345685545L;
	
	/**
	 * Creates TestFailureException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public TestFailureException(String message) {
		super(message);
	}
}
