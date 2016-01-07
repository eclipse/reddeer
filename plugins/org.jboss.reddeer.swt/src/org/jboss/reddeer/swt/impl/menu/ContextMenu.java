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

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.core.handler.ActionContributionItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatchers;

/**
 * Context Menu implementation for all context menu related to some Control.
 * Control must have focus to provide context menu
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * 
 */
public class ContextMenu extends AbstractMenu implements Menu {
	private static final Logger log = Logger.getLogger(ContextMenu.class);
	private ActionContributionItem actionItem;
	private MenuItem menuItem;

	/**
	 * Context menu given by String path
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 *
	 * @param path the path
	 */
	public ContextMenu(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());		
	}
	
	/**
	 * Context menu given by matchers.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ContextMenu(Matcher<String>... matchers) {

		menuItem = ml.lookFor(ml.getTopMenuMenuItemsFromFocus(),matchers);
		if(menuItem == null){
			log.info("No menu item found, looking for contribution item");
			actionItem = ml.lookFor(ml.getMenuContributionItems(), matchers);
			if (actionItem == null){
				throw new SWTLayerException("Contribution item not found");
			}
		}
		this.matchers = matchers;
		
	}	
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#select()
	 */
	@Override
	public void select() {
		log.info("Select context menu item with text " + getText());
		if(menuItem != null){
			mh.select(menuItem);
		} else {
			mh.select(actionItem);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#isSelected()
	 */
	@Override
	public boolean isSelected() {
		if(menuItem != null){
			return mh.isSelected(menuItem);
		} else {
			return false;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#getText()
	 */
	@Override
	public String getText() {
		if(actionItem != null){
			return actionItem.getAction().getText().replace("&", "");
		} else {
			String ret = mh.getMenuItemText(menuItem).replace("&", "");
			return ret;
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#getSWTWidget()
	 */
	@Override
	public MenuItem getSWTWidget() {
		return menuItem;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		if(menuItem != null){
			return WidgetHandler.getInstance().isEnabled(menuItem);
		}
		return ActionContributionItemHandler.getInstance().isEnabled(actionItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isDisposed()
	 */
	@Override
	public boolean isDisposed() {
		if(menuItem != null){
			return WidgetHandler.getInstance().isDisposed(menuItem);
		}
		//actionItem doesnt have isDisposed method, so just return false
		return false;
	}
}
