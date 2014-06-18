package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for scale manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Scale extends Widget {

	/**
	 * Returns minimum value of the scale.
	 * 
	 * @return minimum value of the scale.
	 */
	int getMinimum();

	/**
	 * Returns maximum value of the scale.
	 * 
	 * @return maximum value of the scale
	 */
	int getMaximum();

	/**
	 * Returns current value of the scale.
	 * 
	 * @return current value of the scale
	 */
	int getSelection();

	/**
	 * Sets current value of the scale.
	 * 
	 * @param value value of the scale to select
	 */
	void setSelection(int value);

	/**
	 * Sets focus on the scale.
	 */
	void setFocus();

	org.eclipse.swt.widgets.Scale getSWTWidget();
}
