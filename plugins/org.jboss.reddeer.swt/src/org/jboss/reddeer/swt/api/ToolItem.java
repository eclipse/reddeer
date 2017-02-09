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
 * API for tool bar item manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface ToolItem extends Widget<org.eclipse.swt.widgets.ToolItem> {

	/**
	 * Clicks the tool item.
	 */
	void click();

	/**
	 * Gets ToolTip text of the tool item.
	 * 
	 * @return text of ToolTip
	 */
	String getToolTipText();

	/**
	 * Returns whether the tool item is selected or not.
	 * 
	 * @return true if the tool item is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Toggles button of the the tool item.
	 * 
	 * @param toggle the button of the tool item or not
	 */
	void toggle(boolean toggle);
}
