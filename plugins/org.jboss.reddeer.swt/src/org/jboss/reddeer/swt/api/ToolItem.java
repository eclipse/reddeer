package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for tool bar item manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface ToolItem extends Widget {

	/**
	 * Clicks the tool item.
	 */
	void click();

	/**
	 * Gets ToolTip text of the tool item.
	 * 
	 * @return text of ToolTip
	 */
	String getToolTipText();

	/**
	 * Returns whether the tool item is selected or not.
	 * 
	 * @return true if the tool item is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Toggles button of the the tool item.
	 * 
	 * @param toggle the button of the tool item or not
	 */
	void toggle(boolean toggle);

	org.eclipse.swt.widgets.ToolItem getSWTWidget();
}
