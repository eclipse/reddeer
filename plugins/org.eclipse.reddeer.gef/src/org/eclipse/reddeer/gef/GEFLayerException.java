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
package org.eclipse.reddeer.gef;

import org.eclipse.reddeer.common.exception.RedDeerException;

/**
 * GEF Layer Exception
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GEFLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 */
	public GEFLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GEFLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param aMessageDetails the a message details
	 */
	public GEFLayerException(String message, Throwable cause, String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 * @param aMessageDetails the a message details
	 */
	public GEFLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
