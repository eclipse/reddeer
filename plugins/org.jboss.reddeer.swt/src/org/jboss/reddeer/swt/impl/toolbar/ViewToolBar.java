package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.ToolBarLookup;

/**
 * Workbench toolbar implementation
 * 
 * @author Jiri Peterka
 *
 */
public class ViewToolBar extends AbstractToolBar {

	/**
	 * Constructor for ToolBar of currently active View. If no view is active,
	 * {@link SWTLayerException} is thrown
	 * 
	 * @throws {@link SWTLayerException}
	 */

	public ViewToolBar() {
		ToolBarLookup tl = new ToolBarLookup();
		toolBar = tl.getViewToolBar();

	}

}
