package org.jboss.reddeer.swt.impl.toolbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.ToolItemLookup;
import org.jboss.reddeer.swt.matcher.WithTooltipTextMatcher;

/**
 * Workbench Tool Item implementation.
 * @author Jiri Peterka
 * 
 * @deprecated since 0.6 Please use {@link DefaultToolItem}.
 *
 */
public class WorkbenchToolItem extends AbstractToolItem {
	
	/**
	 * Create Workbench ToolItem containing given tooltip.
	 * @param toolTip
	 */
	public WorkbenchToolItem(String toolTip) {
		this(new WithTooltipTextMatcher(toolTip));
	}	
	
	/**
	 * Create Workbench ToolItem matching given matcher. 
	 * @param matcher
	 */
	public WorkbenchToolItem(Matcher<String> matcher) {
		toolItem = ToolItemLookup.getInstance().getWorkbenchToolItem(matcher);
	}
}
