package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Link manipulation
 * @author Jiri Peterka
 *
 */
public interface Link extends Widget{
	
	/**
	 * Returns text of link
	 * @return
	 */
	String getText();
	
	/**
	 * Clicks on link
	 */
	void click();
	
	org.eclipse.swt.widgets.Link getSWTWidget();

}
