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
package org.eclipse.reddeer.workbench.impl.menu;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.lookup.MenuItemLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatchers;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.impl.menu.AbstractMenuItem;
import org.eclipse.reddeer.workbench.lookup.WorkbenchPartMenuLookup;

/**
 * Implementation for menu of workbench part.
 * 
 * @author Rastislav Wagner, rhopp
 *
 */

public class WorkbenchPartMenuItem extends AbstractMenuItem implements MenuItem {

	/**
	 * Instantiates a new workbench part menu item.
	 *
	 * @param path the path
	 */
	public WorkbenchPartMenuItem(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}

	/**
	 * Instantiates a new workbench part menu item.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public WorkbenchPartMenuItem(Matcher<String>... matchers) {
		super(MenuItemLookup.getInstance().lookFor(WorkbenchPartMenuLookup.getInstance().getViewMenu(), matchers));
	}

}
