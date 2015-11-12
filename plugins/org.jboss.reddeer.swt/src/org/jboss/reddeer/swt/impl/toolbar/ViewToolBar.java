package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.core.lookup.ToolBarLookup;

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
	 */
	public ViewToolBar() {
		super(ToolBarLookup.getInstance().getViewToolBar());
	}

}
