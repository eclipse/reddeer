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

import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.core.lookup.MenuLookup;
import org.eclipse.reddeer.swt.api.Shell;

/**
 * Shell Menu
 * @author rawagner
 *
 */
public class ShellMenu extends AbstractMenu{
	
	public ShellMenu() {
		super(MenuLookup.getInstance().getMenuFromActiveShell());
	}
	
	public ShellMenu(Shell shell) {
		super(ShellHandler.getInstance().getMenuBar(shell.getSWTWidget()));
	}

}
