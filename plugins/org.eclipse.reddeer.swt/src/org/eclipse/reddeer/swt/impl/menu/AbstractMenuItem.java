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
package org.eclipse.reddeer.swt.impl.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.reddeer.swt.api.Control;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.widgets.AbstractItem;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.handler.MenuHandler;
import org.eclipse.reddeer.core.handler.MenuItemHandler;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.core.lookup.MenuLookup;

/**
 * Abstract class for all MenuItem implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenuItem extends AbstractItem<MenuItem> implements org.eclipse.reddeer.swt.api.MenuItem {

	protected AbstractMenuItem(MenuItem swtWidget) {
		super(swtWidget);
	}
	
	protected MenuLookup menuLookup = MenuLookup.getInstance();
	protected MenuItemHandler menuItemHandler = MenuItemHandler.getInstance();
	private static final Logger log = Logger.getLogger(AbstractMenuItem.class);


	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Menu#select()
	 */
	@Override
	public void select(){
		log.info("Select menu item with text " + getText());
		menuItemHandler.select(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Menu#isSelected()
	 */
	@Override
	public boolean isSelected(){
		return menuItemHandler.isSelected(swtWidget);
	}
	
	@Override
	public String getText() {
		return menuItemHandler.getMenuItemText(swtWidget).replace("&", "");
	}
	
	@Override
	public boolean isEnabled() {
		return menuItemHandler.isEnabled(swtWidget);
	}
	
	@Override
	public boolean isDisposed() {
		return WidgetHandler.getInstance().isDisposed(swtWidget);
	}
	
	@Override
	public List<org.eclipse.reddeer.swt.api.MenuItem> getChildItems() {
		List<MenuItem> items = MenuHandler.getInstance().getItems(menuItemHandler.getMenuFromMenuItem(swtWidget));
		if(items == null) {
			return new ArrayList<>();
		}
		return items.stream().map(DefaultMenuItem::new).collect(Collectors.toList());
	}
	
	@Override
	public List<org.eclipse.reddeer.swt.api.MenuItem> getAvailableChildItems() {
		List<org.eclipse.reddeer.swt.api.MenuItem> availableItems = new ArrayList<>();

		for (org.eclipse.reddeer.swt.api.MenuItem item : getChildItems()) {
			if(item.isEnabled())
				availableItems.add(item);
		}
		return availableItems;
	}
	
	@Override
	public Menu getParent() {
		return new DefaultMenu(menuItemHandler.getParent(swtWidget));
	}
	
	@Override
	public Control<?> getParentControl() {
		throw new SWTLayerException("Menu item does not have parent control");
	}
	
	@Override
	public Menu getMenu() {
		org.eclipse.swt.widgets.Menu menu = menuItemHandler.getMenu(swtWidget);
		return menu == null ? null: new DefaultMenu(menu);
	}
	
}
