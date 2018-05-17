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
package org.eclipse.reddeer.jface.exception;

import org.eclipse.reddeer.common.exception.RedDeerException;

/**
 * Thrown when an error can be identified on the JFace layer
 * 
 * @author Lucia Jelinkova
 * @since 0.6
 *
 */
public class JFaceLayerException extends RedDeerException {

	private static final long serialVersionUID = -3641202955039921211L;

	/**
	 * Instantiates a new j face layer exception.
	 *
	 * @param message the message
	 */
	public JFaceLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new j face layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public JFaceLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
