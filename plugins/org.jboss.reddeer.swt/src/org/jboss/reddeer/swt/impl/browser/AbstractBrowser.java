package org.jboss.reddeer.swt.impl.browser;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.core.handler.BrowserHandler;
import org.jboss.reddeer.swt.impl.browser.internal.BrowserProgressListener;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Browsers implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractBrowser extends AbstractWidget<org.eclipse.swt.browser.Browser> implements Browser{
	
	private static final Logger log = Logger.getLogger(AbstractBrowser.class);
	
	private BrowserProgressListener browserProgressListener;
	
	public AbstractBrowser(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.browser.Browser.class, referencedComposite, index, matchers);
		this.browserProgressListener = new BrowserProgressListener(this);
	}
	
	@Override
	public boolean isPageLoaded() {
		return browserProgressListener.isDone();
	}

	@Override
	public void forward() {
		log.info("Browser forward");
		setUpProgressListener();
		if (BrowserHandler.getInstance().forward(this.getSWTWidget())){
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page is fully loaded
			AbstractWait.sleep(TimePeriod.SHORT);
		}
		resetProgressListener();
	}

	@Override
	public void back() {
		log.info("Browser back");
		setUpProgressListener();
		if (BrowserHandler.getInstance().back(this.getSWTWidget())) {
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page
			// is fully loaded
			AbstractWait.sleep(TimePeriod.SHORT);
		}
		resetProgressListener();
	}

	@Override
	public void setURL(String url) {
		log.info("Set browser URL to " + url);
		setUpProgressListener();
		if (BrowserHandler.getInstance().setURL(this.getSWTWidget(), url)){
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page is fully loaded
			AbstractWait.sleep(TimePeriod.NORMAL);
		}
		resetProgressListener();
	}
	
	@Override
	public String getURL() {
		new WaitUntil(new PageIsLoaded(this));
		return BrowserHandler.getInstance().getURL(this.getSWTWidget());	
	}
	
	@Override
	public String getText() {
		new WaitUntil(new PageIsLoaded(this));
		return BrowserHandler.getInstance().getText(this.getSWTWidget());	
	}
	@Override
	public void refresh() {
		log.info("Browser refresh");
		BrowserHandler.getInstance().refresh(this.getSWTWidget());		
	}
	private void setUpProgressListener (){
		BrowserHandler.getInstance().addProgressListener(this.getSWTWidget(), browserProgressListener);
		browserProgressListener.setDone(false);
	}
	private void resetProgressListener (){
		BrowserHandler.getInstance().removeProgressListener(this.getSWTWidget(), browserProgressListener);
		browserProgressListener.setDone(true);
	}
}
