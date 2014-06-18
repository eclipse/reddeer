package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for tab item manipulation.
 * 
 * @author apodhrad
 * 
 */
public interface TabItem extends Widget {

	/**
	 * Activates the tab item.
	 */
	void activate();

	/**
	 * Returns text of the tab item.
	 * 
	 * @return text of the tab item
	 */
	String getText();

	org.eclipse.swt.widgets.TabItem getSWTWidget();
}
