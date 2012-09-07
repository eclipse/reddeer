package org.jboss.reddeer.swt.api;

import java.util.List;

/**
 * API for Tree item manipulation
 * 
 * @author Jiri Peterka
 *
 */
public interface TreeItem {

	String getText();
	
	String getToolTipText();

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
	
	boolean isSelected();
	
	boolean isDisposed();
	
	void select();
	
	void expand();

	void doubleClick();
}
