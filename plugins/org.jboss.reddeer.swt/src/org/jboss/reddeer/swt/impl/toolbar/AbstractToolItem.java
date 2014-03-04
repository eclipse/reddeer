package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolItem implements ToolItem {

	protected org.eclipse.swt.widgets.ToolItem toolItem;
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public void click() {
		Thrower.objectIsNull(toolItem, "ToolItem is null" );
		WidgetHandler.getInstance().click(toolItem);
	}
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public String getToolTipText() {	
		String tooltipText;
		tooltipText = WidgetHandler.getInstance().getToolTipText(toolItem);
		return tooltipText;
	}
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public boolean isSelected() {
		return WidgetHandler.getInstance().isSelected(toolItem);
	}
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public void toggle(boolean toggle) {
		if (isSelected() != toggle){
			click();
		}		
	}
	
	public org.eclipse.swt.widgets.ToolItem getSWTWidget(){
		return toolItem;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(toolItem);
	}
}
