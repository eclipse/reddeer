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
package org.jboss.reddeer.swt.impl.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.MenuHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.MenuLookup;

/**
 * Abstract class for all Menu implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenu implements Menu {

	protected String[] path;
	protected Matcher<String>[] matchers;
	protected MenuLookup ml = MenuLookup.getInstance();
	protected MenuHandler mh = MenuHandler.getInstance();
	protected MenuItem menuItem = null;
	private static final Logger log = Logger.getLogger(AbstractMenu.class);


	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#select()
	 */
	@Override
	public void select(){
		log.info("Select menu item with text " + getText());
		mh.select(menuItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#isSelected()
	 */
	@Override
	public boolean isSelected(){
		return mh.isSelected(menuItem);
	}
	
	@Override
	public String getText() {
		return mh.getMenuItemText(menuItem).replace("&", "");
	}
	
	@Override
	public boolean isEnabled() {
		return mh.isEnabled(menuItem);
	}
	
	@Override
	public boolean isDisposed() {
		return WidgetHandler.getInstance().isDisposed(menuItem);
	}
	
	@Override
	public List<String> getChildItems() {
		List<String> itemsText = new ArrayList<>();
		MenuItem[] items = mh.getMenuItems(menuItem);
		if(items != null){
			for(MenuItem i: items){
				itemsText.add(mh.getMenuItemText(i).replace("&", ""));
			}
		}
		return itemsText;
	}
	
	public MenuItem getSWTWidget(){
		return menuItem;
	}
	
}
