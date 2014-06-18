package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for progress bar manipulation.
 * 
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public interface ProgressBar extends Widget {

	/**
	 * Gets state of the progress bar.
	 * 
	 * @return current state (SWT.NORMAL, SWT.ERROR, SWT.PAUSED)
	 */
	int getState();

	org.eclipse.swt.widgets.ProgressBar getSWTWidget();

}
