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
package org.eclipse.reddeer.eclipse.wst.jsdt.ui.wizards;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * Represents the wizard for creating new JavaScript project. It provides access to the first wizard page {@link JavaProjectWizardFirstPage}. 
 * 
 * @author Pavol Srna
 *
 */
public class JavaProjectWizard extends NewMenuWizard {

	public static final String TITLE = "New JavaScript Project";
	
	/**
	 * Instantiates a new java project wizard dialog.
	 */
	public JavaProjectWizard() {
		super(TITLE, "JavaScript", "JavaScript Project");
	}
	
}
