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
package org.eclipse.reddeer.eclipse.test.jdt.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.jface.preference.PreferenceDialog;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchPreferenceDialogTest {
	
	private static final String DIALOG_TITLE = "Preferences";

	private static final String PAGE_NAME = TestingPreferencePage.TITLE;

	private WorkbenchPreferenceDialog dialog;
	
	private PreferencePage preferencePage;

	@Before
	public void setup(){
		dialog = new WorkbenchPreferenceDialog();
		preferencePage = new PreferencePageImpl(dialog);
	}

	@After
	public void cleanup(){
		ShellIsAvailable condition = new ShellIsAvailable(DIALOG_TITLE);
		if(condition.test()){
			new DefaultShell(condition.getResult()).close();
		}
		if(new ShellIsAvailable(DIALOG_TITLE).test()){
			
		}
	}

	@Test
	public void openAndSelect(){
		dialog.open();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		
		dialog.select(preferencePage);
		assertThat(dialog.getPageName(), is(PAGE_NAME));
	}
	
	@Test
	public void openAndSelectByPath(){
		dialog.open();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		
		dialog.select(TestingPreferencePage.TestTopCategory.TOP_CATEGORY,
				TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME);
		assertThat(dialog.getPageName(), is(PAGE_NAME));
	}
	
	@Test
	public void ok(){
		dialog.open();
		dialog.select(preferencePage);
		dialog.ok();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performOkCalled);
	}

	@Test
	public void cancel(){
		dialog.open();
		dialog.select(preferencePage);
		dialog.cancel();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performCancelCalled);
	}

	@Test
	public void apply(){
		dialog.open();
		dialog.select(preferencePage);
		preferencePage.apply();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performApplyCalled);
	}

	@Test
	public void restoreDefaults(){
		dialog.open();
		dialog.select(preferencePage);
		preferencePage.restoreDefaults();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performDefaultsCalled);
	}

	class PreferencePageImpl extends PreferencePage {

		public PreferencePageImpl(PreferenceDialog dialog) {
			super(dialog, new String[]{TestingPreferencePage.TestTopCategory.TOP_CATEGORY,
					TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME});
		}
	}
}
