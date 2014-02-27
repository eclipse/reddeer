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
	 * Returns hyperlink's text 
	 * @return
	 */
	String getText();

	/**
	 * Cliks the hyperlink
	 */
	void activate();
}
