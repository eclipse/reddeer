/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.page;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.integration.test.installation.common.condition.DialogTitleIsFound;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.hamcrest.Matcher;

public class ValidatedWizardPage extends WizardPage {
	
	/**
	 * Construct wizard page and check if dialog with given page title is available.
	 * 
	 * @param titleAreaDialog TitleAreaDialog which should contains this page
	 * @param pageTitle Page title
	 */
	public ValidatedWizardPage(TitleAreaDialog titleAreaDialog, String pageTitle) {
		super(titleAreaDialog.getShell());
		new WaitUntil(new DialogTitleIsFound(titleAreaDialog, pageTitle));
	}

	/**
	 * Construct wizard page and check if dialog with given page title is available.
	 * 
	 * @param titleAreaDialog TitleAreaDialog which should contains this page
	 * @param matcher matcher which will be used do match page title 
	 */
	public ValidatedWizardPage(TitleAreaDialog titleAreaDialog, Matcher<?> matcher) {
		super(titleAreaDialog.getShell());
		new WaitUntil(new DialogTitleIsFound(titleAreaDialog, matcher));
	}
}
