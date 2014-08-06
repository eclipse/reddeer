package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.handler.ToolItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolItem implements ToolItem {
	
	private static final Logger log = Logger.getLogger(AbstractToolItem.class);

	protected org.eclipse.swt.widgets.ToolItem toolItem;
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public void click() {
		Thrower.objectIsNull(toolItem, "ToolItem is null" );
		log.info("Click tool item " + getToolTipText());
		ToolItemHandler.getInstance().click(toolItem);
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
		return ToolItemHandler.getInstance().isSelected(toolItem);
	}
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public void toggle(boolean toggle) {
		log.info((toggle ? "Click" : "Unclick") + " tool item " + getToolTipText());
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
