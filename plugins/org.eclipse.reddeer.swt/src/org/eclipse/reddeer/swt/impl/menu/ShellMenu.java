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
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatchers;

/**
 * Shell menu implementation
 * @author Jiri Peterka
 *
 */
public class ShellMenu extends AbstractMenu implements Menu {
	
	private static final Logger log = Logger.getLogger(ShellMenu.class);
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu.
	 * Menu is found within currently focused shell.
	 *
	 * @param path the path
	 */
	public ShellMenu(final String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	
	/**
	 * Instantiates a new shell menu. Menu is found within currently focused shell.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ShellMenu(final Matcher<String>... matchers) {
		super(MenuLookup.getInstance().lookFor(MenuLookup.getInstance().getActiveShellTopMenuItems(), matchers));
	}
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu.
	 *
	 * @param shell to look for menu
	 * @param path the path
	 */
	public ShellMenu(final Shell shell, final String... path) {
		this(shell, new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	/**
	 * Instantiates a new shell menu.
	 *
	 * @param shell to look for menu
	 * @param matchers to match shell item path
	 */
	@SuppressWarnings("unchecked")
	public ShellMenu(final Shell shell, final Matcher<String>... matchers) {
		super(MenuLookup.getInstance().lookFor(MenuLookup.getInstance().getMenuBarItems(shell.getSWTWidget()), matchers));
	}
}
