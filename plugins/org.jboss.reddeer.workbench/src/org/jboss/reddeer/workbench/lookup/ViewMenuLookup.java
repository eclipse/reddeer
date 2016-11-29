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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.core.lookup.MenuLookup;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.eclipse.ui.IViewSite;

public class ViewMenuLookup {

	
	private static final Logger log = Logger.getLogger(ViewMenuLookup.class);
	private static ViewMenuLookup instance;
	private MenuLookup ml = MenuLookup.getInstance();
	
	private ViewMenuLookup(){}
	
	public static ViewMenuLookup getInstance(){
		if (instance == null) {
			instance = new ViewMenuLookup();
		} 
		return instance;
	}
	
	/**
	 * Gets all tool bar menu items.
	 *  
	 * @return list of MenuManager instances related to tool bar menus.
	 */
	public List<IContributionItem> getViewMenus(){	
		IWorkbenchPart part = getActivePart(false);
		
		List<IContributionItem> menuContributionItems = new ArrayList<IContributionItem>();
		IMenuManager m = ((IViewSite) part.getSite()).getActionBars().getMenuManager();
		if (m instanceof MenuManager) {
			menuContributionItems.addAll(Arrays.asList(((MenuManager) m).getItems()));
		}
		if(menuContributionItems.isEmpty()){
			throw new WorkbenchLayerException("No Menu found in active part: "+part.getTitle());
		}
		return menuContributionItems;
	}
	
	/**
	 * Looks for ActionContributionItem matching matchers.
	 *
	 * @param contItems the cont items
	 * @param matchers menu item text matchers
	 * @return action contribution item
	 */
	public ActionContributionItem lookFor(final List<IContributionItem> contItems, final Matcher<String>... matchers) {	
		ActionContributionItem contItem = Display.syncExec(new ResultRunnable<ActionContributionItem>(){

			@Override
			public ActionContributionItem run() {
				ActionContributionItem currentItem = null;
				List<IContributionItem> currentMenuContributionItems = contItems;
				for (Matcher<String> m : matchers) {
					currentItem = null;
					for (IContributionItem i : currentMenuContributionItems) {
						if(i instanceof ActionContributionItem){
							String normalized = ((ActionContributionItem)i).getAction().getText().replace("&", "");
							log.debug("Found item:'" + normalized + "'");
							if (m.matches(normalized)) {
								log.info("Item match:'" + normalized + "'");
								currentItem =(ActionContributionItem)i;
								break;
							} 
						} else if(i instanceof MenuManager){
							String normalized =((MenuManager)i).getMenuText().replace("&", "");
							log.debug("Found Menu Manager:'" + normalized + "'");
							if (m.matches(normalized)) {
								log.debug("Menu Manager match:'" + normalized + "'");
								currentMenuContributionItems = Arrays.asList(((MenuManager) i).getItems());
							}
						}
					}
			
				}
				return currentItem;
			}
		});
		return contItem;
	}
	
	public MenuItem lookForViewMenu(final List<IContributionItem> contItems, final Matcher<String>... matchers) {
		IWorkbenchPart part = getActivePart(false);
		final IMenuManager m = ((IViewSite) part.getSite()).getActionBars().getMenuManager();
		if (!(m instanceof MenuManager)) {
			throw new WorkbenchLayerException("No Menu found in active part: " + part.getTitle());
		}
		MenuItem contItem = Display.syncExec(new ResultRunnable<MenuItem>() {

			@Override
			public MenuItem run() {
				MenuItem currentItem = null;
				Menu currentMenu = ((MenuManager) m).getMenu();
				for (Matcher<String> matcher : matchers) {
					for (MenuItem item : currentMenu.getItems()) {
						String normalized = item.getText().replace("&", "");
						if (matcher.matches(normalized)) {

							// is submenu?
							if (item.getMenu() != null) {

								// populate submenu
								ml.sendHide(item.getMenu(), true);
								ml.sendShowUI(item.getMenu());

								// drill down
								currentMenu = item.getMenu();
								break;
							} else {
								
								// we got a match
								currentItem = item;
								break;
							}
						}
					}
				}
				return currentItem;
			}
		});
		return contItem;
	}
	
	/**
	 * Gets contribution items from focused control.
	 * Used in situation when menu could contain dynamic menu from e4.
	 * 
	 * @return list of menu contribution items
	 */
	public List<IContributionItem> getMenuContributionItems() {
		List<IContributionItem> contItems = new ArrayList<IContributionItem>();
		final Control control  = WidgetLookup.getInstance().getFocusControl();
		final Menu menu = ml.getControlMenu(control);
		
		contItems = Display.syncExec(new ResultRunnable<List<IContributionItem>>() {
			@Override
			public List<IContributionItem> run() {
				List<IContributionItem> contItemsRun = new ArrayList<IContributionItem>();
				ml.sendHide(menu, true);
				ml.sendShowUI(menu);
				if(menu.getData() != null && menu.getData() instanceof MenuManager){
					contItemsRun.addAll(Arrays.asList(((MenuManager)menu.getData()).getItems()));
					log.debug("Menu manager found");
				} else {
					log.debug("Menu manager not found");
				}
	
				return contItemsRun;
			}
		});
		return contItems;
	}
	
	/**
	 * Gets active Workbench Part.
	 * @param restore should restore the part
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
