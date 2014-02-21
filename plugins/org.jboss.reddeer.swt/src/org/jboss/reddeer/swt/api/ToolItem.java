package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for Toolbar manipulation
 * @author Jiri Peterka
 *
 */
public interface ToolItem extends Widget{

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
	 * Returns true when button is enabled
	 */
	boolean isEnabled();
	/**
	 * Returns ToolItem tooltip text
	 */
	void toggle(boolean toggle);
	
	 org.eclipse.swt.widgets.ToolItem getSWTWidget();
}
