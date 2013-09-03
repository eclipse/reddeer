package org.jboss.reddeer.swt.impl.browser.internal;

import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.jboss.reddeer.swt.api.Browser;
/**
 * Internal Progress Listener of Browser 
 * @author Vlado Pakan
 *
 */
public class BrowserProgressListener implements ProgressListener {
	private final Browser browser;
	private boolean done = true;
	private int numCalledIsDoneWithNoChange = 0;

	public BrowserProgressListener(Browser browser) {
		this.browser = browser;
	}
	// returns true in case page is loaded completely or
	// there is no change after calling this method more then 20 times
	// because on MS Windows method completed is not called properly
	public synchronized boolean isDone() {
		numCalledIsDoneWithNoChange++;
		return done || numCalledIsDoneWithNoChange > 20;
	}

	public synchronized void setDone(boolean done) {
		this.done = done;
	}

	public synchronized void changed(ProgressEvent event) {
		numCalledIsDoneWithNoChange = 0;
	}

	public void completed(ProgressEvent event) {
		setDone(true);
		browser.getSWTWidget().removeProgressListener(this);
	}

}
