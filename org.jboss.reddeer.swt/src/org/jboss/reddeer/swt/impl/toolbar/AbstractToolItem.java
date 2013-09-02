package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public class AbstractToolItem implements ToolItem {

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
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return toolItem.getToolTipText();
			}
			
		});
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
}
