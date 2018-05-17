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
package org.eclipse.reddeer.eclipse.ui.wizards.datatransfer;

import org.eclipse.reddeer.eclipse.selectionwizard.ImportMenuWizard;

/**
 * Wizard for importing external projects into the workspace. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ExternalProjectImportWizardDialog extends ImportMenuWizard {

	/**
	 * Construct the wizard with "General" &gt; "Existing Projects into Workspace".
	 */
	public ExternalProjectImportWizardDialog() {
		super("Import","General", "Existing Projects into Workspace");
	}
}
