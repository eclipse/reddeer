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
