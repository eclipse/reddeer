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

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for button (push, radio and toggle) manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Button extends Widget {

	/**
	 * Performs click on the button.
	 */
	void click();

	/**
	 * Returns text on the button.
	 * 
	 * @return text on the button
	 */
	String getText();

	/**
	 * Returns the ToolTip of the button.
	 * 
	 * @return the ToolTip text on the button
	 */
	String getToolTipText();

	/**
	 * Finds out whether button is enabled or not.
	 * 
	 * @return true if this button is enabled, false otherwise
	 */
	boolean isEnabled();

	org.eclipse.swt.widgets.Button getSWTWidget();
}
