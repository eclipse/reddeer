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

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuWizardDialog;

/**
 * Abstract class for wizard dialogs with selection page (such as NewWizard, ImportExportWizard)
 * @author rawagner
 *
 */
public abstract class AbstractSelectionWizardDialog extends WorkbenchMenuWizardDialog {
	
	protected Matcher<String> matcher;
	protected String[] wizardPath;
	
	/**
	 * Constructs new AbstractSelectionWizardDialog
	 * @param shellText wizard text when next is clicked
	 * @param wizardCategory wizard category in selection page
	 * @param wizardName wizard name in selection page
	 */
	public AbstractSelectionWizardDialog(String shellText, String wizardCategory, String wizardName) {
		this(shellText, new String[] {wizardCategory, wizardName});
	}
	
	/**
	 * Constructs new AbstractSelectionWizardDialog
	 * @param shellText wizard text when next is clicked
	 * @param wizardPath wizard path in selection page
	 */
	public AbstractSelectionWizardDialog(String shellText, String[] wizardPath) {
		super();
		this.matcher = new WithTextMatcher(shellText);
		this.wizardPath = wizardPath;
		isOpen();
	}

}
