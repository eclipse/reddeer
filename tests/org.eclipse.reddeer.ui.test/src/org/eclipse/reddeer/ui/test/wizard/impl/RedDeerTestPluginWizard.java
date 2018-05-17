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

package org.eclipse.reddeer.ui.test.wizard.impl;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

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
