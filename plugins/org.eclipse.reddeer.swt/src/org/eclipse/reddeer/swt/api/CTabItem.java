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
 * API for CTab item manipulation.
 * 
 * @author Vlado Pakan
 *
 */
public interface CTabItem extends Item<org.eclipse.swt.custom.CTabItem>, ReferencedComposite {

	/**
	 * Activates CTab item.
	 */
	void activate();

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

	/**
	 * Returns parent folder {@link CTabFolder}
	 * @return parent folder
	 */
	CTabFolder getFolder();
	
	/**
	 * Gets tooltip text of CTabItem
	 * @return tooltip text of CTabItem
	 */
	String getToolTipText();
	
	/**
	 * Checks if tab item is active
	 * @return
	 */
	boolean isActive();
}
