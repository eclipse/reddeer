package org.jboss.reddeer.swt.api;

import java.util.List;

/**
 * API For ExpandBar manipulation
 * @author Vlado Pakan
 *
 */
public interface ExpandBar {
	/**
	 * Returns number of contained Expand Bar items 
	 * @return
	 */
	int getItemsCount();
	/**
	 * Returns contained Expand Bar items 
	 * @return
	 */
	List<ExpandBarItem> getItems();
	/**
	 * Sets focus to ExpandBar
	 */
	void setFocus();
	/**
	 * Expands all Expand Bar Items 
	 */
	void expandAll();
	/**
	 * Collapses all Expand Bar Items
	 */
	void collapseAll();
	/**
	 * Returns swt widget of Expand Bar
	 * @return
	 */
	org.eclipse.swt.widgets.ExpandBar getSWTWidget();
}
