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
package org.eclipse.reddeer.swt.impl.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.widgets.AbstractItem;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.handler.MenuHandler;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.core.lookup.MenuLookup;

/**
 * Abstract class for all Menu implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenu extends AbstractItem<MenuItem> implements Menu {

	protected AbstractMenu(MenuItem swtWidget) {
		super(swtWidget);
	}
	
	protected MenuLookup ml = MenuLookup.getInstance();
	protected MenuHandler mh = MenuHandler.getInstance();
	private static final Logger log = Logger.getLogger(AbstractMenu.class);


	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Menu#select()
	 */
	@Override
	public void select(){
		log.info("Select menu item with text " + getText());
		mh.select(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Menu#isSelected()
	 */
	@Override
	public boolean isSelected(){
		return mh.isSelected(swtWidget);
	}
	
	@Override
	public String getText() {
		return mh.getMenuItemText(swtWidget).replace("&", "");
	}
	
	@Override
	public boolean isEnabled() {
		return mh.isEnabled(swtWidget);
	}
	
	@Override
	public boolean isDisposed() {
		return WidgetHandler.getInstance().isDisposed(swtWidget);
	}
	
	@Override
	public List<String> getChildItems() {
		List<String> itemsText = new ArrayList<>();
		MenuItem[] items = mh.getMenuItems(swtWidget);
		if(items != null){
			for(MenuItem i: items){
				itemsText.add(mh.getMenuItemText(i).replace("&", ""));
			}
		}
		return itemsText;
	}
	
	@Override
	public List<Menu> getMenuItems() {
		MenuItem[] items = ml.getItemsFromMenu(mh.getMenuFromMenuItem(swtWidget));
		List<Menu> menus = new ArrayList<>(items.length);
		
		for (MenuItem item : items){
			if(mh.getMenuItemText(item).isEmpty())		
				continue;

			menus.add(new DefaultMenu(item));
		}
		return menus;
	}
	
	@Override
	public List<Menu> getAvailableChildItems() {
		List<Menu> availableItems = new ArrayList<>();

		for (Menu item : getMenuItems()) {
			if(item.isEnabled())
				availableItems.add(item);
		}
		return availableItems;
	}
}
