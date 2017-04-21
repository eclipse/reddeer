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
package org.eclipse.reddeer.eclipse.exception;

import org.eclipse.reddeer.common.exception.RedDeerException;

/**
 * Thrown when an error can be identified on the Eclipse layer (e.g. something is not found on a view)
 * 
 * @author Lucia Jelinkova
 *
 */
public class EclipseLayerException extends RedDeerException {

	private static final long serialVersionUID = 3457199665187648827L;

	/**
	 * Instantiates a new eclipse layer exception.
	 *
	 * @param message the message
	 */
	public EclipseLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new eclipse layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public EclipseLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
