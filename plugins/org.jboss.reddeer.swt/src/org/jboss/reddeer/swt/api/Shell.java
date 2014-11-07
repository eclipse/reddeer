package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for shell manipulation.
 * 
 * @author Jiri Peterka
 * 
 */
public interface Shell extends Widget, ReferencedComposite {

	/**
	 * Returns title of the shell.
	 * 
	 * @return title of the shell.
	 */
	String getText();
	
	/**
	 * Check if shell is visible.
	 * @return true if shell is visible, false otherwise
	 */
	boolean isVisible();

	/**
	 * Sets focus on the shell.
	 */
	void setFocus();
	
	/**
	 * Checks if shell is focused.
	 * @return true if shell is focused, false otherwise
	 */
	boolean isFocused();

	/**
	 * Closes the shell.
	 */
	void close();
	
	org.eclipse.swt.widgets.Shell getSWTWidget();

}
