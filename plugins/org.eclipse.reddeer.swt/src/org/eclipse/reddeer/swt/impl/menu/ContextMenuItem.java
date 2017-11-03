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

import org.eclipse.reddeer.core.lookup.MenuItemLookup;
import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatchers;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Item;
import org.hamcrest.Matcher;

/**
 * Context MenuItem implementation for all context menu items related to some Control.
 * Control must have focus to provide context menu
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * 
 */
public class ContextMenuItem extends AbstractMenuItem {

	/**
	 * Context menu given by String path
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 *
	 * @param path the path
	 */
	public ContextMenuItem(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());		
	}
	
	/**
	 * Context menu given by String path
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 * 
	 * @param referencedComposite reference composite to get menu of
	 * @param path the path
	 */
	public ContextMenuItem(ReferencedComposite referencedComposite, String... path) {
		this(referencedComposite, new WithMnemonicTextMatchers(path).getMatchers());		
	}

	public ContextMenuItem(Item<?> item, String... path) {
		super(MenuItemLookup.getInstance().lookFor(
				MenuLookup.getInstance().getItemMenu(item.getSWTWidget(), item.getParentControl().getSWTWidget()),
				new WithMnemonicTextMatchers(path).getMatchers()));
	}
	
	/**
	 * Context menu given by matchers.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ContextMenuItem(Matcher<String>... matchers) {
		super(MenuItemLookup.getInstance().lookFor(
				MenuLookup.getInstance().getMenuFromFocusControl(),matchers));
	}
	
	/**
	 * Context menu given by matchers.
	 *
	 * @param referencedComposite reference composite to get menu of
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ContextMenuItem(ReferencedComposite referencedComposite, Matcher<String>... matchers) {
		super(MenuItemLookup.getInstance().lookFor(
				MenuLookup.getInstance().getControlMenu(referencedComposite.getControl()),matchers));
	}

	@SuppressWarnings("unchecked")
	public ContextMenuItem(Item<?> item, Matcher<String>... matchers) {
		super(MenuItemLookup.getInstance().lookFor(
				MenuLookup.getInstance().getItemMenu(item.getSWTWidget(), item.getParentControl().getSWTWidget()),matchers));
	}
}
