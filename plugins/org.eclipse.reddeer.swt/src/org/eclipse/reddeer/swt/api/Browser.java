/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.api;

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
