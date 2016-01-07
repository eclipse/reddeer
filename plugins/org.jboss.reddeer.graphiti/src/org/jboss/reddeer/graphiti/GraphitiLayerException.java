/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.graphiti;

import org.jboss.reddeer.gef.GEFLayerException;

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
