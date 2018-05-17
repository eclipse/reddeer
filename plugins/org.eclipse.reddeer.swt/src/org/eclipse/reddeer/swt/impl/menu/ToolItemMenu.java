/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.menu;

import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.swt.api.ToolItem;

/**
 * Represents menu under ToolItem
 * @author rawagner
 *
 */
public class ToolItemMenu extends AbstractMenu{
	
	/**
	 * Constructs this menu from specified tool item
	 * @param item to get menu of
	 */
	public ToolItemMenu(ToolItem item) {
		super(MenuLookup.getInstance().getToolItemMenu(item.getSWTWidget()));
	}

}
