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
package org.eclipse.reddeer.core.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link MenuItem} widgets.
 * 
 * @author Jiri Peterka
 *
 */
public class MenuItemHandler extends ItemHandler{
	private static final Logger log = Logger.getLogger(MenuItemHandler.class);
	private static MenuItemHandler instance;
	
	/**
	 * Gets instance of MenuHandler.
	 * 
	 * @return instance of MenuHandler
	 */
	public static MenuItemHandler getInstance(){
		if(instance == null){
			instance = new MenuItemHandler();
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
	 * Gets menu of menu item
	 * @param item to get menu of
	 * @return menu of menu item
	 */
	public Menu getParent(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return item.getParent();
			}
		});
	}

	/**
	 * Selects (click) for MenuItem.
	 * @param item given item which is going to be selected (clicked)
	 */
	public void select(final MenuItem item) {
		if (!isEnabled(item)) {
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
					final Event event = createSelectionEvent(item);

					log.info("Select menu item: " + item.getText());
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
				return item.getEnabled();
			}
		});
	}
	
	/**
	 * Gets menu of given menu item
	 * @param item to handle
	 * @return menu of given menu item
	 */
	public Menu getMenuFromMenuItem(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Menu>() {
			@Override
			public Menu run() { 
				return item.getMenu(); 
			}
		});
	}

	/**
	 * Returns path of given menu item
	 * @param item to get path of
	 * @return path of given menu item
	 */
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
	
	private Event createSelectionEvent(final MenuItem menuItem) {
		final Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = menuItem;
		event.item = menuItem;
		event.display = menuItem.getDisplay();
		event.type = SWT.Selection;
		return event;
	}

	/**
	 * Gets cascade menu of given menu item
	 * @param swtWidget to handle
	 * @return cascade menu of given menu item
	 */
	public Menu getMenu(MenuItem swtWidget) {
		return Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return swtWidget.getMenu();
			}
		});
	}

}
