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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * Wizard dialog for creating new Java project.
 */
public class JavaProjectWizard extends NewMenuWizard {

	/**
	 * Constructs the wizard with Java &gt; Java Project.
	 */
	public JavaProjectWizard() {
		super("New Java Project", "Java", "Java Project");
	}
	
}
