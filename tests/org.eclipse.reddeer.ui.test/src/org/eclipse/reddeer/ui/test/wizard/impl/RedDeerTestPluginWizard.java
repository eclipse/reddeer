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

package org.eclipse.reddeer.ui.test.wizard.impl;

import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;

/**
 * This class represents a new RedDeer Test Plug-in wizard
 * @author jrichter
 *
 */
public class RedDeerTestPluginWizard extends NewMenuWizard {

	public static final String CATEGORY="RedDeer";
	public static final String NAME="RedDeer Test Plug-in";
	
	/**
	 * Default constructor
	 */
	public RedDeerTestPluginWizard() {
		super("New RedDeer Test Plugin",CATEGORY, NAME);
	}
}
