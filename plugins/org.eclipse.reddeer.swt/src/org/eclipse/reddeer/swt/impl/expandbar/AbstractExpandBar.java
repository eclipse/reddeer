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
package org.eclipse.reddeer.swt.impl.expandbar;

import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.ExpandBar;
import org.eclipse.reddeer.swt.api.ExpandItem;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.handler.ExpandBarHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all Expand Bar implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandBar extends AbstractControl<org.eclipse.swt.widgets.ExpandBar> implements ExpandBar {

	private static final Logger logger = Logger.getLogger(AbstractExpandBar.class);

	protected AbstractExpandBar(org.eclipse.swt.widgets.ExpandBar swtExpandBar){
		super(swtExpandBar);
	}
	
	protected AbstractExpandBar(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.ExpandBar.class, referencedComposite, index, matchers);
	}
	
	/**
	 * See {@link ExpandBar}.
	 *
	 * @return the items count
	 */
	@Override
	public int getItemsCount() {
		return getItems().size();
	}
	
	/**
	 * See {@link ExpandBar}.
	 *
	 * @return the items
	 */
	@Override
	public List<ExpandItem> getItems() {
		List<org.eclipse.swt.widgets.ExpandItem> items = ExpandBarHandler.getInstance().getItems(swtWidget);
		List<org.eclipse.reddeer.swt.api.ExpandItem> rdItems = items.stream().map(t -> new DefaultExpandItem(t)).collect(Collectors.toList());
		return rdItems;
	}
	
	/**
	 * See {@link ExpandBar}.
	 */
	@Override
	public void setFocus() {
		ControlHandler.getInstance().setFocus(this.getSWTWidget());

	}
	
	/**
	 * See {@link ExpandBar}.
	 */
	@Override
	public void expandAll() {
		logger.info("Expand all expand bar items");
		for (ExpandItem expandBarItem : getItems()){
			expandBarItem.expand();
		}
	}
	
	/**
	 * See {@link ExpandBar}.
	 */
	@Override
	public void collapseAll() {
		logger.info("Collapse all expand bar items");
		for (ExpandItem expandBarItem : getItems()){
			expandBarItem.collapse();
		}
	}
}
