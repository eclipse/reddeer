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
package org.eclipse.reddeer.jface.test.condition;

import static org.junit.Assert.*;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.test.wizard.TestingWizard;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WindowIsAvailableTest {
	
	
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
	
	@After
	public void closeWindow() {
		if(wizardDialog != null && wizardDialog.isOpen()) {
			wizardDialog.cancel();
		}
	}
	
	@Test
	public void testWindowIsAvailableCondition() {
		new WaitUntil(new WindowIsAvailable(wizardDialog));
		new FinishButton(wizardDialog).click();
		new WaitWhile(new WindowIsAvailable(wizardDialog));
	}
	
	@Test
	public void testWindowIsAvailableConditionText() {
		new WaitUntil(new WindowIsAvailable("Testing Wizard"));
		new FinishButton(wizardDialog).click();
		new WaitWhile(new WindowIsAvailable("Testing Wizard"));
	}
	
	@Test
	public void testWindowIsAvailableConditionRegex() {
		new WaitUntil(new WindowIsAvailable(new WithTextMatcher(new RegexMatcher("Testing.*"))));
		new FinishButton(wizardDialog).click();
		new WaitWhile(new WindowIsAvailable(new WithTextMatcher(new RegexMatcher("Testing.*"))));
	}
	
	@Test
	public void testWindowIsAvailableConditionRegexAndClass() {
		new WaitUntil(new WindowIsAvailable(org.eclipse.jface.wizard.WizardDialog.class, new WithTextMatcher(new RegexMatcher("Testing.*"))));
		new FinishButton(wizardDialog).click();
		new WaitWhile(new WindowIsAvailable(org.eclipse.jface.wizard.WizardDialog.class, new WithTextMatcher(new RegexMatcher("Testing.*"))));
	}
	
	@Test
	public void testWindowIsAvailableConditionWrongClass() {
		assertFalse(new WindowIsAvailable(PreferenceDialog.class, new WithTextMatcher(new RegexMatcher("Testing.*"))).test());
	}
	
	@Test
	public void testWindowIsAvailableConditionOKClass() {
		assertTrue(new WindowIsAvailable(Window.class, new WithTextMatcher(new RegexMatcher("Testing.*"))).test());
	}

}