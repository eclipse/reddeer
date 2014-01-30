package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for TabItem manipulation.
 * 
 * @author apodhrad
 * 
 */
public interface TabItem extends Widget{

	/**
	 * Activate this tab.
	 */
	void activate();

	/**
	 * Returns the text of tab item
	 * 
	 * @return
	 */
	String getText();
	
	org.eclipse.swt.widgets.TabItem getSWTWidget();
}
