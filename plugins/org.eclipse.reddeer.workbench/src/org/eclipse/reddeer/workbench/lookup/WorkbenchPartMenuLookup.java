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
package org.eclipse.reddeer.workbench.lookup;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchShellLookup;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.ui.IViewSite;

/**
 * View lookup
 * @author rawagner
 *
 */
public class WorkbenchPartMenuLookup {
	
	private static WorkbenchPartMenuLookup instance;

	public static WorkbenchPartMenuLookup getInstance() {
		if (instance == null) {
			instance = new WorkbenchPartMenuLookup();
		}
		return instance;
	}
	
	/**
	 * Returns Menu of currently active view
	 * @return Menu of currently active view
	 */
	public Menu getViewMenu() {
		IWorkbenchPart part = getActivePart(false);
		IMenuManager m = ((IViewSite) part.getSite()).getActionBars().getMenuManager();
		
		if (m instanceof MenuManager) {
			return  Display.syncExec(new ResultRunnable<Menu>() {
				
				@Override
				public Menu run() {
					MenuManager manager = ((MenuManager) m);
					manager.createContextMenu(WorkbenchShellLookup.getInstance().getWorkbenchShell());
					manager.update(false);
					return ((MenuManager) m).getMenu();
					
				}
			});
		}
		return null;
	}

	/**
	 * Gets active Workbench Part.
	 * 
	 * @param restore
	 *            should restore the part
	 * @return active WorkbenchPart
	 */
	private IWorkbenchPart getActivePart(final boolean restore) {
		IWorkbenchPart result = Display.syncExec(new ResultRunnable<IWorkbenchPart>() {

			@Override
			public IWorkbenchPart run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				IWorkbenchPartReference activePartReference = activePage.getActivePartReference();
				IWorkbenchPart part = activePartReference.getPart(restore);
				return part;
			}
		});
		return result;
	}
}