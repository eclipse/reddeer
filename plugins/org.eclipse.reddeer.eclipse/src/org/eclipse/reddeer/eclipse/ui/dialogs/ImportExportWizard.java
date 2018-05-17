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
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuWizardDialog;

/**
 * Represents Eclipse ImportExportWizard
 * @author rawagner
 *
 */
public class ImportExportWizard extends WorkbenchMenuWizardDialog {
	
	private static WithTextMatcher importTitle = new WithTextMatcher("Import");
	private static WithTextMatcher exportTitle = new WithTextMatcher("Export");
	
	private static String importMenu = "Import...";
	private static String exportMenu = "Export...";
	
	/**
	 * Constructs ImportExportWizard. This wizard represends both Import and Export Wizard. 
	 * @param importPage true if ImportExportWizard should represent Import wizard, false if Export Wizard
	 */
	public ImportExportWizard(boolean importPage) {
		super(importPage ? importTitle:exportTitle,"File", importPage? importMenu:exportMenu);
	}

}
