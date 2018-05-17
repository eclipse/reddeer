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
package org.eclipse.reddeer.core.exception;

import org.eclipse.reddeer.common.exception.RedDeerException;
/**
 * RedDeer CoreLayerException indicates exceptional situation at RedDeer core layer.
 * 
 * @author Jiri Peterka
 *
 */
public class CoreLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new CoreLayerException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public CoreLayerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new CoreLayerException with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public CoreLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new CoreLayerException with the specified detail
	 * message, cause and messageDetails.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 * @param messageDetails details of message
	 */
	public CoreLayerException(String message, Throwable cause,
			String[] messageDetails) {
		super(message, cause);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Constructs a new CoreLayerException with the specified detail
	 * message and messageDetails.
	 * 
	 * @param message the detail message
	 * @param messageDetails details of message
	 */
	public CoreLayerException(String message, String[] messageDetails) {
		super(message);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
