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
package org.eclipse.reddeer.core.handler;

import java.util.Arrays;
import java.util.List;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;

/**
 * Contains methods for handling UI operations on {@link Menu} widgets.
 * 
 *
 */
public class MenuHandler extends WidgetHandler{
	
	private static MenuHandler instance;
	
	private MenuHandler() {}

	
	/**
	 * Gets instance of WidgetHandler.
	 * 
	 * @return instance of WidgetHandler
	 */
	public static MenuHandler getInstance(){
		if(instance == null){
			instance = new MenuHandler();
		}
		return instance;
	}
	
	/**
	 * Gets menu items
	 * @param swtMenu to handle
	 * @return menu items of given menu
	 */
	public List<MenuItem> getItems(final Menu swtMenu){
		MenuItem[] items = Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				sendHide(swtMenu, true);
				sendShowUI(swtMenu);				
				return swtMenu.getItems();
			}
		});
		return Arrays.asList(items);
	}
	
	/**
	 * Checks whether menu is visible
	 * @param swtMenu to handle
	 * @return true if menu is visible, false otherwise
	 */
	public boolean isVisible(final Menu swtMenu) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return swtMenu.isVisible();
			}
		});
	}
	
	/**
	 * Checks whether menu is enabled
	 * @param swtMenu to handle
	 * @return true if menu is enabled, false otherwise
	 */
	public boolean isEnabled(final Menu swtMenu) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return swtMenu.isEnabled();
			}
		});
		
	}
	
	/**
	 * Gets parent menu 
	 * @param swtMenu to handle
	 * @return parent menu
	 */
	public Menu getParentMenu(final Menu swtMenu) {
		return Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return swtMenu.getParentMenu();
			}
		});
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
	
}
