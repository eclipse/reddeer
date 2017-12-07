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
package org.eclipse.reddeer.core.lookup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.MenuHandler;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;

@SuppressWarnings("unchecked")
public class MenuItemLookup {
	
	private static final Logger log = Logger.getLogger(MenuItemLookup.class);
	private static MenuItemLookup instance = null;

	private StringBuilder foundPath;
	private Set<String> foundMenuItems;

	private MenuItemLookup() { 
		foundMenuItems = new HashSet<>();
	}
	
	/**
	 * Gets singleton instance of MenuItemLookup.
	 * 
	 * @return instance of MenuItemLookup
	 */
	public static MenuItemLookup getInstance() {
		if (instance == null) {
			instance = new MenuItemLookup();
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
	public MenuItem lookFor(Menu menu, Matcher<String>... matchers) {
		List<MenuItem> items = MenuHandler.getInstance().getItems(menu);
		MenuItem lastMenuItem = getMatchingMenuPath(items, matchers);
		if (lastMenuItem == null) {
			StringJoiner stringOfMatchers = new StringJoiner(", ");
			for (Matcher<String> matcher : matchers) {
				stringOfMatchers.add(matcher.toString());
			}
			StringJoiner stringOfFoundMenuItems = new StringJoiner("\n", "\t", "");
			for (String foundMenuItem : foundMenuItems) {
				stringOfFoundMenuItems.add(foundMenuItem);
			}
			throw new CoreLayerException("No menu item matching " + stringOfMatchers
					+ " was found.\nThe following items were found on path '" + foundPath + "':\n"
					+ stringOfFoundMenuItems);
		}
		return lastMenuItem;
	}
	
	/**
	 * Gets Menu item matching menu path defined by specified top menu items and matchers.
	 * 
	 * @param topItems top level menu items where to search for menu item
	 * @param matchers matchers to match menu item
	 * @return matching MenuItem
	 */
	private MenuItem getMatchingMenuPath(final List<MenuItem> topItems,
			final Matcher<String>... matchers) {
		foundPath = new StringBuilder("/");
		foundMenuItems.clear();
		MenuItem i = Display.syncExec(new ResultRunnable<MenuItem>() {

			@Override
			public MenuItem run() {
				Menu currentMenu = null;
				MenuItem currentItem = null;;
				List<MenuItem> menuItems = topItems;
				for (Matcher<String> m : matchers) {
					currentItem = null;
					for (MenuItem i : menuItems) {
						String normalized = i.getText().replace("&", "");
						log.debug("Found menu:'" + normalized + "'");
						foundMenuItems.add(normalized);
						if (m.matches(normalized)) {
							log.debug("Item match:" + normalized);
							foundPath.append(normalized);
							foundMenuItems.clear();
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
						MenuHandler.getInstance().sendShowUI(currentMenu);
						menuItems = Arrays.asList(currentMenu.getItems());
					} 
				}
				return currentItem;
			}
		});
		return i;
	}

}
