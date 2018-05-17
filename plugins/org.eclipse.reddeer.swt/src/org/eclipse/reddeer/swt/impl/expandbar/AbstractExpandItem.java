/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.expandbar;

import org.eclipse.swt.widgets.Control;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.ExpandBar;
import org.eclipse.reddeer.swt.api.ExpandItem;
import org.eclipse.reddeer.core.handler.ExpandItemHandler;
import org.eclipse.reddeer.swt.widgets.AbstractItem;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;

/**
 * Basic ExpandBarItem class is abstract class for all Expand Bar Item implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandItem extends AbstractItem<org.eclipse.swt.widgets.ExpandItem> implements ExpandItem {

	private static final Logger logger = Logger.getLogger(AbstractExpandItem.class);

	protected AbstractExpandItem(final org.eclipse.swt.widgets.ExpandItem swtExpandItem) {
		super(swtExpandItem);
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
			ExpandItemHandler.getInstance().expand(getSWTWidget(), getParent().getSWTWidget());
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
			ExpandItemHandler.getInstance().collapse(getSWTWidget(), getParent().getSWTWidget());
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
	
	/**
	 * See {@link ExpandItem}.
	 *
	 * @return the parent
	 */
	@Override
	public ExpandBar getParent() {
		return new DefaultExpandBar(ExpandItemHandler.getInstance().getParent(swtWidget));
	}
	
	@Override
	public org.eclipse.reddeer.swt.api.Control<?> getParentControl() {
		return getParent();
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