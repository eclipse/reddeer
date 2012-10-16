package org.jboss.reddeer.swt.api;

/**
 * API for Toolbar manipulation
 * @author Jiri Peterka
 *
 */
public interface ToolItem {

	/*
	 * Click a Toolbar item 
	 */
	void click();
	
	
	/*
	 * Returns ToolItem tooltip text
	 */
	String getToolTipText();  
}
