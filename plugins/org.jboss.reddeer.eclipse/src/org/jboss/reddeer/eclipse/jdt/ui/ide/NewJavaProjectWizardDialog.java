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
package org.jboss.reddeer.eclipse.jdt.ui.ide;

import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Wizard dialog for creating new Java project.
 */
public class NewJavaProjectWizardDialog extends NewWizardDialog {

	/**
	 * Constructs the wizard with Java > Java Project.
	 */
	public NewJavaProjectWizardDialog() {
		super("Java", "Java Project");
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.wizard.WizardDialog#finish()
	 */
	@Override
	public void finish() {
		finish(false);
	}

	/**
	 * Closes dialog clicking on finish button.
	 * @param openAssociatedPerspective - true when associated perspective
	 * has to be open
	 */
	public void finish(boolean openAssociatedPerspective) {
		log.debug("Finish wizard dialog");
		new PushButton("Finish").click();

		if (isWaitForOpenAssociatedPerspective()) {
			final String openAssociatedPerspectiveShellText = "Open Associated Perspective?";
			try {
				new WaitUntil(new ShellWithTextIsAvailable(
						openAssociatedPerspectiveShellText),
						TimePeriod.getCustom(20), false);
				// Try to find open perspective test
				new DefaultShell(openAssociatedPerspectiveShellText);
				if (openAssociatedPerspective) {
					new PushButton("Yes").click();
				} else {
					new PushButton("No").click();
				}
				new WaitWhile(new ShellWithTextIsAvailable(
						openAssociatedPerspectiveShellText), TimePeriod.LONG);
			} catch (WaitTimeoutExpiredException wtee) {
				log.info("Shell 'Open Associated Perspective' wasn't shown");
			} catch (SWTLayerException sle) {
				log.info("Shell 'Open Associated Perspective' wasn't shown");
			}

		}

		new WaitWhile(new ShellWithTextIsAvailable("New Java Project"));
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);

	}

	/**
	 * Return true if Eclipse should ask to Open Associated Perspective when new
	 * project is created.
	 * 
	 * @return
	 */
	private boolean isWaitForOpenAssociatedPerspective() {

		return "prompt".equalsIgnoreCase(Platform.getPreferencesService()
				.getString("org.eclipse.ui.ide",
						"SWITCH_PERSPECTIVE_ON_PROJECT_CREATION", "prompt",
						null));
	}
}
