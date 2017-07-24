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

import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.swt.api.Control;
import org.eclipse.reddeer.swt.api.Item;

/**
 * Context Menu
 * @author rawagner
 *
 */
public class ContextMenu extends AbstractMenu{
	
	public ContextMenu() {
		super(MenuLookup.getInstance().getMenuFromFocusControl());
	}
	
	public ContextMenu(Control<?> control) {
		super(MenuLookup.getInstance().getControlMenu(control.getSWTWidget()));
	}
	
	public ContextMenu(Item<?> item) {
		super(MenuLookup.getInstance().getItemMenu(item.getSWTWidget(), item.getParentControl().getSWTWidget()));
	}


}
