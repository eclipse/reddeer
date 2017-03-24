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
package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.core.reference.ReferencedComposite;

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
