package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.swt.api.Browser;

/**
 * Condition is met when page is fully loaded in a {@link Browser}.
 * 
 * @author Vlado Pakan
 *
 */
public class PageIsLoaded implements WaitCondition {

	private Browser browser;

	/**
	 * Constructs PageIsLoaded wait condition. Condition is met when 
	 * page is loaded in specified browser. 
	 * 
	 * @param browser browser where to test page availability status (loaded or not)
	 */
	public PageIsLoaded(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean test() {
		return browser.isPageLoaded();
	}

	@Override
	public String description() {
		return "page is loaded to browser";
	}

}
