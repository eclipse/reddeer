package org.jboss.reddeer.eclipse.ui.browser;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.condition.BrowserHasURL;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.AbstractEditor;

/**
 * Represents a browser editor.
 */
public class BrowserEditor extends AbstractEditor{
	
	private InternalBrowser browser;
	private static final TimePeriod TIMEOUT = TimePeriod.LONG;
	
	/**
	 * Constructs the browser editor with a given title.
	 * 
	 * @param title Title
	 */
	public BrowserEditor(String title){
		super(title);
		browser = new InternalBrowser();
	}
	
	/**
	 * Constructs the browser editor with a given title matcher.
	 * 
	 * @param titleMatcher Title matcher
	 */
	public BrowserEditor(Matcher<String> titleMatcher){
		super(titleMatcher);
		browser = new InternalBrowser();
	}
	
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
		new WaitWhile(new BrowserHasURL(this, prevUrl), TIMEOUT);
		new WaitUntil(new PageIsLoaded(browser), TIMEOUT);
	}

	/**
	 * Go to the next page in browser
	 */
	public void forward() {
		String prevUrl = browser.getURL();
		browser.forward();
		new WaitWhile(new BrowserHasURL(this, prevUrl), TIMEOUT);
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

}
