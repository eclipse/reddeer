package org.jboss.reddeer.swt.api;

/**
 * API For Scale manipulation
 * @author Jiri Peterka
 *
 */
public interface Scale {
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
	/**
	 * Returns underlying swt widget
	 * @return
	 */
	org.eclipse.swt.widgets.Scale getSWTWidget();
}
