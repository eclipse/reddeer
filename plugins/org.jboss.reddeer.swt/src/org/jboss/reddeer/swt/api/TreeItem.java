package org.jboss.reddeer.swt.api;

import java.util.List;

import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for Tree item manipulation
 * 
 * @author Jiri Peterka
 *
 */
public interface TreeItem extends Widget{

	/**
	 * Return the text of tree item
	 * 
	 * @return
	 */
	String getText();
	
	/**
	 * Return the tool tip text of tree item
	 * 
	 * @return
	 */
	String getToolTipText();
	
	/**
	 * Return the tree item path
	 * 
	 * @return
	 */
	String[] getPath();
	
	/**
	 * Return the specific cell on index
	 * 
	 * @param index
	 * @return
	 */
	String getCell(int index);

	/**
	 * Returns all direct tree items. 
	 * 
	 * @return
	 */
	List<TreeItem> getItems();
	
	/**
	 * Returns the direct tree item with the specified label.
	 * 
	 * @param text
	 * @return
	 */
	TreeItem getItem (String text);
	
	/**
	 * Return the state of selection of tree item
	 * 
	 * @return
	 */
	boolean isSelected();
	
	/**
	 * Return the state of disposal of tree item
	 * 
	 * @return
	 */
	boolean isDisposed();
	
	/**
	 * Select tree item
	 */
	void select();
	
	/**
	 * Expand tree item and waits with default time period
	 */
	void expand();
	
	/**
	 * Collapse tree item
	 */
	void collapse();
	
	/**
	 * Double click on tree item
	 */
	void doubleClick();
	
	/**
	 * Set is being checked state of tree item according to parameter check
	 * 
	 * @param check
	 */
	void setChecked(boolean check);
	
	/**
	 * Return the state of being checked of tree item
	 * 
	 * @return
	 */
	boolean isChecked();
	Tree getParent();

	/**
	 * Expands tree item and waits with default timePeriod until expanded tree item
	 * has minItemsCount children
	 * @param minItemsCount
	 */
	void expand(int minItemsCount);
	/**
	 * Expands tree item and waits with timePeriod until expanded tree item
	 * has minItemsCount children
	 * @param minItemsCount
	 * @param timePeriod
	 */
	void expand(int minItemsCount , TimePeriod timePeriod);
	/**
	 * Expands tree item and waits with timePeriod
	 * @param timePeriod
	 */
	void expand(TimePeriod timePeriod);
	/**
	 * Returns true when item is expanded 
	 * @return
	 */
	 boolean isExpanded();
	 
	 org.eclipse.swt.widgets.TreeItem getSWTWidget();
}
