package org.jboss.reddeer.swt.api;

/**
 * API For CTabItem manipulation
 * @author Vlado Pakan
 *
 */
public interface CTabItem {
	/**
	 * Activates CTabItem
	 */
	void activate();
	/**
	 * Returns the text of tree item
	 * 
	 * @return
	 */
	String getText();
	
	/**
	 * Returns the tool tip text of tree item
	 * 
	 * @return
	 */
	String getToolTipText();
	/**
	 * Closes CTabItem
	 */
	void close();
	/**
	 * Returns true when close button should be displayed
	 */
	boolean isShowClose();
}
