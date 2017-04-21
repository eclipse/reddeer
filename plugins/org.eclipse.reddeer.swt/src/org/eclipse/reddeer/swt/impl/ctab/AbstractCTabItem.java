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
package org.eclipse.reddeer.swt.impl.ctab;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.handler.CTabItemHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.CTabFolder;
import org.eclipse.reddeer.swt.api.CTabItem;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.widgets.AbstractItem;

/**
 * Abstract class for all CTabItem implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCTabItem extends AbstractItem<org.eclipse.swt.custom.CTabItem> implements CTabItem {

	private static final Logger logger = Logger.getLogger(AbstractCTabItem.class);

	protected org.eclipse.swt.custom.CTabFolder swtParent;

	private CTabItemHandler cTabItemHandler = CTabItemHandler.getInstance();

	protected AbstractCTabItem(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.CTabItem.class, referencedComposite, index, matchers);
		this.swtParent = cTabItemHandler.getCTabFolder(swtWidget);
	}
	
	protected AbstractCTabItem(org.eclipse.swt.custom.CTabItem swtWidget) {
		super(swtWidget);
	}
	
	/**
	 * See {@link CTabItem}.
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		cTabItemHandler.activate(swtWidget);
	}

	/**
	 * See {@link CTabItem}.
	 *
	 * @return the tool tip text
	 */
	@Override
	public String getToolTipText() {
		return CTabItemHandler.getInstance().getToolTipText(swtWidget);
	}

	/**
	 * See {@link CTabItem}.
	 */
	@Override
	public void close() {
		logger.info("Close CTabItem " + getText());
		if (isShowClose()) {
			activate();
			cTabItemHandler.clickCloseButton(swtWidget);
		} else {
			throw new SWTLayerException("CTabItem with label " + getText()
					+ " has no close button");
		}
	}

	/**
	 * See {@link CTabItem}.
	 *
	 * @return true, if is show close
	 */
	@Override
	public boolean isShowClose() {
		return cTabItemHandler.isShowClose(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.CTabItem#isShowing()
	 */
	@Override
	public boolean isShowing() {
		return cTabItemHandler.isShowing(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.CTabItem#getFolder()
	 */
	@Override
	public CTabFolder getFolder() {
		return new DefaultCTabFolder(swtParent);
	}
	
	@Override
	public Control getControl(){
		return cTabItemHandler.getControl(swtWidget);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((swtWidget == null) ? 0 : swtWidget.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCTabItem other = (AbstractCTabItem) obj;
		if (swtWidget == null) {
			if (other.swtWidget != null)
				return false;
		} else if (!swtWidget.equals(other.swtWidget))
			return false;
		return true;
	}
}
