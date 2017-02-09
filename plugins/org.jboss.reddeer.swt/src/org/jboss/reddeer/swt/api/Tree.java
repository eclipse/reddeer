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

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for tree manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Tree extends Widget<org.eclipse.swt.widgets.Tree> {

	/**
	 * Gets top level tree items.
	 * 
	 * @return top level tree items
	 */
	List<TreeItem> getItems();

	/**
	 * Gets all tree items recursively.
	 * 
	 * @return all tree items
	 */
	List<TreeItem> getAllItems();

	/**
	 * Selects one or more tree items.
	 * 
	 * @param treeItems tree items to select
	 */
	void selectItems(TreeItem... treeItems);
	
	/**
	 * Gets the selected tree items.
	 * 
	 * @return tree items which are selected
	 */
	List<TreeItem> getSelectedItems();

	/**
	 * Sets focus on the tree.
	 */
	void setFocus();

	/**
	 * Unselects all selected items.
	 */
	void unselectAllItems();

	/**
	 * Gets count of columns in the tree.
	 * 
	 * @return count of columns
	 */
	int getColumnCount();
	
	/**
	 * Gets text of header columns in the tree.
	 * 
	 * @return list of String inside header columns
	 */
	List<String> getHeaderColumns();
}
