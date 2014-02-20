package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Scale manipulation
 * @author Jiri Peterka
 *
 */
public interface Scale extends Widget{
	/**
	 * Returns minimum value of Scale
	 * @return
	 */
	int getMinimum();
	/**
	 * Returns maximum value of Scale
	 * @return
	 */
	int getMaximum();
	/**
	 * Returns current value of Scale
	 * @return
	 */
	int getSelection();
	/**
	 * Sets current value of scale
	 * @param value
	 */	
	void setSelection(int value);
	/**
	 * Sets focus to scale
	 * @param value
	 */	
	void setFocus();
	
	org.eclipse.swt.widgets.Scale getSWTWidget();
}
