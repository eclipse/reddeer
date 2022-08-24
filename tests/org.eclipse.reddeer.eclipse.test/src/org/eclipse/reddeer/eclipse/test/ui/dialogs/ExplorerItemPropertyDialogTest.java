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
package org.eclipse.reddeer.eclipse.test.ui.dialogs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyDialog;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ExplorerItemPropertyDialogTest {

	private static final String PROJECT_NAME = "PropertyTestProject";

	private static DefaultProject project;

	private PropertyDialog dialog;

	private PropertyPage page;

	@BeforeClass
	public static void createProject(){
		JavaProjectWizard wizardDialog = new JavaProjectWizard();
		wizardDialog.open();
		NewJavaProjectWizardPageOne page1 = new NewJavaProjectWizardPageOne(wizardDialog);
		page1.setProjectName(PROJECT_NAME);
		wizardDialog.finish();		
		
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		project = explorer.getProject(PROJECT_NAME);
	}

	@Before
	public void setup() {
		dialog = project.openProperties();
		page = new TestPropertyPageRedDeer(dialog);
	}

	@After 
	public void cleanup(){
		if(dialog!=null && dialog.isOpen()){
			dialog.cancel();
		}
	}

	@AfterClass
	public static void deleteProject(){
		DeleteUtils.forceProjectDeletion(project,true);		
	}
	
	@Test
	public void openAndSelect(){
		dialog.open();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getShell().getText()));

		dialog.select(page);
		assertThat(dialog.getPageName(), is(TestPropertyPage.PAGE_TITLE));
	}

	@Test
	public void openAndSelectByPath(){
		dialog.open();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getShell().getText()));

		dialog.select(TestPropertyPage.PAGE_TITLE);
		assertThat(dialog.getPageName(), is(TestPropertyPage.PAGE_TITLE));
	}

	@Test
	public void ok(){
		dialog.open();
		dialog.select(page);
		String dialogText = dialog.getShell().getText();
		dialog.ok();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(dialogText)));
		assertTrue(TestPropertyPage.performOkCalled);
	}

	@Test
	public void cancel(){
		dialog.open();
		dialog.select(page);
		String dialogText = dialog.getShell().getText();
		dialog.cancel();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(dialogText)));
		assertTrue(TestPropertyPage.performCancelCalled);
	}

	@Test
	public void apply(){
		dialog.open();
		dialog.select(page);
		page.apply();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getShell().getText()));
		assertThat(page.getName(), is(TestPropertyPage.PAGE_TITLE));
		assertTrue(TestPropertyPage.performApplyCalled);
	}

	@Test
	public void restoreDefaults(){
		dialog.open();
		dialog.select(page);
		page.restoreDefaults();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getShell().getText()));
		assertThat(page.getName(), is(TestPropertyPage.PAGE_TITLE));
		assertTrue(TestPropertyPage.performDefaultsCalled);
	}

	private class TestPropertyPageRedDeer extends PropertyPage {
		public TestPropertyPageRedDeer(PropertyDialog dialog) {
			super(dialog,TestPropertyPage.PAGE_TITLE);
		}
	}
}
