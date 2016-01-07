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
 * API for CTab item manipulation.
 * 
 * @author Vlado Pakan
 *
 */
public interface CTabItem extends Widget {

	/**
	 * Activates CTab item.
	 */
	void activate();

	/**
	 * Returns the text of the CTab item.
	 * 
	 * @return text on the CTab item
	 */
	String getText();

	/**
	 * Returns the ToolTip text of the CTab item.
	 * 
	 * @return ToolTip text of the CTab item
	 */
	String getToolTipText();

	/**
	 * Closes CTabItem.
	 */
	void close();

	/**
	 * Find outs whether the close button should be shown or not. 
	 * 
	 * @return true if the close button should be shown
	 */
	boolean isShowClose();
	
	/**
	 * Returns true if the tab is visible
	 * @return true if the tab is visible
	 */
	boolean isShowing();

	org.eclipse.swt.custom.CTabItem getSWTWidget();
	
	/**
	 * Returns parent folder {@link CTabFolder}
	 * @return parent folder
	 */
	CTabFolder getFolder();
}
