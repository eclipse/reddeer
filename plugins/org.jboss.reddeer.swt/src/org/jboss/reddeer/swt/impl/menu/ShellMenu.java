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

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatchers;

/**
 * Shell menu implementation
 * @author Jiri Peterka
 *
 */
public class ShellMenu extends AbstractMenu implements Menu {
	
	private static final Logger log = Logger.getLogger(ShellMenu.class);
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 *
	 * @param path the path
	 */
	public ShellMenu(final String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	
	/**
	 * Instantiates a new shell menu.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ShellMenu(final Matcher<String>... matchers) {
		this.matchers = matchers;
		menuItem = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#select()
	 */
	@Override
	public void select() {
		log.info("Select shell menu with text " + getText());
		mh.select(menuItem);
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
	 * @see org.jboss.reddeer.swt.api.Menu#getSWTWidget()
	 */
	public MenuItem getSWTWidget(){
		return menuItem;
	}
}
