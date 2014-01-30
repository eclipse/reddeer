package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.lookup.WidgetLookup;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolBar implements ToolBar {

		
	protected org.eclipse.swt.widgets.ToolBar toolBar;
	
	public org.eclipse.swt.widgets.ToolBar getSWTWidget(){
		return toolBar;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(toolBar);
	}
	
	
}
