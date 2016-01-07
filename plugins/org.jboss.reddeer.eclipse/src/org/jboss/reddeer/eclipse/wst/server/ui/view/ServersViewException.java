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
package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;

/**
 * Thrown when an unexpected state or operation is detected on {@link ServersView} 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServersViewException extends EclipseLayerException {

	private static final long serialVersionUID = -5850409602777204031L;

	/**
	 * Instantiates a new servers view exception.
	 *
	 * @param message the message
	 */
	public ServersViewException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new servers view exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ServersViewException(String message, Throwable cause) {
		super(message, cause);
	}
}
