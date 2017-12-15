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
package org.eclipse.reddeer.workbench.impl.part;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.CTabItem;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.eclipse.reddeer.swt.impl.menu.DefaultMenu;
import org.eclipse.reddeer.workbench.api.WorkbenchPart;
import org.eclipse.reddeer.workbench.handler.WorkbenchPartHandler;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Abstract class for all WorkbenchPart implementations
 * @author rawagner
 *
 */
public abstract class AbstractWorkbenchPart implements WorkbenchPart {

	protected static final Logger log = Logger.getLogger(AbstractWorkbenchPart.class);
	
	protected CTabItem cTabItem;
	protected MPart mPart;
	
	
	public AbstractWorkbenchPart(MPart mPart) {
		this.mPart = mPart;
		this.cTabItem = new DefaultCTabItem(new WorkbenchShell(), mPart.getLabel());
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
	public void activate() {
		cTabItem.activate();
		
	}

	@Override
	public void close() {
		cTabItem.close();
		
	}

	@Override
	public boolean isDirty() {
		return mPart.isDirty();
	}

	@Override
	public void save() {
		WorkbenchPartHandler.getInstance().save(mPart);
	}

	@Override
	public void close(boolean save) {
		WorkbenchPartHandler.getInstance().save(mPart);
		close();
	}

	@Override
	public Menu getContextMenu() {
		for(MMenu menu: mPart.getMenus()) {
			if(menu.getElementId().startsWith("popup:") && menu.getWidget() instanceof org.eclipse.swt.widgets.Menu) {
				return new DefaultMenu((org.eclipse.swt.widgets.Menu) menu.getWidget());
			}
		}
		return null;
	}

}