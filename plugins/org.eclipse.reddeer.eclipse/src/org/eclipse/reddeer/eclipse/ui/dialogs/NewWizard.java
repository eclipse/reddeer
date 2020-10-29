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

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuWizardDialog;

/**
 * Represents Eclipse NewWizard that can be found in File, New, Other... menu
 * 
 * @author vpakan
 * @since 0.6
 *
 */
public class NewWizard extends WorkbenchMenuWizardDialog{
	
	public static final String DIALOG_TITLE = "Select a wizard";
	
	public NewWizard() {
		// super(DIALOG_TITLE,"File","New","Other...");
		super(new WithTextMatcher(new RegexMatcher("New|" + DIALOG_TITLE)),"File","New","Other...");
	}

	
	
	
}
