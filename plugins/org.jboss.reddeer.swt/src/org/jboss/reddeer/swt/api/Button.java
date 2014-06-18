package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for button (push, radio and toggle) manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Button extends Widget {

	/**
	 * Performs click on the button.
	 */
	void click();

	/**
	 * Returns text on the button.
	 * 
	 * @return text on the button
	 */
	String getText();

	/**
	 * Returns the ToolTip of the button.
	 * 
	 * @return the ToolTip text on the button
	 */
	String getToolTipText();

	/**
	 * Finds out whether button is enabled or not.
	 * 
	 * @return true if this button is enabled, false otherwise
	 */
	boolean isEnabled();

	org.eclipse.swt.widgets.Button getSWTWidget();
}
