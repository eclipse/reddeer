package org.jboss.reddeer.eclipse.condition;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.browser.BrowserEditor;
import org.jboss.reddeer.eclipse.ui.browser.BrowserView;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Wait condition which returns true if a given browser has the specified URL.
 */
public class BrowserHasURL implements WaitCondition {

	private BrowserView browser;
	private BrowserEditor browserEditor;
	private String expectedURL;
	private Matcher<String> expectedURLMatcher;
	
	/**
	 * Construct a condition with a given browser view and expected URL.
	 * 
	 * @param browser Browser view
	 * @param expectedURL Expected URL
	 */
	public BrowserHasURL(BrowserView browser,String expectedURL){
		this.browser = browser;
		this.expectedURL = expectedURL;
	}
	
	/**
	 * Construct a condition with a given browser view and URL matcher.
	 * 
	 * @param browser Browser view
	 * @param expectedURLMatcher URL matcher
	 */
	public BrowserHasURL(BrowserView browser,Matcher<String> expectedURLMatcher){
		this.browser = browser;
		this.expectedURLMatcher = expectedURLMatcher;
	}
	
	/**
	 * Construct a condition with a given browser editor and expected URL.
	 * 
	 * @param browser Browser editor
	 * @param expectedURL Expected URL
	 */
	public BrowserHasURL(BrowserEditor browser,String expectedURL){
		this.browserEditor = browser;
		this.expectedURL = expectedURL;
	}
	
	/**
	 * Construct a condition with a given browser editor and URL matcher.
	 * 
	 * @param browser Browser editor
	 * @param expectedURL URL matcher
	 */
	public BrowserHasURL(BrowserEditor browser,Matcher<String> expectedURLMatcher){
		this.browserEditor = browser;
		this.expectedURLMatcher = expectedURLMatcher;
	}

	@Override
	public boolean test() {
		if (expectedURLMatcher != null){
			if(browser != null){
				return expectedURLMatcher.matches(browser.getPageURL());
			} else {
				return expectedURLMatcher.matches(browserEditor.getPageURL());
			}
		} else {
			if(browser != null){
				return browser.getPageURL().equals(expectedURL);
			} else {
				return browserEditor.getPageURL().equals(expectedURL);
			}
		}
		
	}
	
	@Override
	public String description() {
		if(expectedURLMatcher != null){
			return "browser is pointed to URL: "+expectedURLMatcher.toString();
		}
		return "browser is pointed to URL: "+expectedURL;
	}
	}