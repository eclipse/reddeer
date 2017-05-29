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
package org.eclipse.reddeer.jface.test.dialogs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
