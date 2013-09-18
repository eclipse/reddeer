package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * API For ExpandBarItem manipulation
 * @author Vlado Pakan
 *
 */
public interface ExpandBarItem {
	/**
	 * Returns the text of expand bar item
	 * 
	 * @return
	 */
	String getText();
	
	/**
	 * Returns the tool tip text of expand bar item
	 * 
	 * @return
	 */
	String getToolTipText();
	/**
	 * Expands expand bar
	 */
	void expand();
	/**
	 * Expands expand bar and wait for specified time period
	 */
	void expand(TimePeriod timePeriod);
	/**
	 * Collapse expand bar
	 */
	void collapse();
	/**
	 * Returns enclosing Expand Bar
	 * @return
	 */
	ExpandBar getParent();
	/**
	 * Returns SWT Expand Item enclosed by this Expand Bar Item
	 * @return
	 */
	org.eclipse.swt.widgets.ExpandItem getSWTWidget();
	/**
	 * Returns SWT Expand Item enclosed by this Expand Bar Item
	 * @return
	 */
	org.eclipse.swt.widgets.ExpandBar getSWTParent();
	/**
	 * Returns true when Expand Bar Item is expanded 
	 * @return
	 */
	 boolean isExpanded();
}
