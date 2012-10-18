package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.lookup.impl.ToolBarLookup;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * Workbench Tool Item implementation 
 * @author Jiri Peterka
 *
 */
public class WorkbenchToolItem extends AbstractToolItem {

	protected ToolItem toolItem;
	
	/**
	 * Create Workbench ToolItem containing given tooltip 
	 * @param toolTip
	 */
	public WorkbenchToolItem(String toolTip) {
		ToolBarLookup tl = new ToolBarLookup();
		ToolBar[] workbenchToolBars = tl.getWorkbenchToolBars();
		ToolItem ti = null;
		for (ToolBar t : workbenchToolBars) {
			ti = tl.getToolItem(t, toolTip);
			if (ti != null) break;
		}
		Thrower.objectIsNull(ti, "ToolItem with toolTip " + toolTip + " cannot be found" );
		toolItem = ti; 
	}
	
	@Override
	public void click() {
		Thrower.objectIsNull(toolItem, "ToolItem is null" );
		WidgetLookup.getInstance().sendClickNotifications(toolItem);
	}
}
