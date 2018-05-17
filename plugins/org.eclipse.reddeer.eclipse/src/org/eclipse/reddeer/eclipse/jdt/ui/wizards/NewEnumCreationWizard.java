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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * Represents new enum wizard
 * 
 * @author rawagner
 *
 */
public class NewEnumCreationWizard extends NewMenuWizard {
	
	/**
	 * Construct the wizard with "Java" &gt; "Enum".
	 */
	public NewEnumCreationWizard() {
		super("New Enum Type","Java", "Enum");
	}

	/**
	 * Gets the first page.
	 *
	 * @return the first page
	 */
	public NewEnumWizardPage getFirstPage() {
		return new NewEnumWizardPage(this);
	}

}
