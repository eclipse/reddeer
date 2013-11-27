package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.ui.browser.BrowserEditor;
import org.jboss.reddeer.eclipse.ui.browser.BrowserView;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.matcher.RegexMatcher;

public class BrowserHasURL implements WaitCondition {

	private BrowserView browser;
	private BrowserEditor browserEditor;
	private String expectedURL;
	private RegexMatcher expectedURLMatcher;
	
	public BrowserHasURL(BrowserView browser,String expectedURL){
		this.browser = browser;
		this.expectedURL = expectedURL;
	}
	
	public BrowserHasURL(BrowserView browser,RegexMatcher expectedURLMatcher){
		this.browser = browser;
		this.expectedURLMatcher = expectedURLMatcher;
	}
	
	public BrowserHasURL(BrowserEditor browser,String expectedURL){
		this.browserEditor = browser;
		this.expectedURL = expectedURL;
	}
	
	public BrowserHasURL(BrowserEditor browser,RegexMatcher expectedURLMatcher){
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
			return "Browser is pointed to URL: "+expectedURLMatcher.toString();
		}
		return "Browser is pointed to URL: "+expectedURL;
	}
	}