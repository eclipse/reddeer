package org.jboss.reddeer.swt.impl.browser;

import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.util.Bot;

public class InternalBrowser extends AbstractBrowser implements Browser {

	public InternalBrowser() {
		browser = Bot.get().browser();
		setReady();
		}
	
	public InternalBrowser(String label) {
		browser = Bot.get().browserWithLabel(label);
		setReady();
	}
	
	@Override
	public boolean isPageLoaded() {
		return browser.isPageLoaded();
	}

	@Override
	public void forward() {
		browser.forward();
		
	}

	@Override
	public void back() {
		browser.back();
		
	}

	@Override
	public void setURL(String url) {
		browser.setUrl(url);	
	}
	
	@Override
	public String getURL() {
		return browser.getUrl();	
	}
	
	@Override
	public String getText() {
		return browser.getText();	
	}

}
