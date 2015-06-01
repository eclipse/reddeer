package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for list manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface List extends Widget {

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

	org.eclipse.swt.widgets.List getSWTWidget();

}
