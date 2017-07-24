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

import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.core.lookup.MenuItemLookup;
import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatchers;

/**
 * Shell menu implementation
 * @author Jiri Peterka
 *
 */
public class ShellMenuItem extends AbstractMenuItem implements MenuItem {
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu.
	 * Menu is found within currently focused shell.
	 *
	 * @param path the path
	 */
	public ShellMenuItem(final String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	
	/**
	 * Instantiates a new shell menu. Menu is found within currently focused shell.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ShellMenuItem(final Matcher<String>... matchers) {
		super(MenuItemLookup.getInstance().lookFor(
				MenuLookup.getInstance().getMenuFromActiveShell(), matchers));
	}
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu.
	 *
	 * @param shell to look for menu
	 * @param path the path
	 */
	public ShellMenuItem(final Shell shell, final String... path) {
		this(shell, new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	/**
	 * Instantiates a new shell menu.
	 *
	 * @param shell to look for menu
	 * @param matchers to match shell item path
	 */
	@SuppressWarnings("unchecked")
	public ShellMenuItem(final Shell shell, final Matcher<String>... matchers) {
		super(MenuItemLookup.getInstance().lookFor(
				MenuLookup.getInstance().getShellMenu(shell.getSWTWidget()), matchers));
	}
}
