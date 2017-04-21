/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.toolbar;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default ToolItem implementation.<br> Do not use for ToolItem inside View
 * ToolBar.
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
	
	public DefaultToolItem(org.eclipse.swt.widgets.ToolItem widget){
		super(widget);
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
		super(rc,index);
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
		super(rc,index,matchers);
	}
}
