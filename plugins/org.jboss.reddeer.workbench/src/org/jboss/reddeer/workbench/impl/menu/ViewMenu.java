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
package org.jboss.reddeer.workbench.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatchers;
import org.jboss.reddeer.jface.handler.ActionContributionItemHandler;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.AbstractMenu;
import org.jboss.reddeer.workbench.lookup.ViewMenuLookup;

/**
 * Implementation for menu of view.
 * 
 * @author Rastislav Wagner, rhopp
 *
 */

public class ViewMenu extends AbstractMenu implements Menu {

	private static final Logger log = Logger.getLogger(ViewMenu.class);

	private ActionContributionItem item;
	protected ActionContributionItemHandler acHandler = ActionContributionItemHandler.getInstance();
	protected ViewMenuLookup vml = ViewMenuLookup.getInstance();

	/**
	 * Instantiates a new view menu.
	 *
	 * @param path the path
	 */
	public ViewMenu(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}

	/**
	 * Instantiates a new view menu.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ViewMenu(Matcher<String>... matchers) {
		item = vml.lookFor(vml.getViewMenus(), matchers);
		if (item == null) {
			// desired item is not ActionContribution item. Trying to find item as MenuItem.
			log.info("Desired ActionContributionItem not found, looking for MenuItem");
			menuItem = vml.lookForViewMenu(vml.getViewMenus(), matchers);
		}
		this.matchers = matchers;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#select()
	 */
	public void select() {
		log.info("Select view menu with text " + getText());
		if (item == null) {
			mh.select(menuItem);
		} else {
			acHandler.select(item);
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#isSelected()
	 */
	@Override
	public boolean isSelected() {

		return item.getAction().isChecked();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#getText()
	 */
	@Override
	public String getText() {
		if (item == null) {
			return mh.getMenuItemText(menuItem);
		} else {
			return item.getAction().getText().replace("&", "");
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#getSWTWidget()
	 */
	public MenuItem getSWTWidget() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		if (item == null) {
			return mh.isEnabled(menuItem);
		} else {
			return acHandler.isEnabled(item);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isDisposed()
	 */
	@Override
	public boolean isDisposed() {
		//actionItem doesnt have isDisposed method, so just return false
		return false;
	}

}
