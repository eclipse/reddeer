package org.jboss.reddeer.eclipse.condition;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.browser.BrowserEditor;
import org.jboss.reddeer.eclipse.ui.browser.BrowserView;
import org.jboss.reddeer.swt.condition.WaitCondition;

public class BrowserHasURL implements WaitCondition {

	private BrowserView browser;
	private BrowserEditor browserEditor;
	private String expectedURL;
	private Matcher<String> expectedURLMatcher;
	
	public BrowserHasURL(BrowserView browser,String expectedURL){
		this.browser = browser;
		this.expectedURL = expectedURL;
	}
	
	public BrowserHasURL(BrowserView browser,Matcher<String> expectedURLMatcher){
		this.browser = browser;
		this.expectedURLMatcher = expectedURLMatcher;
	}
	
	public BrowserHasURL(BrowserEditor browser,String expectedURL){
		this.browserEditor = browser;
		this.expectedURL = expectedURL;
	}
	
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