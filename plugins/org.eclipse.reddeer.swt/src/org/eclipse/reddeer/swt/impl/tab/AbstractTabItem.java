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
package org.eclipse.reddeer.swt.impl.tab;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.TabFolder;
import org.eclipse.reddeer.swt.api.TabItem;
import org.eclipse.reddeer.core.handler.TabItemHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractItem;

/**
 * Abstract class for all TabItem implementations
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractTabItem extends AbstractItem<org.eclipse.swt.widgets.TabItem> implements TabItem {

	private static final Logger logger = Logger.getLogger(AbstractTabItem.class);

	protected AbstractTabItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.TabItem.class, refComposite, index, matchers);
	}
	
	protected AbstractTabItem(org.eclipse.swt.widgets.TabItem widget){
		super(widget);
	}
	
	/**
	 * See {@link TabItem}.
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		TabItemHandler.getInstance().select(swtWidget);
	}
	
	@Override
	public Control getControl(){
		return TabItemHandler.getInstance().getControl(swtWidget);
	}
	
	@Override
	public String getToolTipText(){
		return TabItemHandler.getInstance().getToolTipText(swtWidget);
	}
	
	@Override
	public TabFolder getTabFolder(){
		return new DefaultTabFolder(TabItemHandler.getInstance().getTabFolder(swtWidget));
	}
	
	@Override
	public boolean isSelected() {
		return TabItemHandler.getInstance().isSelected(swtWidget);
	}
}
