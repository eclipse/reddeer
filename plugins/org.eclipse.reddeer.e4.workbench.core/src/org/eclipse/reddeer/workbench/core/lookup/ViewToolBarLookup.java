/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.core.lookup;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.reddeer.workbench.core.exception.E4WorkbenchCoreLayerException;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;

public class ViewToolBarLookup {
	
	private static ViewToolBarLookup instance;
	
	private ViewToolBarLookup(){}
	
	public static ViewToolBarLookup getInstance(){
		if(instance == null){
			instance = new ViewToolBarLookup();
		}
		return instance;
	}

	/**
	 * Gets ToolBar of currently active View.
	 * 
	 * @return tool bar of active view
	 * @throws E4WorkbenchCoreLayerException if there is no active view
	 */
	public ToolBar getViewToolBar() {
		MToolBar toolbar = WorkbenchPartLookup.getInstance().getActiveWorkbenchPart().getToolbar();
		if(toolbar != null && toolbar.getWidget() instanceof ToolBar) {
			return (ToolBar) toolbar.getWidget();
		}
		return null;
	}
}
