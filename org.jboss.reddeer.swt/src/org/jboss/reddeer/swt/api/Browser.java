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
	 * Set given url in browser, browser then loads it asynchronously 
	 */
	void setURL(String url);
	
	/**
	 * Get url from browser 
	 */
	String getURL();
	
	/**
	 * Get text from page in browser 
	 */
	String getText();

}
