/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.api;

/**
 * API for HTML browser manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Browser extends Control<org.eclipse.swt.browser.Browser> {

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
	 * Evaluates given script and returns its output.
	 * 
	 * @param script
	 *            Script to evaluate;
	 * @return output of script's evaluation
	 */
	Object evaluate(String script);

	/**
	 * Executes given script.
	 * 
	 * @param script
	 *            Script to execute.
	 * @return true if script was executed successfully, false otherwise           
	 *           
	 */
	boolean execute(String script);
}
