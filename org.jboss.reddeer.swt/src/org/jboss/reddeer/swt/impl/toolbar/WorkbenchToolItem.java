package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.lookup.impl.ToolBarLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * Workbench Tool Item implementation 
 * @author Jiri Peterka
 *
 */
public class WorkbenchToolItem extends AbstractToolItem {
	
	/**
	 * Create Workbench ToolItem containing given tooltip 
	 * @param toolTip
	 */
	public WorkbenchToolItem(String toolTip) {
		this(new TextMatcher(toolTip));
	}	
	
	/**
	 * Create Workbench ToolItem matching given toolTip matcher 
	 * @param matcher
	 */
	public WorkbenchToolItem(Matcher<String> toolTipMatcher) {
		ToolBarLookup tl = new ToolBarLookup();		
		ToolBar[] workbenchToolBars = tl.getWorkbenchToolBars();
		ToolItem ti = null;
		for (ToolBar t : workbenchToolBars) {
			ti = tl.getToolItem(t, toolTipMatcher);
			if (ti != null) break;
		}
		Thrower.objectIsNull(ti, "ToolItem matching " + toolTipMatcher.toString() + " cannot be found" );
		toolItem = ti; 
		
	}
}
