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
 * API for tab item manipulation.
 * 
 * @author apodhrad
 * 
 */
public interface TabItem extends Item<org.eclipse.swt.widgets.TabItem>, ReferencedComposite {

	/**
	 * Activates the tab item.
	 */
	void activate();
	
	/**
	 * Gets tooltip text of tab item
	 * @return tooltip text of tab item
	 */
	String getToolTipText();
	
	/**
	 * Gets parent of tab item
	 * @return TabFolder which is parent of tab item
	 */
	TabFolder getTabFolder();
	
	/**
	 * Checks if TabItem is selected
	 * @return true if tabItem is selected, false otherwise
	 */
	boolean isSelected();
}
