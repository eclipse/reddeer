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
package org.eclipse.reddeer.graphiti;

import org.eclipse.reddeer.gef.GEFLayerException;

/**
 * Graphiti Layer Exception
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GraphitiLayerException extends GEFLayerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 */
	public GraphitiLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GraphitiLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param aMessageDetails the a message details
	 */
	public GraphitiLayerException(String message, Throwable cause, String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 * @param aMessageDetails the a message details
	 */
	public GraphitiLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
