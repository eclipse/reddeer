package org.jboss.reddeer.swt.api;

/**
 * API For Link manipulation
 * @author Jiri Peterka
 *
 */
public interface Link {
	
	/**
	 * Returns text of link
	 * @return
	 */
	String getText();
	
	/**
	 * Clicks on link
	 */
	void click();

}
