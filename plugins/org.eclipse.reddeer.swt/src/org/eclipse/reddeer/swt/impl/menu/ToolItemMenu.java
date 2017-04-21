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
import org.eclipse.reddeer.swt.api.ToolItem;
import org.eclipse.reddeer.core.handler.ToolItemHandler;
import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatchers;

/**
 * This class represents drop down menu for ToolItems.
 * 
 * @author rhopp
 *
 */
public class ToolItemMenu extends AbstractMenu {

	protected ToolItemHandler tih = ToolItemHandler.getInstance();

	/**
	 * Constructor for desired menu for given ToolItem.
	 * 
	 * @param item ToolItem with SWT.DROP_DOWN style.
	 * @param path Path to desired menu.
	 */
	public ToolItemMenu(ToolItem item, String... path) {
		this(item, new WithMnemonicTextMatchers(path).getMatchers());
	}

	/**
	 * Constructor for desired menu for given ToolItem.
	 * 
	 * @param item ToolItem with SWT.DROP_DOWN style.
	 * @param path Path to desired menu.
	 */
	@SuppressWarnings("unchecked")
	public ToolItemMenu(ToolItem item, Matcher<String>... path) {
		super(MenuLookup.getInstance().lookFor(MenuLookup.getInstance().getToolItemMenuItems(item.getSWTWidget()),
				path));
	}
	
}

