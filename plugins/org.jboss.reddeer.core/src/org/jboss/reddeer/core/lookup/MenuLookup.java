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
package org.jboss.reddeer.core.lookup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.ToolItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Menu lookup provides methods for finding menus and context menus and their items. Works also with dynamic menus.
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * 
 */
@SuppressWarnings("unchecked")
public class MenuLookup {

	private static final Logger log = Logger.getLogger(MenuLookup.class);
	private static MenuLookup instance = null;
	
	private MenuLookup() { }
	
	/**
	 * Gets singleton instance of MenuLookup.
	 * 
	 * @return instance of MenuLookup
	 */
	public static MenuLookup getInstance() {
		if (instance == null) {
			instance = new MenuLookup();
		}
		return instance;
	}

	/**
	 * Looks for MenuItem matching matchers starting on specified array of top level menu items.
	 * 
	 * @param topItems top level menu items
	 * @param matchers menu item text matchers
	 * @return MenuItem matching matchers
	 */
	public MenuItem lookFor(MenuItem[] topItems, Matcher<String>... matchers) {		
		MenuItem lastMenuItem = getMatchingMenuPath(topItems, matchers);
		if (lastMenuItem == null) {
			throw new CoreLayerException("No menu item matching specified path found");
		}
		return lastMenuItem;
	}
	

	/**
	 * Gets top level menu items of focused control.
	 * Does not work with dynamic menus from e4.
	 * 
	 * @return array of top menu items of focused control
	 */
	public MenuItem[] getTopMenuMenuItemsFromFocus() {

		final Control control  = WidgetLookup.getInstance().getFocusControl();
		if (control == null) {
			throw new CoreLayerException(
					"No control has focus. Perhaps something has stolen it? Try to regain focus with for example \"new DefaultShell()\".");
		}
		
		//Send MenuDetect event. Some menus doesn't exist before that..
		WidgetHandler.getInstance().notify(SWT.MenuDetect, control);
		
		final Menu menu = getControlMenu(control);
		
		return getItemsFromMenu(menu);
	}
	
	public MenuItem[] getItemsFromMenu(final Menu menu){
		MenuItem[] items = Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				sendHide(menu, true);
				sendShowUI(menu);				
				return menu.getItems();
			}
		});

		if (items == null) {
			throw new CoreLayerException(
					"Could not find top menu items, menu doesn't exist or wrong focus");
		}

		return items;
	}
	
	/**
	 * Gets menu items from active shell menu bar.
	 * @return array of top menu items of active shell
	 */
	public MenuItem[] getActiveShellTopMenuItems() {
		Shell activeShell = ShellLookup.getInstance().getActiveShell();
		if(activeShell == null){
			throw new CoreLayerException("Cannot find menu bar because there's no active shell");
		}
		return getMenuBarItems(activeShell);	
	}
	
	/**
	 * Gets first level of menu items for specified DropDown ToolItem.
	 * 
	 * @param item DropDown ToolItem to get its menu items
	 * @return first level of menu items
	 */

	public MenuItem[] getToolItemMenuItems(ToolItem item) {
		if (!ToolItemHandler.getInstance().isDropDown(item)) {
			throw new CoreLayerException("Given ToolItem isn't of style SWT.DROP_DOWN");
		}
		final ShowMenuListener l = new ShowMenuListener();
		addMenuListener(l);
		ToolItemHandler.getInstance().clickDropDown(item);
		removeMenuListener(l);
		return Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				l.getMenu().setVisible(false);
				return l.getMenu().getItems();
			}
		});
	}

	private void addMenuListener(final Listener listener) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Display.getDisplay().addFilter(SWT.Show, listener);
			}
		});
	}

	private void removeMenuListener(final Listener listener) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Display.getDisplay().removeFilter(SWT.Show, listener);
			}
		});
	}

	/**
	 * Gets menu bar items.
	 * 
	 * @param s shell where menu bar items are looked up
	 * @return array of menu items of specified shell 
	 */
	private MenuItem[] getMenuBarItems(final Shell s) {

		MenuItem[] items = Display.syncExec(new ResultRunnable<MenuItem[]>() {

			@Override
			public MenuItem[] run() {
				log.info("Getting Menu Bar of shell '" + s.getText() + "'");
				Menu menu = s.getMenuBar();
				if (menu == null){
					return null;
				}
				MenuItem[] items = menu.getItems();
				return items;
			}
		});
		if(items == null){
			String shellText = ShellHandler.getInstance().getText(s);
			throw new CoreLayerException("Cannot find a menu bar of shell " + shellText);
		}
		return items;
	}

	/**
	 * Gets Menu of specified control.
	 * 
	 * @param c control where menu is placed
	 * @return menu placed under specified control
	 */
	public Menu getControlMenu(final Control c) {

		Menu menu = Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				Menu m = c.getMenu();
				return m;
			}
		});

		if (menu == null) {
			throw new CoreLayerException(
					c.getClass() +" Has no menu");
		}

		return menu;	
	}
	
	/**
	 * Gets Menu item matching menu path defined by specified top menu items and matchers.
	 * 
	 * @param topItems top level menu items where to search for menu item
	 * @param matchers matchers to match menu item
	 * @return matching MenuItem
	 */
	private MenuItem getMatchingMenuPath(final MenuItem[] topItems,
			final Matcher<String>... matchers) {
		MenuItem i = Display.syncExec(new ResultRunnable<MenuItem>() {

			@Override
			public MenuItem run() {
				Menu currentMenu = null;
				MenuItem currentItem = null;;
				MenuItem[] menuItems = topItems;
				for (Matcher<String> m : matchers) {
					currentItem = null;
					for (MenuItem i : menuItems) {
						String normalized = i.getText().replace("&", "");
						log.debug("Found menu:'" + normalized + "'");
						if (m.matches(normalized)) {
							log.debug("Item match:" + normalized);
							currentItem = i;
							currentMenu = i.getMenu();
							break;
						} 
					}
					if (currentItem == null){
						return null;
					}
					if (m != matchers[matchers.length-1]) {
						currentMenu = currentItem.getMenu();
						sendShowUI(currentMenu);
						menuItems = currentMenu.getItems();
					} 
				}
				return currentItem;
			}
		});
		return i;
	}


	/**
	 * Sends SWT.Show to widget.
	 * 
	 * @param widget widget where event is sent
	 */
	public void sendShowUI(Widget widget) {
		widget.notifyListeners(SWT.Show, new Event());
	}
		

	/**
	 * Hides menu.
	 * 
	 * @param menu menu to hide
	 * @param recur recursion flag
	 */
	public void sendHide(final Menu menu, final boolean recur) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {

				if (menu != null) {
					menu.notifyListeners(SWT.Hide, new Event());
					if (recur) {
						if (menu.getParentMenu() != null) {
							sendHide(menu.getParentMenu(), recur);
						} else {
							menu.setVisible(false);
						}
					}
				}
			}

		});

	}

	private class ShowMenuListener implements Listener{

		private Menu menu = null;
		
		public Menu getMenu() {
			return menu;
		}
		
		@Override
		public void handleEvent(Event event) {
			if (event.widget instanceof Menu){
				Menu menu = (Menu) event.widget;
				if (event.type == SWT.Show){
					this.menu = menu;
				}
				if (event.type == SWT.Hide){
					this.menu = null;
				}
			}
		}
		
	}
	
}
