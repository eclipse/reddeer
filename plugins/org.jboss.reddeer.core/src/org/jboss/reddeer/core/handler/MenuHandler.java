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
package org.jboss.reddeer.core.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link MenuItem} widgets.
 * 
 * @author Jiri Peterka
 *
 */
public class MenuHandler extends ItemHandler{
	private static final Logger log = Logger.getLogger(MenuHandler.class);
	private static MenuHandler instance;
	
	/**
	 * Gets instance of MenuHandler.
	 * 
	 * @return instance of MenuHandler
	 */
	public static MenuHandler getInstance(){
		if(instance == null){
			instance = new MenuHandler();
		}
		return instance;
	}
	
	/**
	 * Gets selection state of MenuItem.
	 * @param item given menuitem
	 * @return true if menuItem is selected
	 */
	public boolean isSelected(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				if (((item.getStyle() & SWT.RADIO) != 0)
						|| ((item.getStyle() & SWT.CHECK) != 0)) {
					return item.getSelection();
				}
				return false;
			}
		});
	}

	/**
	 * Selects (click) for MenuItem.
	 * @param item given item which is going to be selected (clicked)
	 */
	public void select(final MenuItem item) {

		Boolean enabled = Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return isMenuEnabled(item);
			}
		});

		if (!enabled) {
			throw new CoreLayerException("Menu item is not enabled");
		} else {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					if (((item.getStyle() & SWT.RADIO) != 0)
							|| ((item.getStyle() & SWT.CHECK) != 0)) {
						item.setSelection(!item.getSelection());
					}
				}
			});

			Display.asyncExec(new Runnable() {

				@Override
				public void run() {
					final Event event = new Event();
					event.time = (int) System.currentTimeMillis();
					event.widget = item;
					event.item = item;
					event.display = item.getDisplay();
					event.type = SWT.Selection;

					log.info("Click on menu item: " + item.getText());
					item.notifyListeners(SWT.Selection, event);
				}
			});
			Display.syncExec(new Runnable() {
				@Override
				public void run() {

				}
			});
		}
	}
	
	/**
	 * Check weather or not menuitem is enabled.
	 * @param menuItem given MenuItem 
	 * @return true if MenuItem is enabled
	 */
	private boolean isMenuEnabled(final MenuItem menuItem) {
		boolean enabled = Display.syncExec(new ResultRunnable<Boolean>() {
			public Boolean run() {
				return menuItem.getEnabled();
			}
		});

		return enabled;
	}
	
	
	/**
	 * Returns menuitem text.
	 * @param i given MenuItem
	 * @return text string of given MenuItem
	 */
	public String getMenuItemText(final MenuItem i) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return i.getText();
			}
		});
		log.debug("Queried MenuItem text:\"" + text + "\"");
		return text;
	}
	
	/**
	 * Returns true if given MenuItem is enabled.
	 * 
	 * @param item
	 *            given MenuItem
	 * @return true if given MenuItem is enabled.
	 */

	public boolean isEnabled(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return item.isEnabled();
			}
		});
	}
	
	/**
	 * Returns menu items or null
	 * 
	 * @param item given MenuItem
	 * @return menu items of given menu or null
	 */
	public MenuItem[] getMenuItems(final MenuItem item){
		return Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				Menu menu = item.getMenu();
				if(menu != null){
					return menu.getItems();
				}
				return null;
			}
		});
	}
	
	public Menu getMenuFromMenuItem(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Menu>() {
			@Override
			public Menu run() { 
				return item.getMenu(); 
			}
		});
	}

	public String[] getMenuPath(final MenuItem item) {
		List<String> titles = Display.syncExec(new ResultRunnable<List<String>>() {
			@Override
			public List<String> run() { 
				MenuItem i = item;
				List<String> titles = new ArrayList<>();
				
				while (i != null) {
					titles.add(i.getText());					
					i = i.getParent().getParentItem();					
				}
				return titles;
			}
		});
		
		List<String> path = new ArrayList<>(titles.size());
		for (String title : titles){
			path.add(getLabelFromText(title));
		}
		
		Collections.reverse(path);
		return path.toArray(new String[1]);
	}

	/**
	 * Formats title from Menu object to label. Removes '&amp;' and shortcut from title.
	 * @param text title text attribute of Menu object.
	 * @return Formatted label.
	 */
	public String getLabelFromText(String text) {
		String label = text.trim();
		
		// '\t' separates name and shortcut
		if(label.contains("\t")) 
			label = label.substring(0, label.indexOf("\t"));
		
		return label;		
	}

}
