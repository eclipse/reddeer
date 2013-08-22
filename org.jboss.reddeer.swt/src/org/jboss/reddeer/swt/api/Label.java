package org.jboss.reddeer.swt.api;

/**
 * API For Label manipulation
 * @author Jiri Peterka
 *
 */
public interface Label {

	/**
	 * Returns text of the label
	 * @return text of the label
	 */
	String getText();

	/**
	 * Checks if label is visible
	 * @return true if label is visible, false otherwise
	 */
	boolean isVisible();
	
}
