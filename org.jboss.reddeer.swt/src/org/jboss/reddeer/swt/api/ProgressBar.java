package org.jboss.reddeer.swt.api;

/**
 * API For ProgressBar manipulation
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public interface ProgressBar {
	
	/**
	 * 
	 * @return actual state (SWT.NORMAL, SWT.ERROR, SWT.PAUSED)
	 */
	int getState();

}
