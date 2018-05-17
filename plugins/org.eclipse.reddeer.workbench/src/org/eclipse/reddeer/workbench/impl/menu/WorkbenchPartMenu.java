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
package org.eclipse.reddeer.workbench.impl.menu;

import org.eclipse.reddeer.swt.impl.menu.AbstractMenu;
import org.eclipse.reddeer.workbench.lookup.WorkbenchPartMenuLookup;

/**
 * Represents View menu
 * @author rawagner
 *
 */
public class WorkbenchPartMenu extends AbstractMenu{
	
	/**
	 * Default constructor. Takes menu from currently active workbench part
	 */
	public WorkbenchPartMenu() {
		super(WorkbenchPartMenuLookup.getInstance().getViewMenu());
	}

}
