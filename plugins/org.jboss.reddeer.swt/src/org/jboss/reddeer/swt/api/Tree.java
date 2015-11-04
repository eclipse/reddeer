package org.jboss.reddeer.swt.api;

import java.util.List;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for tree manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Tree extends Widget {

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
	 * @param treeItems tree items which are selected
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

	org.eclipse.swt.widgets.Tree getSWTWidget();
}
