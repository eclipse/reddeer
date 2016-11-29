/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
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
		org.jboss.reddeer.common.util.Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				wizard = new TestingWizard();
				
				org.eclipse.jface.wizard.WizardDialog swtWizardDialog = new org.eclipse.jface.wizard.WizardDialog(shell, wizard);
				swtWizardDialog.create();
				swtWizardDialog.open();
			}
		});
		new WaitUntil(new ShellWithTextIsAvailable(TestingWizard.TITLE));
		wizardDialog = new WizardDialog();
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
		final String currentDialogTitle = wizardDialog.getTitle();
		assertTrue("Expected current dialog title is '" + TestingWizard.TITLE + "'" +
			"\nbut current dialog title is '" + currentDialogTitle + "'",
			TestingWizard.TITLE.equals(currentDialogTitle));
	}
	
	@Test
	public void getPageTitle() {
		final String currentPageTitle = wizardDialog.getPageTitle();
		assertTrue("Expected current page title is '" + TestingWizard.PAGE_TITLE + "'" +
			"\nbut current page title is '" + currentPageTitle + "'",
			TestingWizard.PAGE_TITLE.equals(currentPageTitle));
	}
	
	@Test
	public void getPageDescription() {
		final String currentPageDescription = wizardDialog.getPageDescription();
		assertTrue("Expected current page description is '" + TestingWizard.PAGE_DESCRIPTION + "'" +
			"\nbut current page description is '" + currentPageDescription + "'",
			TestingWizard.PAGE_DESCRIPTION.equals(currentPageDescription));
	}

	@After
	public void tearDown(){
		Shell activeShell = new DefaultShell();
		if (new DefaultShell().getText().equals(TestingWizard.TITLE)){
			activeShell.close();
		}
	}

}
