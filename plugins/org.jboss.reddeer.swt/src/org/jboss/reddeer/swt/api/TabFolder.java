package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for tab folder manipulation.
 * 
 * @author Jiri Peterka
 * @author Andrej Podhradsky
 *
 */
public interface TabFolder extends Widget {

	/**
	 * Gets tab item labels.
	 * 
	 * @return labels of the tab item
	 */
	String[] getTabItemLabels();
}
