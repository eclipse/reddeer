package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public class AbstractToolItem implements ToolItem {

	protected org.eclipse.swt.widgets.ToolItem toolItem;

	public void click() {
		Thrower.objectIsNull(toolItem, "ToolItem is null" );
		WidgetLookup.getInstance().sendClickNotifications(toolItem);
	}
	
	public String getToolTipText() {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return toolItem.getToolTipText();
			}
			
		});
	}

	
	
}
