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

public class InstallDetailsPage extends ValidatedWizardPage {

	public static final String PAGE_TITLE = "Install Details";
	
	public InstallDetailsPage(TitleAreaDialog titleAreaDialog) {
		super(titleAreaDialog, PAGE_TITLE);
	}
}
