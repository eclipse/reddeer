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
 * API for tab folder manipulation.
 * 
 * @author Jiri Peterka
 * @author Andrej Podhradsky
 *
 */
public interface TabFolder extends Control<org.eclipse.swt.widgets.TabFolder> {

	/**
	 * Gets tab item labels.
	 * 
	 * @return labels of the tab item
	 */
	String[] getTabItemLabels();
	
	/**
	 * Gets selected tab items
	 * @return selected tab items
	 */
	List<TabItem> getSelection();
	
	/**
	 * Gets tab items of tab folder
	 * @return tab items of tab folder
	 */
	List<TabItem> getItems();
}
