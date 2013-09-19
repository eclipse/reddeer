package org.jboss.reddeer.swt.api;

import java.util.List;


/**
 * API for Table manipulation
 * @author Jiri Peterka
 *
 */
public interface Table {

	/**
	 * Returns table row count
	 * @return row count
	 */
	int rowCount();
	
	/**
	 * Select rows on given indexes 
	 * @param index given row indexes
	 */
	void select(int... indexes);
	/**
	 * Select rows
	 * @param items to be selected
	 */
	void select(String... items);
	
	/**
	 * Selects all table items
	 */
	void selectAll();

	/**
	 * Deselects all table items
	 */
	void deselectAll();

	/**
	 * Returns item in row
	 * @param row
	 * @return
	 */
	TableItem getItem(final int row);
	
	/**
	 * Returns item with given text
	 * @param itemText
	 * @return
	 */
	TableItem getItem(final String itemText);
	
	/**
	 * Returns all table items
	 * @return list of all table items
	 */
	List<TableItem> getItems();
	
}
