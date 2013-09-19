package org.jboss.reddeer.swt.api;


/**
 * API For List manipulation
 * @author Jiri Peterka
 *
 */
public interface List {
	
	/**
	 * Selects item in list
	 * @param listItem to select
	 */
	void select(String listItem);
	
	/**
	 * Selects item in list based on its index
	 * @param listItemIndex item index to select
	 */
	void select(int listItemIndex);	
	
	/**
	 * Gets all list items
	 * @return all items in list
	 */
	String[] getListItems();
	
	/**
	 * Deselects all list items
	 */
	void deselectAll();
	
	/**
	 * Selects items in list
	 * @param listItems to select
	 */
	void select(String... listItems);
	
	/**
	 * Selects items in list based on its indexes
	 * @param indices of items to select
	 */
	void select(int... indices);
	
	/**
	 * Selects all list items
	 */
	void selectAll();

}
