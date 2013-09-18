package org.jboss.reddeer.swt.impl.progressbar;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.ProgressBar;
import org.jboss.reddeer.swt.lookup.ProgressBarLookup;
import org.jboss.reddeer.swt.matcher.StyleMatcher;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for ProgressBar
 * 
 * @author rhopp
 *
 */

public abstract class AbstractProgressBar implements ProgressBar {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	org.eclipse.swt.widgets.ProgressBar widget;

	protected AbstractProgressBar(int index, int style) {
		log.info("Searching for ProgressBar:"
				+ "\n  index: " + index
				+ "\n  style: " + style);
		widget = ProgressBarLookup.getInstance().getProgressBar(index, new StyleMatcher(style));
	}

	/**
	 * Returns state of this progressbar. One of SWT.NORMAL, SWT.ERROR, SWT.PAUSED. 
	 * Note: This operation is a hint and is not supported on platforms that do not have this concept.
	 */
	
	@Override
	public int getState() {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getState();
			}
		});
	}

}
