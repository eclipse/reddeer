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

import org.hamcrest.Matcher;

/**
 * API for tree manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Tree extends Control<org.eclipse.swt.widgets.Tree> {

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
	 * Returns tree item with given path
	 * @param itemPath path of tree item to get
	 * @return tree item with given path
	 */
	TreeItem getItem(String... itemPath);
	
	/**
	 * Returns tree item matching given matchers
	 * @param itemMatchers matchers matching tree item
	 * @return tree item matching given matchers
	 */
	@SuppressWarnings("unchecked")
	TreeItem getItem(Matcher<org.eclipse.swt.widgets.TreeItem>... itemMatchers);

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
