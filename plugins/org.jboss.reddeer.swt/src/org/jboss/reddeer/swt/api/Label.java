package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for label manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Label extends Widget {

	/**
	 * Returns the text of the label.
	 * 
	 * @return text of the label
	 */
	String getText();

	/**
	 * Checks if label is visible.
	 * 
	 * @return true if label is visible, false otherwise
	 */
	boolean isVisible();
	
	org.eclipse.swt.widgets.Label getSWTWidget();
	
}
