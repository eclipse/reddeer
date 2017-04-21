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
	public MavenImportWizardPage() {

	}

	/**
	 * Activates the wizard.
	 */
	public void activate() {
		new DefaultShell(TITLE);
	}

	/**
	 * Sets a root directory.
	 * 
	 * @param path
	 *            Path
	 */
	public void setRootDirectory(String path) {
		activate();
		new LabeledCombo("Root Directory:").setText(path);
	}

	/**
	 * Clicks on 'Refresh' button.
	 */
	public void refresh() {
		activate();
		new PushButton("Refresh").click();
	}

	/**
	 * Waits until a project is loaded (usually after refresh).
	 * 
	 * @param timeout
	 *            Timeout
	 */
	public void waitUntilProjectIsLoaded(TimePeriod timeout) {
		activate();
		new WaitUntil(new ProjectsIsLoaded(), timeout);
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
			return new PushButton("Finish").isEnabled() || new PushButton("Next >").isEnabled();
		}

		@Override
		public String description() {
			return "The project is still not loaded.";
		}

	}
}
