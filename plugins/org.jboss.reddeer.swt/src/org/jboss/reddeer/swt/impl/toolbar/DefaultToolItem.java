package org.jboss.reddeer.swt.impl.toolbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.lookup.ToolItemLookup;
import org.jboss.reddeer.core.matcher.WithTooltipTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default ToolItem implementation.</p> Do not use for ToolItem inside View
 * ToolBar. For this, please use {@link ViewToolItem}.
 * 
 * @author Jiri Peterka
 * @author Radim Hopp
 *
 */
public class DefaultToolItem extends AbstractToolItem {

	/**
	 * Default constructor creating first ToolItem in active shell/view/editor.
	 * 
	 */

	public DefaultToolItem() {
		this(null, 0);
	}

	/**
	 * Constructor for first ToolItem inside given {@link ReferencedComposite}.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} to look into for this ToolItem.
	 */

	public DefaultToolItem(ReferencedComposite rc) {
		this(rc, 0);
	}

	/**
	 * Constructor for ToolItem with given tooltip inside active
	 * shell/view/editor.
	 * 
	 * @param tooltip
	 *            assigned to a ToolItem
	 */
	public DefaultToolItem(String tooltip) {
		this(new WithTooltipTextMatcher(tooltip));
	}

	/**
	 * Constructor for ToolItem matching given matchers inside active
	 * shell/view/editor.
	 * 
	 * @param matcher
	 *            Matcher to match desired ToolItem.
	 */

	public DefaultToolItem(Matcher<?> matcher) {
		this(null, 0, matcher);
	}

	/**
	 * Constructor for nth ToolItem in active shell/view/editor.
	 * 
	 * @param index
	 *            Index of ToolItem
	 */

	public DefaultToolItem(int index) {
		this(null, index);
	}

	/**
	 * Constructor for nth ToolItem with given tooltip in active
	 * workbench/shell.
	 * 
	 * @param index
	 *            Index of ToolItem matching given tooltip.
	 * @param tooltip
	 *            Tooltip to look for.
	 */

	public DefaultToolItem(int index, String tooltip) {
		this(null, index, new WithTooltipTextMatcher(tooltip));
	}

	/**
	 * Constructor for ToolItem withing given ReferencedComposite matching given
	 * matcher.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} to look into for this ToolItem.
	 * @param matcher
	 *            Matcher to match desired ToolItem.
	 */

	public DefaultToolItem(ReferencedComposite rc, Matcher<?> matcher) {
		this(rc, 0, matcher);
	}

	/**
	 * Constructor for ToolItem with given tooltip inside given
	 * {@link ReferencedComposite}.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} to look into for this ToolItem.
	 * @param tooltip
	 *            Tooltip to look for.
	 */

	public DefaultToolItem(ReferencedComposite rc, String tooltip) {
		this(rc, 0, new WithTooltipTextMatcher(tooltip));
	}

	/**
	 * Constructor for nth ToolItem inside given {@link ReferencedComposite}.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} to look into for this ToolItem.
	 * @param index
	 *            Index of ToolItem within given {@link ReferencedComposite}
	 */

	public DefaultToolItem(ReferencedComposite rc, int index) {
		toolItem = ToolItemLookup.getInstance().getToolItem(rc, index);
	}

	/**
	 * Constructor for nth ToolItem matching provided matchers inside given
	 * {@link ReferencedComposite}.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} to look into for this ToolItem.
	 * @param index
	 *            Index of ToolItem.
	 * @param matchers
	 *            Matcher to match desired ToolItem.
	 */

	public DefaultToolItem(ReferencedComposite rc, int index,
			Matcher<?>... matchers) {
		toolItem = ToolItemLookup.getInstance()
				.getToolItem(rc, index, matchers);
	}
}
