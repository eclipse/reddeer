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
