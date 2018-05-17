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
package org.eclipse.reddeer.eclipse.ui.wizards.newresource;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class BasicNewProjectResourceWizard extends NewMenuWizard {
	
	/**
	 * Constructs the wizard with "General" - "Project".
	 */
	public BasicNewProjectResourceWizard() {
		super("New Project","General", "Project");
	}
}
