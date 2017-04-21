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
package org.eclipse.reddeer.eclipse.wst.jsdt.ui.wizards;

import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;

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
