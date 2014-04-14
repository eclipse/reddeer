package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.lookup.ToolBarLookup;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;

/**
 * Default ToolBar implementation. It expect that shell , view or editor where ToolBar item should be found 
 * @author Jiri Peterka
 *
 */
public class DefaultToolItem extends AbstractToolItem {
 
		
	/**
	 * Lookup for ToolItem with given Tooltip
	 * @param tooltip assigned to a ToolItem
	 */
	public DefaultToolItem(String tooltip) {
		this(new WithTextMatcher(tooltip));
	}
	
	public DefaultToolItem(Matcher<String> matcher) {
		ToolBarLookup tl = new ToolBarLookup();
		ToolBar toolBar = tl.getDefaultToolbar();
		if (toolBar == null)
			throw new SWTLayerException("Default toolbar is null");


		ToolItem ti = null;
		ti = tl.getToolItem(toolBar, matcher);			
		
		Thrower.objectIsNull(ti, "ToolItem with toolTip " + matcher.toString() + " cannot be found" );
		toolItem = ti;
	}
}
