/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.jface.test.dialogs.impl.TestingNewWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class NewWizardDialogTest {

	private NewWizardDialogImpl newWizardDialog = new NewWizardDialogImpl();

	@Test
	public void open() {
		assertNull(newWizardDialog.getShell());
		newWizardDialog.open();
		assertNotNull(newWizardDialog.getShell());
		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingNewWizard.NAME));
	}
	
	@Test
	public void useExistingWizard() {
		newWizardDialog.open();
		NewWizardDialogImpl secondNewWizardDialog = new NewWizardDialogImpl();
		assertNotNull(secondNewWizardDialog.getShell());
		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingNewWizard.NAME));;
	}
	
	@Test
	public void activate() {
		newWizardDialog.open();
		NewWizardDialogImpl secondNewWizardDialog = new NewWizardDialogImpl();
		newWizardDialog.cancel();
		newWizardDialog.open();
		assertTrue(secondNewWizardDialog.getShell().isDisposed());
		secondNewWizardDialog.activate();
		assertNotNull(secondNewWizardDialog.getShell());
		assertFalse(secondNewWizardDialog.getShell().isDisposed());
		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingNewWizard.NAME));;
	}

	@After
	public void tearDown(){
		if(newWizardDialog.isOpen()){
			newWizardDialog.cancel();
		}
	}
	
	private class NewWizardDialogImpl extends NewMenuWizard {

		public NewWizardDialogImpl() {
			super(TestingNewWizard.NAME, TestingNewWizard.CATEGORY, TestingNewWizard.NAME);
		}
	}
}
