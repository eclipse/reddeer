/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.api;

import org.jboss.reddeer.swt.api.Shell;

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

}
