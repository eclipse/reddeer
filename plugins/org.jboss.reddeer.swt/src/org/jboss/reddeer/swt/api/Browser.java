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

	/**
	 * Evaluates given script and returns its output. For more information see
	 * {@link org.eclipse.swt.browser.Browser#evaluate(String)}.
	 * 
	 * @param script
	 *            Script to evaluate;
	 */

	Object evaluate(String script);

	/**
	 * Executes given script. For more information see
	 * {@link org.eclipse.swt.browser.Browser#execute(String)}.
	 * 
	 * @param script
	 *            Script to execute.
	 */

	boolean execute(String script);
	
	org.eclipse.swt.browser.Browser getSWTWidget();

}
