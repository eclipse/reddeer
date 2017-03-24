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
package org.jboss.reddeer.swt.impl.expandbar;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandItem;
import org.jboss.reddeer.core.handler.ExpandItemHandler;
import org.jboss.reddeer.swt.widgets.AbstractItem;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * Basic ExpandBarItem class is abstract class for all Expand Bar Item implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandItem extends AbstractItem<org.eclipse.swt.widgets.ExpandItem> implements ExpandItem {

	private static final Logger logger = Logger.getLogger(AbstractExpandItem.class);

	protected org.eclipse.swt.widgets.ExpandBar swtParent;

	protected AbstractExpandItem(final org.eclipse.swt.widgets.ExpandItem swtExpandItem) {
		super(swtExpandItem);
		this.swtParent = ExpandItemHandler.getInstance().getParent(swtExpandItem);
	}

	/**
	 * See {@link ExpandItem}.
	 *
	 * @return the tool tip text
	 */
	public String getToolTipText() {
		return ExpandItemHandler.getInstance().getToolTipText(swtWidget);
	}

	/**
	 * See {@link ExpandItem}.
	 */
	@Override
	public void expand() {
		expand(TimePeriod.SHORT);
	}
	
	/**
	 * See {@link ExpandItem}.
	 *
	 * @param timePeriod the time period
	 */
	@Override
	public void expand(TimePeriod timePeriod) {
		logger.debug("Expand Expand Bar Item " + getText());
		if (!isExpanded()) {
			ExpandItemHandler.getInstance().expand(getSWTWidget(), getSWTParent());
			AbstractWait.sleep(timePeriod);
			logger.info("Expand Bar Item " + getText()
					+ " has been expanded");
		} else {
			logger.debug("Expand Bar Item " + getText()
					+ " is already expanded. No action performed");
		}
	}
	
	/**
	 * See {@link ExpandItem}.
	 */
	@Override
	public void collapse() {
		logger.debug("Collapse Expand Bar Item " + getText());
		if (isExpanded()) {
			ExpandItemHandler.getInstance().collapse(getSWTWidget(), getSWTParent());
			logger.info("Expand Bar Item " + getText()
					+ " has been collapsed");
		} else {
			logger.debug("Expand Bar Item " + getText()
					+ " is already collapsed. No action performed");
		}
	}
	
	/**
	 * Return control of Expand Bar Item.
	 *
	 * @return the control
	 */
	@Override
	public Control getControl() {
		return swtWidget.getControl();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.ExpandBarItem#getSWTParent()
	 */
	public org.eclipse.swt.widgets.ExpandBar getSWTParent() {
		return swtParent;
	}
	
	/**
	 * See {@link ExpandItem}.
	 *
	 * @return the parent
	 */
	@Override
	public ExpandBar getParent() {
		return new DefaultExpandBar(swtParent);
	}
	
	/**
	 * See {@link ExpandItem}.
	 *
	 * @return true, if is expanded
	 */
	@Override
	public boolean isExpanded() {
		return ExpandItemHandler.getInstance().isExpanded(getSWTWidget());
	}
}