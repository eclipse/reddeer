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
package org.eclipse.reddeer.eclipse.selectionwizard;

import org.eclipse.reddeer.eclipse.ui.dialogs.WorkbenchWizardSelectionPage;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuWizardDialog;
import org.hamcrest.Matcher;

public class SelectionWizardOpenable extends Openable{
	
	private String[] wizardPath;
	private WorkbenchMenuWizardDialog selectionWizard;

	public SelectionWizardOpenable(WorkbenchMenuWizardDialog selectionWizard, String[] wizardPath, Matcher<?> shellMatcher) {
		super(shellMatcher);
		this.wizardPath = wizardPath;
		this.selectionWizard = selectionWizard;
	}

	@Override
	public void run() {
		selectionWizard.open();
		WorkbenchWizardSelectionPage selectionPage = new WorkbenchWizardSelectionPage(selectionWizard){};
		selectionPage.selectProject(wizardPath);
		selectionWizard.next();
	}

}
