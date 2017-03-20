/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.workbench.api.TopMenuOpenable;
import org.jboss.reddeer.workbench.topmenu.TopMenuWizardDialog;

/**
 * Represents Eclipse ImportExportWizard
 * @author rawagner
 *
 */
public class ImportExportWizard extends TopMenuWizardDialog implements TopMenuOpenable {
	
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
