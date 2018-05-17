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
package org.eclipse.reddeer.workbench.core.lookup;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.reddeer.workbench.core.exception.WorkbenchCoreLayerException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

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
	 * @throws WorkbenchCoreLayerException if there is no active view
	 */
	public ToolBar getViewToolBar() {
		ToolBar toolbar = Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				ToolBar toolBar = null;
				IWorkbenchPartSite site = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite();
				if (!(site instanceof IViewSite)) {
					throw new WorkbenchCoreLayerException("Active part is not View.");
				}
				IViewSite viewSite = (IViewSite) site;
				IToolBarManager toolBarManager = viewSite.getActionBars()
						.getToolBarManager();
				if (toolBarManager instanceof ToolBarManager) {
					toolBar = ((ToolBarManager) toolBarManager).getControl();
				}
				return toolBar;
			}
		});
		return toolbar;
	}
}
