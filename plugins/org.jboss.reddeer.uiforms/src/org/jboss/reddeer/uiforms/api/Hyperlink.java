package org.jboss.reddeer.uiforms.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Represents Hyperlink object
 * 
 * @author Lucia Jelinkova
 *
 */
public interface Hyperlink extends Widget {

	/**
	 * Returns hyperlink's text .
	 *
	 * @return the text
	 */
	String getText();

	/**
	 * Cliks the hyperlink.
	 */
	void activate();
}
