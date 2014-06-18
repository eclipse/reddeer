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
	 * 
	 * @param listItems list items to select
	 */
	void select(String... listItems);

	/**
	 * Selects items in list at specified positions.
	 * 
	 * @param indices indices of items to select
	 */
	void select(int... indices);

	/**
	 * Selects all list items.
	 */
	void selectAll();

	org.eclipse.swt.widgets.List getSWTWidget();

}
