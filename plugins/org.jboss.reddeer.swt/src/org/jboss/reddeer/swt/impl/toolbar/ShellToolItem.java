package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.lookup.ToolBarLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * ShellToolItem implementation. It expects shell where toolbar should be found on
 * @author jjankovi
 *
 */
public class ShellToolItem extends AbstractToolItem {
	
	/**
	 * Lookup for ShellTooItem with no additional parameters
	 */
	public ShellToolItem() {
		this(null, 0);
	}
	
	/**
	 * Lookup for ShellTooItem with given index 
	 * @param index
	 */
	public ShellToolItem(int index) {
		this(null, index);
	}
	
	/**
	 * Lookup for ShellTooItem with given tooltip
	 * @param tooltip
	 */
	public ShellToolItem(String tooltip) {
		this(new TextMatcher(tooltip), 0);
	}
	
	/**
	 * Lookup for ShellTooItem with given matcher
	 * @param matcher
	 */
	public ShellToolItem(Matcher<String> matcher) {
		this(matcher, 0);
	}
	
	/**
	 * Lookup for ShellTooItem with given matcher and index
	 * @param matcher
	 */
	public ShellToolItem(Matcher<String> matcher, int index) {
		ToolBarLookup tl = new ToolBarLookup();
		final ToolBar shellToolbar = tl.getShellToolBar();
		ToolItem ti = null;
		ti = tl.getToolItem(shellToolbar, matcher, index);			
		Thrower.objectIsNull(ti, "ToolItem " + 
				((matcher==null)?
				"":
				"with toolTip " + matcher.toString()) + 
				"with index: " + index + " cannot be found");
		toolItem = ti;
	}
	
}
