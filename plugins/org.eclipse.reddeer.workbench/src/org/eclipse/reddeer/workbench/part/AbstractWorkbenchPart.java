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
package org.eclipse.reddeer.workbench.part;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.CTabItem;
import org.eclipse.reddeer.workbench.api.WorkbenchPart;
import org.eclipse.reddeer.workbench.handler.WorkbenchPartHandler;

/**
 * Abstract class for all WorkbenchPart implementations
 * @author rawagner
 *
 */
public abstract class AbstractWorkbenchPart implements WorkbenchPart {

	protected static final Logger log = Logger.getLogger(AbstractWorkbenchPart.class);
	
	protected CTabItem cTabItem;
	
	
	public AbstractWorkbenchPart(CTabItem cTabItem) {
		this.cTabItem = cTabItem;
	}

	@Override
	public String getTitle() {
		return cTabItem.getText();
	}

	@Override
	public Image getTitleImage() {
		return cTabItem.getImage();
	}
	
	@Override
	public String getTitleToolTip() {
		return cTabItem.getToolTipText();
	}
	
	@Override
	public Control getControl() {
		return cTabItem.getControl();
	}
	
	@Override
	public boolean isActive() {
		return cTabItem.isActive();
	}
	
	@Override
	public void minimize() {
		activate();
		log.info("Minimize workbench part");
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MINIMIZE);
	}
	
	@Override
	public void maximize() {
		activate();
		log.info("Maximize workbench part");
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MAXIMIZE);
	}
	
	@Override
	public void restore() {
		activate();
		log.info("Restore workbench part");
		// in order to restore maximized window maximized action has to be called
		WorkbenchPartHandler.getInstance().performAction(ActionFactory.MAXIMIZE);
	}

}