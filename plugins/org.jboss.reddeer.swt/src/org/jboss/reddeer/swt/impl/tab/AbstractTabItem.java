/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.tab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TabItem;
import org.jboss.reddeer.core.handler.TabItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all TabItem implementations
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractTabItem extends AbstractWidget<org.eclipse.swt.widgets.TabItem> implements TabItem {

	private static final Logger logger = Logger.getLogger(AbstractTabItem.class);

	protected org.eclipse.swt.widgets.TabFolder swtParent;
	
	private TabItemHandler tabItemHandler = TabItemHandler.getInstance();

	protected AbstractTabItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.TabItem.class, refComposite, index, matchers);
		swtParent = TabItemHandler.getInstance().getTabFolder(swtWidget);
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
		tabItemHandler.select(swtWidget);
		tabItemHandler.notifyTabFolder(
			tabItemHandler.createEventForTabItem(swtWidget,SWT.Selection),
			swtParent);
	}
	
	@Override
	public Control getControl(){
		return tabItemHandler.getControl(swtWidget);
	}
	
	/**
	 * See {@link TabItem}.
	 *
	 * @return the text
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
}
