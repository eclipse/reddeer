package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for shell manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Shell extends Widget {

	/**
	 * Returns title of the shell.
	 * 
	 * @return title of the shell.
	 */
	String getText();

	/**
	 * Sets focus on the shell.
	 */
	void setFocus();

	/**
	 * Closes the shell.
	 */
	void close();
	
	org.eclipse.swt.widgets.Shell getSWTWidget();

}
