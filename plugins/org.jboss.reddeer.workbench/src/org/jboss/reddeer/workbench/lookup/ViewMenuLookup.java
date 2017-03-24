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
package org.jboss.reddeer.workbench.lookup;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.workbench.core.lookup.WorkbenchShellLookup;
import org.jboss.reddeer.core.lookup.MenuLookup;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.eclipse.ui.IViewSite;

/**
 * View lookup
 * @author rawagner
 *
 */
public class ViewMenuLookup {

	private static final Logger log = Logger.getLogger(ViewMenuLookup.class);
	private static ViewMenuLookup instance;
	private MenuLookup ml = MenuLookup.getInstance();

	private ViewMenuLookup() {
	}

	public static ViewMenuLookup getInstance() {
		if (instance == null) {
			instance = new ViewMenuLookup();
		}
		return instance;
	}
	
	/**
	 * Returns MenuItems of currently active view
	 * @return Array of Menu Items of currently active view
	 */
	public MenuItem[] getViewMenuItems(){
		IWorkbenchPart part = getActivePart(false);
		IMenuManager m = ((IViewSite) part.getSite()).getActionBars().getMenuManager();
		
		if (m instanceof MenuManager) {
			Menu men =  Display.syncExec(new ResultRunnable<Menu>() {
				
				@Override
				public Menu run() {
					MenuManager manager = ((MenuManager) m);
					manager.createContextMenu(WorkbenchShellLookup.getInstance().getWorkbenchShell());
					manager.update(false);
					return ((MenuManager) m).getMenu();
					
				}
			});
			return ml.getItemsFromMenu(men);
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
