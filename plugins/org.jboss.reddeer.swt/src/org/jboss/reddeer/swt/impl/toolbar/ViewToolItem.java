package org.jboss.reddeer.swt.impl.toolbar;

import org.hamcrest.Matcher;

/**
 * ViewToolItem implementation. This will look for ToolItem inside current
 * active View ToolBar. </p> If no View is active, exception is thrown.
 * 
 * @author Jiri Peterka
 *
 */
public class ViewToolItem extends DefaultToolItem {

	/**
	 * Constructor for ToolItem within currently active View with
	 * {@code tooltip} text.
	 * 
	 * @param tooltip
	 *            tooltip text to look for.
	 */

	public ViewToolItem(String tooltip) {
		super(new ViewToolBar(), tooltip);
	}

	/**
	 * Constructor for ToolItem within currently active View matching
	 * {@code matcher}.
	 * 
	 * @param matcher
	 *            matcher to match against tool items.
	 */

	public ViewToolItem(Matcher<String> matcher) {
		super(new ViewToolBar(), matcher);
	}
}
