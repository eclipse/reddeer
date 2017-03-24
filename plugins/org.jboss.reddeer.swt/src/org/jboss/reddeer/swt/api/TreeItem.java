/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.api;

import java.util.List;

import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * API for tree item manipulation.
 * 
 * @author Jiri Peterka, Marian Labuda
 *
 */
public interface TreeItem extends Item<org.eclipse.swt.widgets.TreeItem> {

	/**
	 * Gets whole text of the tree item.
	 * 
	 * @return whole text of the tree item
	 */
	String getText();
	
	/**
	 * Returns ToolTip text of the tree item.
	 * 
	 * @return ToolTip text of the tree item
	 */
	String getToolTipText();
	
	/**
	 * Returns path to the tree item in the tree.
	 * 
	 * @return path to the tree item
	 */
	String[] getPath();
	
	/**
	 * Returns text of cell on the position defined by the index. 
	 * 
	 * @param index of cell of the tree item
	 * @return text of the tree item in cell specified by the index
	 */
	String getCell(int index);

	/**
	 * Returns all descending direct tree items of the tree item. 
	 * 
	 * @return direct descending tree items 
	 */
	List<TreeItem> getItems();
	
	/**
	 * Returns direct tree item with the specified label.
	 * 
	 * @param text text of direct tree item
	 * @return tree item having specified label
	 */
	TreeItem getItem(String text);
	
	/**
	 * Returns child tree item with the specified path.
	 * 
	 * @param path path to tree item
	 * @return tree item having specified label
	 */
	TreeItem getItem(String... path);
	
	/**
	 * Returns whether the tree item is selected or not.
	 * 
	 * @return true if the tree item is selected, false otherwise
	 */
	boolean isSelected();
	
	/**
	 * Selects the tree item.
	 */
	void select();
	
	/**
	 * Expands the tree item and waits for a default time period.
	 */
	void expand();
	
	/**
	 * Collapses the tree item.
	 */
	void collapse();
	
	/**
	 * Clicks twice on the tree item.
	 */
	void doubleClick();
	
	/**
	 * Sets state of the tree item. The tree item must have check box next to it. 
	 * 
	 * @param check whether the tree item should be checked or not
	 */
	void setChecked(boolean check);
	
	/**
	 * Returns whether the tree item is checked or not.
	 * 
	 * @return true if the tree item is checked, false otherwise
	 */
	boolean isChecked();
	
	/**
	 * Gets parent tree of the tree item.
	 * 
	 * @return parent tree of the tree item
	 */
	Tree getParent();

	/**
	 * Expands the tree item and waits for default time period until the expanded 
	 * tree item has at least defined count of items.
	 * 
	 * @param minItemsCount minimal count of items the tree item has to have
	 */
	void expand(int minItemsCount);
	
	/**
	 * Expands the tree item and waits for time period until the expanded tree item
	 * has at least defined count of items.
	 * 
	 * @param minItemsCount minimal count of items the tree item has to have
	 * @param timePeriod time period to wait
	 */
	void expand(int minItemsCount , TimePeriod timePeriod);
	
	/**
	 * Expands the tree item and waits for time period.
	 * 
	 * @param timePeriod time period to wait
	 */
	void expand(TimePeriod timePeriod);
	
	/**
	 * Returns whether the tree item is expanded or not.
	 * 
	 * @return true if the tree item is expanded, false otherwise
	 */
	 boolean isExpanded();
	 
	 /**
	  * Sets text at a column defined by the index.
	  * 
	  * @param text text to set
	  * @param index the column index
	  */
	 void setText(String text, int index);
	 
	 /**
	  * Sets text to the tree item.
	  * 
	  * @param text text to set
	  */
	 void setText(String text);
}
