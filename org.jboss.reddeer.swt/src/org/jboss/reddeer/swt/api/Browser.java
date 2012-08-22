package org.jboss.reddeer.swt.api;

/**
 * API For HTML Browser manipulation
 * @author Jiri Peterka
 *
 */
public interface Browser {
	
	/**
	 * Indicates wheather page is loaded or not
	 * @return
	 */
	boolean isPageLoaded();
	
	/**
	 * Press forward on browser
	 */
	void forward();
	
	/**
	 * Press back on browser
	 */
	void back();
	
	/**
	 * Open given url in browser
	 */
	void openURL(String url);

}
