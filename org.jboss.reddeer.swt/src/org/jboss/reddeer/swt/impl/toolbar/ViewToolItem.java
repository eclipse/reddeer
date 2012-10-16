package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.jboss.reddeer.swt.lookup.impl.ToolBarLookup;


/**
 * viewToolItem implementation. It expect view where toolbar should be found has 
 * @author Jiri Peterka
 *
 */
public class ViewToolItem extends AbstractToolItem {
 
		
	/**
	 * Lookup for ToolItem with given Tooltip
	 * @param tooltip assigned to a ToolItem
	 */
	public ViewToolItem(String tooltip) {
		ToolBarLookup tl = new ToolBarLookup();
		ToolBar workbenchToolBar = tl.getViewToolbar();
		ToolItem toolItem = tl.getToolItem(workbenchToolBar, tooltip);
		botToolItem = new SWTBotToolbarPushButton(toolItem);
	}
}
