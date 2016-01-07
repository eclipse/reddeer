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
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for shell manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Shell extends Widget, ReferencedComposite {

	/**
	 * Returns title of the shell.
	 * 
	 * @return title of the shell.
	 */
	String getText();
	
	/**
	 * Check if shell is visible.
	 * @return true if shell is visible, false otherwise
	 */
	boolean isVisible();

	/**
	 * Sets focus on the shell.
	 */
	void setFocus();
	
	/**
	 * Checks if shell is focused.
	 * @return true if shell is focused, false otherwise
	 */
	boolean isFocused();

	/**
	 * Closes the shell.
	 */
	void close();
	
	org.eclipse.swt.widgets.Shell getSWTWidget();

}
