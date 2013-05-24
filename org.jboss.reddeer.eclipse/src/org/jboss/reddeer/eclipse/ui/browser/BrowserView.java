package org.jboss.reddeer.eclipse.ui.browser;

import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.exception.ViewNotFoundException;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Represents Internal Browser view in Eclipse
 * 
 * @author psuchy
 * 
 */
public class BrowserView extends WorkbenchView {

	private Browser browser;
	private static final TimePeriod TIMEOUT = TimePeriod.LONG;

	/**
	 * Constructor of BrowserView Class
	 * Check if Internal Web Browser view exists
	 * 
	 * @throws ViewNotFoundException if the view does not exist
	 * @see WorkbenchView
	 */
	public BrowserView() {
		super("Internal Web Browser");
	}

	/**
	 * Opens Internal Web Browser view
	 */
	@Override
	public void open() {
		super.open();
		browser = new InternalBrowser();
	};

	/**
	 * Opens page with given URL in browser
	 * 
	 * @param url
	 */
	public void openPageURL(String url) {
		browser.setURL(url);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Refreshes currently opened page in browser
	 */
	public void refreshPage() {
		browser.setURL(browser.getURL());
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Go to the previous page in browser
	 */
	public void back() {
		String prevUrl = browser.getURL();
		browser.back();
		new WaitWhile(new BrowserHasURL(browser, prevUrl), TIMEOUT);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Go to the next page in browser
	 */
	public void forward() {
		String prevUrl = browser.getURL();
		browser.forward();
		new WaitWhile(new BrowserHasURL(browser, prevUrl), TIMEOUT);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Gets URL of the currently opened page
	 * 
	 * @return String URL of the current page
	 */
	public String getPageURL() {
		return browser.getURL();
	}
	
	/**
	 * Gets Text of the currently opened page
	 * 
	 * @return String Text of the current page
	 */
	public String getText() {
		return browser.getText();
	}

	/**
	 * WaitCondition to test whether page is already loaded into browser
	 */
	private class PageIsLoaded implements WaitCondition {

		private Browser browser;

		private PageIsLoaded(Browser browser) {
			this.browser = browser;
		}

		@Override
		public boolean test() {
			return browser.isPageLoaded();
		}

		@Override
		public String description() {
			return "Page is loaded in browser.";
		}
	}
	
	private class BrowserHasURL implements WaitCondition {

		private Browser browser;
		private String URL;

		private BrowserHasURL(Browser browser,String URL) {
			this.browser = browser;
			this.URL = URL;
		}

		@Override
		public boolean test() {
			return browser.getURL().equals(URL);
		}

		@Override
		public String description() {
			return "Browser is pointed to URL: "+URL;
		}
	}

}
