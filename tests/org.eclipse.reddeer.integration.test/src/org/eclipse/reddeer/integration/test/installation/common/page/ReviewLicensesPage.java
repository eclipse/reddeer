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

import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.swt.impl.button.RadioButton;

public class ReviewLicensesPage extends ValidatedWizardPage {

	public static final String PAGE_TITLE = "Review Licenses";
	
	public ReviewLicensesPage(TitleAreaDialog installDialog) {
		super(installDialog, PAGE_TITLE);
	}
	
	public boolean isLicenseAccepted() {
		return new RadioButton(referencedComposite, 0).isSelected();
	}
	
	public void acceptLicense(){
		new RadioButton(referencedComposite, 0).click();
	}
}
