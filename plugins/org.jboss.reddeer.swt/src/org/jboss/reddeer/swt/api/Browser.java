package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for HTML browser manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Browser extends Widget {

	/**
	 * Indicates whether a page is loaded or not.
	 * 
	 * @return true if page is loaded, false otherwise
	 */
	boolean isPageLoaded();

	/**
	 * Presses forward on the browser.
	 */
	void forward();

	/**
	 * Presses back on the browser.
	 */
	void back();

	/**
	 * Sets given URL in the browser. Browser loads it asynchronously.
	 * 
	 * @param url to set
	 */
	void setURL(String url);

	/**
	 * Gets URL from the browser.
	 * 
	 * @return URL of current site in the browser
	 */
	String getURL();

	/**
	 * Gets text from a page in the browser.
	 * 
	 * @return text in a page
	 */
	String getText();

	/**
	 * Refreshes loaded page.
	 */
	void refresh();

	org.eclipse.swt.browser.Browser getSWTWidget();

}
