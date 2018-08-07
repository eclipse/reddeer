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
package org.eclipse.reddeer.eclipse.selectionwizard;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.eclipse.ui.dialogs.NewWizard;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.button.NoButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Represents wizard which can be found in new wizard dialog (menu 'File -&gt;
 * New -&gt; Other...').
 * 
 * @author rawagner
 * @contributor jkopriva@redhat.com
 *
 */
public abstract class NewMenuWizard extends AbstractSelectionWizardDialog {

	/**
	 * Constructs new Wizard that can be found in NewWizard.
	 * 
	 * @param shellText      new wizard text when next is clicked (ie 'New File')
	 * @param wizardCategory new wizard category (ie 'General')
	 * @param wizardName     new wizard name (ie 'File')
	 */
	public NewMenuWizard(String shellText, String wizardCategory, String wizardName) {
		super(shellText, wizardCategory, wizardName);
	}

	/**
	 * Constructs new Wizard that can be found in NewWizard.
	 * 
	 * @param shellText  new wizard text when next is clicked (ie 'New File')
	 * @param wizardPath wizard path in new wizard (ie 'General, File')
	 */
	public NewMenuWizard(String shellText, String[] wizardPath) {
		super(shellText, wizardPath);
	}

	@Override
	protected Openable getOpenAction() {
		return new SelectionWizardOpenable(new NewWizard(), wizardPath, matcher);
	}

	/**
	 * Click the finish button in wizard dialog. This method should be used with
	 * NewMenuWizard with associated perspective. If user wants to use this method
	 * with NewMenuWizard without associated perspective, he should use finish() or
	 * finish(TimePeriod).
	 * 
	 * @param openPerspective open perspective related to the new project
	 */
	public void finish(boolean openPerspective) {
		finish(TimePeriod.LONG, openPerspective);
	}

	/**
	 * Click the finish button in wizard dialog. This method should be used with
	 * NewMenuWizard with associated perspective. If user wants to use this method
	 * with NewMenuWizard without associated perspective, he should use finish() or
	 * finish(TimePeriod).
	 * 
	 * @param timePeriod      timeout for running job after clicking on Finish
	 *                        button
	 * @param openPerspective open perspective related to the new project
	 */
	public void finish(TimePeriod timePeriod, boolean openPerspective) {
		String openAssociatedPerspective = PreferencesUtil.getOpenAssociatedPerspective();
		checkShell();
		log.info("Finish wizard");

		new FinishButton(this).click();

		try {
			new WaitWhile(new JobIsRunning(), timePeriod);
		} catch (NoClassDefFoundError e) {
			// do nothing, reddeer.workbench plugin is not available
		}

		if (!openAssociatedPerspective.equals("prompt")) {
			throw new RedDeerException("Property " + RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE + " is set to '"
					+ openAssociatedPerspective + "' but it should be set to 'prompt'");
		}

		new WaitUntil(new ShellIsAvailable("Open Associated Perspective?"));
		if (openPerspective) {
			new PushButton("Open Perspective").click();
		} else {
			new NoButton().click();
		}
	}

}