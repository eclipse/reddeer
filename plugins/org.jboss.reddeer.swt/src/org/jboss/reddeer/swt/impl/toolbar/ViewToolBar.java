package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.lookup.ToolBarLookup;

/**
 * Workbench toolbar implementation
 * @author Jiri Peterka
 *
 */
public class ViewToolBar extends AbstractToolBar {

	public ViewToolBar() {
		ToolBarLookup tl = new ToolBarLookup();
		toolBar = tl.getPartToolbar();
	}
	
	
}
