package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for TabFolder manipulation
 * 
 * @author Jiri Peterka
 * @author Andrej Podhradsky
 *
 */
public interface TabFolder extends Widget{

	/**
	 * Get tab item labels
	 * 
	 * @return array of tab item labels
	 */
	String[] getTabItemLabels();
}
