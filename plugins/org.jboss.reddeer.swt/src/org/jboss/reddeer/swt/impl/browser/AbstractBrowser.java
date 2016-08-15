/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.browser;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.handler.BrowserHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.condition.PageIsLoaded;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.browser.internal.BrowserProgressListener;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Browsers implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractBrowser extends AbstractWidget<org.eclipse.swt.browser.Browser> implements Browser{
	
	private static final Logger log = Logger.getLogger(AbstractBrowser.class);
	
	private BrowserProgressListener browserProgressListener;
	
	/**
	 * Instantiates a new abstract browser.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public AbstractBrowser(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.browser.Browser.class, referencedComposite, index, matchers);
		this.browserProgressListener = new BrowserProgressListener(this);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#isPageLoaded()
	 */
	@Override
	public boolean isPageLoaded() {
		return browserProgressListener.isDone();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#forward()
	 */
	@Override
	public void forward() {
		log.info("Browser forward");
		setUpProgressListener();
		if (BrowserHandler.getInstance().forward(this.getSWTWidget())){
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page is fully loaded
			AbstractWait.sleep(TimePeriod.SHORT);
		} else {
			throw new SWTLayerException("forward action was not successful");
		} 
		resetProgressListener();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#back()
	 */
	@Override
	public void back() {
		log.info("Browser back");
		setUpProgressListener();
		if (BrowserHandler.getInstance().back(this.getSWTWidget())) {
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page
			// is fully loaded
			AbstractWait.sleep(TimePeriod.SHORT);
		} else {
			throw new SWTLayerException("back action was not successful");
		}
		resetProgressListener();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#setURL(java.lang.String)
	 */
	@Override
	public void setURL(String url) {
		log.info("Set browser URL to '" + url + "'");
		setUpProgressListener();
		if (BrowserHandler.getInstance().setURL(this.getSWTWidget(), url)){
			new WaitUntil(new PageIsLoaded(this), TimePeriod.LONG);
			// Unfortunately Browser needs some time to get ready even when page is fully loaded
			AbstractWait.sleep(TimePeriod.NORMAL);
		} else {
			throw new SWTLayerException("setUrl action was not successful");
		}
		resetProgressListener();
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#getURL()
	 */
	@Override
	public String getURL() {
		new WaitUntil(new PageIsLoaded(this));
		return BrowserHandler.getInstance().getURL(this.getSWTWidget());	
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#getText()
	 */
	@Override
	public String getText() {
		new WaitUntil(new PageIsLoaded(this));
		return BrowserHandler.getInstance().getText(this.getSWTWidget());	
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#refresh()
	 */
	@Override
	public void refresh() {
		log.info("Browser refresh");
		BrowserHandler.getInstance().refresh(this.getSWTWidget());		
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#evaluate(java.lang.String)
	 */
	@Override
	public Object evaluate(String script) {
		log.info("Evaluate script: " + script);
		return BrowserHandler.getInstance().evaluate(this.getSWTWidget(), script);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Browser#execute(java.lang.String)
	 */
	@Override
	public boolean execute(String script) {
		log.info("Execute script: " + script);
		return BrowserHandler.getInstance().execute(this.getSWTWidget(), script);
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
