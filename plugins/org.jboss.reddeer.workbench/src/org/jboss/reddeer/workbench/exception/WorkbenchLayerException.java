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
package org.jboss.reddeer.workbench.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

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
