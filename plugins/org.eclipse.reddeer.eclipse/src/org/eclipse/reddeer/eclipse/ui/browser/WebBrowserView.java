/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.browser;

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;

import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.BrowserHasURL;
import org.eclipse.reddeer.swt.api.Browser;
import org.eclipse.reddeer.swt.condition.PageIsLoaded;
import org.eclipse.reddeer.swt.impl.browser.InternalBrowser;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents Internal Browser view in Eclipse
 * 
 * @author psuchy
 * 
 */
public class WebBrowserView extends WorkbenchView {

	protected Browser browser;
	protected static final TimePeriod TIMEOUT = TimePeriod.LONG;

	/**
	 * Constructor of BrowserView Class
	 * Check if Internal Web Browser view exists.
	 *
	 * @see WorkbenchView
	 */
	public WebBrowserView() {
		super("Internal Web Browser");
	}
	
	/**
	 * Opens Internal Web Browser view.
	 */
	@Override
	public void open() {
		if (browser == null){
			super.open();
			browser = new InternalBrowser(cTabItem);
		}
	};

	/**
	 * Opens page with given URL in browser.
	 *
	 * @param url the url
	 */
	public void openPageURL(String url) {
		activate();
		browser.setURL(url);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Refreshes currently opened page in browser.
	 */
	public void refreshPage() {
		activate();
		browser.setURL(browser.getURL());
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Go to the previous page in browser.
	 */
	public void back() {
		activate();
		String prevUrl = browser.getURL();
		browser.back();
		new GroupWait(TIMEOUT, waitWhile(new BrowserHasURL(this, prevUrl)),
				waitUntil(new PageIsLoaded(browser)));
	}

	/**
	 * Go to the next page in browser.
	 */
	public void forward() {
		activate();
		String prevUrl = browser.getURL();
		browser.forward();
		new GroupWait(TIMEOUT, waitWhile(new BrowserHasURL(this, prevUrl)),
				waitUntil(new PageIsLoaded(browser)));
	}

	/**
	 * Gets URL of the currently opened page.
	 *
	 * @return String URL of the current page
	 */
	public String getPageURL() {
		activate();
		return browser.getURL();
	}
	
	/**
	 * Gets Text of the currently opened page.
	 *
	 * @return String Text of the current page
	 */
	public String getText() {
		activate();
		return browser.getText();
	}
}
