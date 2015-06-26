package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for CTab folder manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface CTabFolder extends Widget {

	/**
	 * Returns selected {@link CTabItem} within the folder
	 * @return selected tab within the folder
	 */
	CTabItem getSelection();
}
