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
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Control;
import org.eclipse.reddeer.swt.api.ToolBar;
import org.eclipse.reddeer.swt.api.ToolItem;
import org.eclipse.reddeer.swt.widgets.AbstractItem;
import org.eclipse.reddeer.core.exception.Thrower;
import org.eclipse.reddeer.core.handler.ToolItemHandler;
import org.eclipse.reddeer.core.lookup.ToolItemLookup;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolItem extends AbstractItem<org.eclipse.swt.widgets.ToolItem> implements ToolItem {
	
	protected AbstractToolItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.ToolItem.class, refComposite == null ? ToolItemLookup.getInstance().findReferencedComposite() : refComposite, index, matchers);
	}
	
	protected AbstractToolItem(org.eclipse.swt.widgets.ToolItem widget){
		super(widget);
	}

	private static final Logger log = Logger.getLogger(AbstractToolItem.class);

	/**
	 * See {@link ToolItem}}.
	 */
	@Override
	public void click() {
		Thrower.objectIsNull(getSWTWidget(), "ToolItem is null" );
		log.info("Click tool item " + getToolTipText());
		ToolItemHandler.getInstance().click(getSWTWidget());
	}
	
	/**
	 * See {@link ToolItem}}.
	 *
	 * @return the tool tip text
	 */
	@Override
	public String getToolTipText() {	
		String tooltipText;
		tooltipText = ToolItemHandler.getInstance().getToolTipText(swtWidget);
		return tooltipText;
	}
	
	/**
	 * See {@link ToolItem}}.
	 *
	 * @return true, if is selected
	 */
	@Override
	public boolean isSelected() {
		return ToolItemHandler.getInstance().isSelected(swtWidget);
	}
	
	/**
	 * See {@link ToolItem}}.
	 *
	 * @param toggle the toggle
	 */
	@Override
	public void toggle(boolean toggle) {
		log.info((toggle ? "Click" : "Unclick") + " tool item " + getToolTipText());
		if (isSelected() != toggle){
			click();
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.widgets.AbstractWidget#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return ToolItemHandler.getInstance().isEnabled(swtWidget);
	}
	
	@Override
	public ToolBar getParent() {
		return new DefaultToolBar(ToolItemHandler.getInstance().getParent(swtWidget));
	}
	
	@Override
	public Control<?> getParentControl() {
		return getParent();
	}
}
