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

import java.util.List;

/**
 * API for expand bar manipulation.
 * 
 * @author Vlado Pakan
 *
 */
public interface ExpandBar extends Control<org.eclipse.swt.widgets.ExpandBar> {
	
	/**
	 * Finds out how many expand bar items are nested in the expand bar.
	 * 
	 * @return number of expand bar items in expand bar
	 */
	int getItemsCount();

	/**
	 * Returns expand bar items items nested in the expand bar.
	 * 
	 * @return list of expand bar items in the expand bar
	 */
	List<ExpandItem> getItems();

	/**
	 * Sets focus on the expand bar.
	 */
	void setFocus();

	/**
	 * Expands all expand bar items nested in the expand bar.
	 */
	void expandAll();

	/**
	 * Collapses all expand bar items nested in the expand bar. 
	 */
	void collapseAll();
}
