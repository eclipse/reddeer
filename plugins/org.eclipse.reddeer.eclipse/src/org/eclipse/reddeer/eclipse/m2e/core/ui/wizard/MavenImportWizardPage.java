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
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

/**
 * Wizard page used in MavenImportWizard.
 * 
 * @author apodhrad
 *
 */
public class MavenImportWizardPage extends WizardPage {

	public static final String TITLE = "Import Maven Projects";

	/**
	 * Default constructor.
	 */
	public MavenImportWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Activates the wizard.
	 */
	public MavenImportWizardPage activate() {
		new DefaultShell(TITLE);
		return this;
	}

	/**
	 * Sets a root directory.
	 * 
	 * @param path
	 *            Path
	 */
	public MavenImportWizardPage setRootDirectory(String path) {
		activate();
		new LabeledCombo(this, "Root Directory:").setText(path);
		return this;
	}

	/**
	 * Clicks on 'Refresh' button.
	 */
	public MavenImportWizardPage refresh() {
		activate();
		new PushButton(this, "Refresh").click();
		return this;
	}

	/**
	 * Waits until a project is loaded (usually after refresh).
	 * 
	 * @param timeout
	 *            Timeout
	 */
	public MavenImportWizardPage waitUntilProjectIsLoaded(TimePeriod timeout) {
		activate();
		new WaitUntil(new ProjectsIsLoaded(), timeout);
		return this;
	}

	/**
	 * WaitCondition which determines whether a project was successfully reloaded (usually after refresh).
	 * 
	 * @author apodhrad
	 *
	 */
	private class ProjectsIsLoaded extends AbstractWaitCondition {

		@Override
		public boolean test() {
			return new PushButton(MavenImportWizardPage.this, "Finish").isEnabled() || new PushButton(MavenImportWizardPage.this, "Next >").isEnabled();
		}

		@Override
		public String description() {
			return "The project is still not loaded.";
		}

	}
}
