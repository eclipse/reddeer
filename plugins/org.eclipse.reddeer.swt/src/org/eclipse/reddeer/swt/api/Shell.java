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
package org.eclipse.reddeer.swt.api;

import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * API for shell manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Shell extends Control<org.eclipse.swt.widgets.Shell>, ReferencedComposite {

	/**
	 * Returns title of the shell.
	 * 
	 * @return title of the shell.
	 */
	String getText();

	/**
	 * Closes the shell
	 */
	void close();
	
	
	/**
	 * Maximize shell
	 */
	void maximize();
	
	/**
	 * Minimize shell
	 */
	void minimize();
	
	/**
	 * Restore shell
	 */
	void restore();
	
	/**
	 * Check if shell is maximized.
	 * @return true if shell is maximized, false otherwise
	 */
	boolean isMaximized();
	
	/**
	 * Check if shell is minimized.
	 * @return true if shell is minimized, false otherwise
	 */
	boolean isMinimized();
}
