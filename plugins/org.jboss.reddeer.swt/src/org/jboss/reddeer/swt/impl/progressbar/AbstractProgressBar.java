package org.jboss.reddeer.swt.impl.progressbar;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ProgressBar;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ProgressBarLookup;
import org.jboss.reddeer.swt.matcher.WithStyleMatcher;

/**
 * Abstract class for ProgressBar
 * 
 * @author rhopp
 *
 */

public abstract class AbstractProgressBar implements ProgressBar {
	
	private static final Logger log = Logger.getLogger(AbstractProgressBar.class);
	
	protected org.eclipse.swt.widgets.ProgressBar widget;

	protected AbstractProgressBar(int index, int style) {
		log.debug("Searching for ProgressBar:"
				+ "\n  index: " + index
				+ "\n  style: " + style);
		widget = ProgressBarLookup.getInstance().getProgressBar(index, new WithStyleMatcher(style));
	}

	/**
	 * Returns state of this progressbar. One of SWT.NORMAL, SWT.ERROR, SWT.PAUSED. 
	 * Note: This operation is a hint and is not supported on platforms that do not have this concept.
	 */
	
	@Override
	public int getState() {
		return ProgressBarHandler.getInstance().getState(widget);
	}
	
	public org.eclipse.swt.widgets.ProgressBar getSWTWidget(){
		return widget;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(widget);
	}

}
