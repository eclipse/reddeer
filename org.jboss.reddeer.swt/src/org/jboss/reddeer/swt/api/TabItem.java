package org.jboss.reddeer.swt.api;

/**
 * API for TabItem manipulation.
 * 
 * @author apodhrad
 * 
 */
public interface TabItem {

	/**
	 * Activate this tab.
	 */
	void activate();

	/**
	 * Returns the text of tab item
	 * 
	 * @return
	 */
	String getText();
}
