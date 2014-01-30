package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For ProgressBar manipulation
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public interface ProgressBar extends Widget{
	
	/**
	 * 
	 * @return actual state (SWT.NORMAL, SWT.ERROR, SWT.PAUSED)
	 */
	int getState();
	
	org.eclipse.swt.widgets.ProgressBar getSWTWidget();

}
