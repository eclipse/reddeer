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
