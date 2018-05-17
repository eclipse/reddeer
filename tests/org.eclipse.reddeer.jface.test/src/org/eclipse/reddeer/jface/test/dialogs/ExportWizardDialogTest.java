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
package org.eclipse.reddeer.jface.test.dialogs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.reddeer.eclipse.selectionwizard.ExportMenuWizard;
import org.eclipse.reddeer.jface.test.dialogs.impl.TestingExportWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ExportWizardDialogTest {

	private ExportWizardDialogImpl exportWizardDialog = new ExportWizardDialogImpl();

	@Test
	public void open() {
		exportWizardDialog.open();

		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingExportWizard.NAME));
	}

	@After
	public void tearDown(){
		exportWizardDialog.cancel();
	}
	
	private class ExportWizardDialogImpl extends ExportMenuWizard {

		public ExportWizardDialogImpl() {
			super(TestingExportWizard.NAME, TestingExportWizard.CATEGORY, TestingExportWizard.NAME);
		}
	}
}
