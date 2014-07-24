package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolBar implements ToolBar{

		
	protected org.eclipse.swt.widgets.ToolBar toolBar;
	
	public org.eclipse.swt.widgets.ToolBar getSWTWidget(){
		return toolBar;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(toolBar);
	}
	
	@Override
	public Control getControl() {
		return toolBar;
	}
}
