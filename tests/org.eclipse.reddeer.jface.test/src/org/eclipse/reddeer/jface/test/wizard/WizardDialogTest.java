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
package org.eclipse.reddeer.jface.test.wizard;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.jface.test.dialogs.impl.TestingTitleAreaDialog;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.CLabel;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.clabel.DefaultCLabel;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WizardDialogTest {

	private org.eclipse.swt.widgets.Shell shell;

	private TestingWizard wizard;
	
	private WizardDialog wizardDialog;
	
	@Before
	public void setUp(){
		shell = new WorkbenchShell().getSWTWidget();
		org.eclipse.reddeer.common.util.Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				wizard = new TestingWizard();
				
				org.eclipse.jface.wizard.WizardDialog swtWizardDialog = new org.eclipse.jface.wizard.WizardDialog(shell, wizard);
				swtWizardDialog.create();
				swtWizardDialog.open();
			}
		});
		wizardDialog = new WizardDialog(TestingWizard.TITLE);
	}

	@Test
	public void next() {
		assertTrue("Next button should be enabled", wizardDialog.isNextEnabled());
		wizardDialog.next();

		CLabel label = new DefaultCLabel();
		assertThat(label.getText(), is("B"));
	}
	@Test
	public void next_notEnabled() {
		wizardDialog.next();
		assertFalse("Next button should be disabled", wizardDialog.isNextEnabled());
	}

	@Test
	public void back() {
		wizardDialog.next();
		assertTrue("Back button should be enabled", wizardDialog.isBackEnabled());
		wizardDialog.back();

		CLabel label = new DefaultCLabel();
		assertThat(label.getText(), is("A"));
	}
	@Test
	public void back_notEnabled() {
		assertFalse("Back button should be disabled", wizardDialog.isBackEnabled());
	}

	@Test
	public void finish() {
		assertTrue("Finish button should be enabled", wizardDialog.isFinishEnabled());
		wizardDialog.finish();
		
		Shell shell = new DefaultShell();
		assertTrue(shell.getText().equals(new WorkbenchShell().getText()));
	}

	@Test
	public void finish_notEnabled() {
		wizard.setFinishEnabled(false);
		assertFalse("Finish button should be disabled", wizardDialog.isFinishEnabled());
	}

	@Test
	public void cancel() {
		wizardDialog.cancel();
		
		Shell shell = new DefaultShell();
		assertTrue(shell.getText().equals(new WorkbenchShell().getText()));
	}
	
	@Test
	public void getTitle() {
		final String currentDialogTitle = wizardDialog.getShell().getText();
		assertTrue("Expected current dialog title is '" + TestingWizard.TITLE + "'" +
			"\nbut current dialog title is '" + currentDialogTitle + "'",
			TestingWizard.TITLE.equals(currentDialogTitle));
	}
	
	@Test
	public void getPageTitle() {
		final String currentPageTitle = wizardDialog.getTitle();
		assertTrue("Expected current page title is '" + TestingWizard.PAGE_TITLE + "'" +
			"\nbut current page title is '" + currentPageTitle + "'",
			TestingWizard.PAGE_TITLE.equals(currentPageTitle));
	}
	
	@Test
	public void getPageDescription() {
		final String currentPageDescription = wizardDialog.getMessage();
		assertTrue("Expected current page description is '" + TestingWizard.PAGE_DESCRIPTION + "'" +
			"\nbut current page description is '" + currentPageDescription + "'",
			TestingWizard.PAGE_DESCRIPTION.equals(currentPageDescription));
	}
	
	//should fail because opened wizard is TitleAreaDialog not WizardDialog
	@Test(expected=CoreLayerException.class)
	public void testTitleAreaDialogAsWizardDialog(){
		Display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				TestingTitleAreaDialog tt = new TestingTitleAreaDialog(null);
				tt.create();
				tt.open();
			}
		});
		new WizardImpl();
		
	}

	@After
	public void tearDown(){
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
	}
	
	private class WizardImpl extends WizardDialog {

		public WizardImpl() {
			super(TestingTitleAreaDialog.TEXT);
		}
	}

}
