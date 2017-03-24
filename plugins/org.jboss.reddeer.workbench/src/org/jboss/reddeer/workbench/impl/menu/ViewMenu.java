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

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.lookup.MenuLookup;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatchers;
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
		super(MenuLookup.getInstance().lookFor(ViewMenuLookup.getInstance().getViewMenuItems(), matchers));
	}

}
