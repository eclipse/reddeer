package org.jboss.reddeer.eclipse.ui.browser;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
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
public class BrowserView extends WorkbenchView {

	protected Browser browser;
	protected static final TimePeriod TIMEOUT = TimePeriod.LONG;

	/**
	 * Constructor of BrowserView Class
	 * Check if Internal Web Browser view exists.
	 *
	 * @see WorkbenchView
	 */
	public BrowserView() {
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
		new WaitWhile(new BrowserHasURL(this, prevUrl), TIMEOUT);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Go to the next page in browser.
	 */
	public void forward() {
		activate();
		String prevUrl = browser.getURL();
		browser.forward();
		new WaitWhile(new BrowserHasURL(this, prevUrl), TIMEOUT);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
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
