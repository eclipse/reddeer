package org.jboss.reddeer.swt.impl.browser;

import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.handler.BrowserHandler;
import org.jboss.reddeer.swt.impl.browser.internal.BrowserProgressListener;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Abstract class for all Browsers implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractBrowser implements Browser{
	protected org.eclipse.swt.browser.Browser swtBrowser;
	private BrowserProgressListener browserProgressListener;
	
	public AbstractBrowser(org.eclipse.swt.browser.Browser browser) {
		this.swtBrowser = browser;
		this.browserProgressListener = new BrowserProgressListener(this);
	}
	
	@Override
	public boolean isPageLoaded() {
		return browserProgressListener.isDone();
	}

	@Override
	public void forward() {
		setUpProgressListener();
		if (BrowserHandler.forward(this)){
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page is fully loaded
			AbstractWait.sleep(TimePeriod.SHORT.getSeconds() * 1000);
		}
		resetProgressListener();
	}

	@Override
	public void back() {
		setUpProgressListener();
		if (BrowserHandler.back(this)) {
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page
			// is fully loaded
			AbstractWait.sleep(TimePeriod.SHORT.getSeconds() * 1000);
		}
		resetProgressListener();
	}

	@Override
	public void setURL(String url) {
		setUpProgressListener();
		if (BrowserHandler.setURL(this , url)){
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page is fully loaded
			AbstractWait.sleep(TimePeriod.NORMAL.getSeconds() * 1000);
		}
		resetProgressListener();
	}
	
	@Override
	public String getURL() {
		return BrowserHandler.getURL(this);	
	}
	
	@Override
	public String getText() {
		return BrowserHandler.getText(this);	
	}
	/**
	 * See {@link Browser}
	 */
	@Override
	public org.eclipse.swt.browser.Browser getSWTWidget() {
		return swtBrowser;
	}
	@Override
	public void refresh() {
		BrowserHandler.refresh(this);		
	}
	private void setUpProgressListener (){
		BrowserHandler.addProgressListener(this, browserProgressListener);
		browserProgressListener.setDone(false);
	}
	private void resetProgressListener (){
		BrowserHandler.removeProgressListener(this, browserProgressListener);
		browserProgressListener.setDone(true);
	}

	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(swtBrowser);
	}
}
