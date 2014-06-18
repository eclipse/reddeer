package org.jboss.reddeer.swt.api;

import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for table manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Table extends Widget {

	/**
	 * Returns true if table contains item with specified text.
	 * 
	 * @param item text of the item to find
	 * @return true if table contains item with the specified name, false otherwise
	 */
	boolean containsItem(String item);

	/**
	 * Returns whether the table contains item with specified text in given cell or not.
	 * 
	 * @param item item to find at specific position
	 * @param cellIndex index of the cell where to look for item
	 * @return true if the table contains specified item, false otherwise
	 */
	boolean containsItem(String item, int cellIndex);

	/**
	 * Returns table row count.
	 * 
	 * @return row count of the table
	 */
	int rowCount();

	/**
	 * Selects rows with specified indices.
	 * 
	 * @param indices indices row indices to select
	 */
	void select(int... indices);

	/**
	 * Selects rows with items with specified texts.
	 * 
	 * @param items texts of items to select
	 */
	void select(String... items);

	/**
	 * Selects all table items.
	 */
	void selectAll();

	/**
	 * Deselects all table items.
	 */
	void deselectAll();

	/**
	 * Returns item in specific row.
	 * 
	 * @param row index of the row
	 * @return table item in the specified row
	 */
	TableItem getItem(final int row);

	/**
	 * Returns item with specified text.
	 * 
	 * @param itemText text of the item to get
	 * @return table item with the specified text
	 */
	TableItem getItem(final String itemText);

	/**
	 * Returns all table items.
	 * 
	 * @return list of all table items
	 */
	List<TableItem> getItems();

	/**
	 * Returns item with specified text and column.
	 * 
	 * @param itemText text of the item to get
	 * @param column column where the item is located
	 * @return table item at specified text and column
	 */
	TableItem getItem(final String itemText, int column);

	/**
	 * Returns index of specified table item. 
	 *
	 * @param tableItem item to find out its index
	 * @return index of specified table item
	 */
	int indexOf(TableItem tableItem);

	/**
	 * Returns table items matching the matcher.
	 * 
	 * @param matcher matcher for items matching
	 * @return table items matching the matcher
	 */
	List<TableItem> getItems(Matcher<TableItem> matcher);

	org.eclipse.swt.widgets.Table getSWTWidget();

}
