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

/**
 * API for list manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface List extends Control<org.eclipse.swt.widgets.List> {

	/**
	 * Selects item in the list.
	 * 
	 * @param listItem label of the list item to select
	 */
	void select(String listItem);

	/**
	 * Selects an item in list at the specified position.
	 * Previously selected item(s) is/are deselected.
	 * 
	 * @param listItemIndex index of the item to select
	 */
	void select(int listItemIndex);

	/**
	 * Gets all list items.
	 * 
	 * @return all items in the list
	 */
	String[] getListItems();

	/**
	 * Deselects all list items.
	 */
	void deselectAll();

	/**
	 * Selects items in list.
	 * Previously selected item(s) is/are deselected.
	 * 
	 * @param listItems list items to select
	 */
	void select(String... listItems);

	/**
	 * Selects items in list at specified positions.
	 * Previously selected item(s) is/are deselected.
	 * 
	 * @param indices indices of items to select
	 */
	void select(int... indices);

	/**
	 * Gets selected list items.
	 * 
	 * @return array of strings representing selected items in list
	 */
	String[] getSelectedItems();
	
	/**
	 * Gets index of selected list item.
	 * 
	 * @return index of selected list item
	 */
	int getSelectionIndex();
	
	/**
	 * Gets indices of selected list items.
	 * 
	 * @return array of indices of selected list items
	 */
	int[] getSelectionIndices();
	
	/**
	 * Selects all list items.
	 */
	void selectAll();
}
