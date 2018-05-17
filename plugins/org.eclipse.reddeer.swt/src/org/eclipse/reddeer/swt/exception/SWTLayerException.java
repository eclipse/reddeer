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
package org.eclipse.reddeer.swt.exception;

import org.eclipse.reddeer.common.exception.RedDeerException;
/**
 * SWTLayerException indicates exceptional situation on the SWT Layer.
 * 
 * @author Jiri Peterka
 */

public class SWTLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new SWTLayerException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public SWTLayerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new SWTLayerException with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public SWTLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new SWTLayerException with the specified detail
	 * message, cause and messageDetails.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 * @param messageDetails details of message
	 */
	public SWTLayerException(String message, Throwable cause,
			String[] messageDetails) {
		super(message, cause);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Constructs a new SWTLayerException with the specified detail
	 * message and messageDetails.
	 * 
	 * @param message the detail message
	 * @param messageDetails details of message
	 */
	public SWTLayerException(String message, String[] messageDetails) {
		super(message);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
