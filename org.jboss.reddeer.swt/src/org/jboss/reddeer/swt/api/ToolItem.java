package org.jboss.reddeer.swt.api;

/**
 * API for Toolbar manipulation
 * @author Jiri Peterka
 *
 */
public interface ToolItem {

	/**
	 * Click a Toolbar item 
	 */
	void click();
	
	
	/**
	 * Returns ToolItem tooltip text
	 */
	String getToolTipText();
	/**
	 * Returns true when button is selected
	 */
	boolean isSelected();
	/**
	 * Returns ToolItem tooltip text
	 */
	void toggle(boolean toggle);
}
