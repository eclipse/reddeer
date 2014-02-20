package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for Shell manipulation
 * 
 * @author Jiri Peterka
 * 
 */
public interface Shell extends Widget{

	/**
	 * Return frame title of a Shell
	 * 
	 * @return
	 */
	String getText();

	/**
	 * Set focus to shell
	 */
	void setFocus();

	/**
	 * Closes shell
	 */
	void close();
	
	org.eclipse.swt.widgets.Shell getSWTWidget();

}
