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
package org.eclipse.reddeer.core.lookup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.CTabItemHandler;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.core.handler.TabItemHandler;
import org.eclipse.reddeer.core.handler.TableItemHandler;
import org.eclipse.reddeer.core.handler.ToolItemHandler;
import org.eclipse.reddeer.core.handler.TreeItemHandler;

/**
 * Menu lookup provides methods for finding menus and context menus and their items. Works also with dynamic menus.
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * 
 */
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
	 * 
	 * @return
	 */
	public Menu getMenuFromFocusControl() {
		final Control control  = WidgetLookup.getInstance().getFocusControl();
		if (control == null) {
			throw new CoreLayerException(
					"No control has focus. Perhaps something has stolen it? Try to regain focus with for example \"new DefaultShell()\".");
		}
		return ControlHandler.getInstance().getMenu(control);
	}
	
	public Menu getMenuFromActiveShell() {
		Shell activeShell = ShellLookup.getInstance().getActiveShell();
		if(activeShell == null){
			throw new CoreLayerException("Cannot find menu bar because there's no active shell");
		}
		return getShellMenu(activeShell);
	}
	
	/**
	 * Returns menu of given tool item
	 * @param item to get menu of
	 * @return menu of given tool item
	 */
	public Menu getToolItemMenu(ToolItem item) {
		if (!ToolItemHandler.getInstance().isDropDown(item)) {
			throw new CoreLayerException("Given ToolItem isn't of style SWT.DROP_DOWN");
		}
		final ShowMenuListener l = new ShowMenuListener();
		addMenuListener(l);
		try {
			ToolItemHandler.getInstance().clickDropDown(item);
			return Display.syncExec(new ResultRunnable<Menu>() {
				@Override
				public Menu run() {
					l.getMenu().setVisible(false);
					return l.getMenu();
				}
			});
		} finally {
			removeMenuListener(l);
		}
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
	 * Returns menu of given shell
	 * @param shell to handle
	 * @return menu of given shell
	 * @throws CoreLayerException if shell has no menu
	 */
	public Menu getShellMenu(final Shell shell){
		Menu shellMenu = ShellHandler.getInstance().getMenuBar(shell);
		if(shellMenu == null) {
			String shellText = ShellHandler.getInstance().getText(shell);
			throw new CoreLayerException("Cannot find a menu bar of shell " + shellText);
		}
		return shellMenu;
		
	}

	/**
	 * Returns menu of given control
	 * @param control control to handle 
	 * @return menu of given control
	 * @throws CoreLayerException if control has no menu
	 */
	public Menu getControlMenu(final Control c) {
		Menu controlMenu = ControlHandler.getInstance().getMenu(c);
		if(controlMenu == null) {
			throw new CoreLayerException(c.getClass() +" Has no menu");
		}
		return controlMenu;
	}
	
	/**
	 * Returns menu of given control
	 * @param item item to handle 
	 * @return menu of given control
	 * @throws CoreLayerException if control has no menu
	 */
	public Menu getItemMenu(final Item item, final Control parentControl) {
		if(item instanceof TreeItem) {
			TreeItemHandler.getInstance().select((TreeItem)item);
		} else if (item instanceof CTabItem) {
			CTabItemHandler.getInstance().activate((CTabItem)item);
		} else if (item instanceof TabItem) {
			TabItemHandler.getInstance().select((TabItem)item);
		} else if (item instanceof TableItem) {
			TableItemHandler.getInstance().select((TableItem) item);
		}
		Menu controlMenu = ControlHandler.getInstance().getMenu(parentControl);
		if(controlMenu == null) {
			throw new CoreLayerException(parentControl.getClass() +" Has no menu");
		}
		return controlMenu;
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
