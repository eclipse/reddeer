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
package org.eclipse.reddeer.jface.api;

import org.eclipse.reddeer.swt.api.Shell;

/**
 * API for Window manipulation
 * @author rawagner
 *
 */
public interface Window {
	
	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	Shell getShell();
	
	/**
	 * Returns Eclipse counterpart class
	 * 
	 * @return Eclipse counterpart class
	 */
	Class<?> getEclipseClass();
	
	/**
	 * Sets shell which is encapsulated by window
	 * @param shell to set
	 */
	void setShell(Shell shell);
	
	/**
	 * Opens the window
	 */
	void open();
	
	/**
	 * Checks if window if open
	 * @return true if window is open, false otherwise
	 */
	boolean isOpen();

}
