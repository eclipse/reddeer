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
package org.eclipse.reddeer.workbench.exception;

import org.eclipse.reddeer.common.exception.RedDeerException;

/**
 * Universal exception for workbench layer.
 */
public class WorkbenchLayerException extends RedDeerException {

	private static final long serialVersionUID = 2815336044156846700L;
	
	/**
	 * Instantiates a new workbench layer exception.
	 *
	 * @param message the message
	 */
	public WorkbenchLayerException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new workbench layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public WorkbenchLayerException(String message, Throwable cause){
		super(message, cause);
	}

}
