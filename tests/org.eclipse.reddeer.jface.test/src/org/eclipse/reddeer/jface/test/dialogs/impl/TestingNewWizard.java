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
package org.eclipse.reddeer.jface.test.dialogs.impl;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class TestingNewWizard extends Wizard implements INewWizard {

	public static final String CATEGORY = "Testing new category";
	
	public static final String NAME = "Testing new wizard";

	public TestingNewWizard() {
		setWindowTitle(NAME);
	}
	
	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
	}
	
	@Override
	public void addPages() {
		addPage(new TestingNewWizardPage());
	}

	@Override
	public boolean performFinish() {
		return false;
	}
	
	public static class TestingNewWizardPage extends WizardPage {

		public static final String PAGE_NAME = "Testing new wizard page";
		
		protected TestingNewWizardPage() {
			super(PAGE_NAME);
			setTitle(PAGE_NAME);
		}

		@Override
		public void createControl(Composite parent) {
			setControl(parent);
			setErrorMessage("Error msg");
			setMessage("Msg");
		}
	}
}
