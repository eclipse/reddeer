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
package org.jboss.reddeer.eclipse.ui.browser;

import static org.jboss.reddeer.common.wait.WaitProvider.waitUntil;
import static org.jboss.reddeer.common.wait.WaitProvider.waitWhile;

import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.eclipse.condition.BrowserHasURL;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

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
			browser = new InternalBrowser();
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
