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

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.ui.ide.dialogs.IDEPerspectivesPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class PerspectivesPreferencePageTest {

	private WorkbenchPreferenceDialog preferencesDialog = 
		new WorkbenchPreferenceDialog();

	@Test
	public void checkAllPreferences() {

		preferencesDialog.open();
		IDEPerspectivesPreferencePage perspectivesPreferencePage = 
			new IDEPerspectivesPreferencePage(preferencesDialog);
		preferencesDialog.select(perspectivesPreferencePage);

		perspectivesPreferencePage.checkAlwaysOpenAssociatedPerspective();
		assertTrue(perspectivesPreferencePage
				.isAlwaysOpenAssociatedPerspective());
		perspectivesPreferencePage.checkNeverOpenAssociatedPerspective();
		assertTrue(perspectivesPreferencePage
				.isNeverOpenAssociatedPerspective());
		perspectivesPreferencePage.checkOpenNewPerspectiveInNewWindow();
		assertTrue(perspectivesPreferencePage
				.isOpenNewPerspectiveInNewWindow());
		perspectivesPreferencePage.checkOpenNewPerspectiveInSameWindow();
		assertTrue(perspectivesPreferencePage
				.isOpenNewPerspectiveInSameWindow());
		perspectivesPreferencePage.checkPromptOpenAssociatedPerspective();
		assertTrue(perspectivesPreferencePage
				.isPromptOpenAssociatedPerspective());
		preferencesDialog.cancel();
	}

	@After
	public void tearDown() {
		if(preferencesDialog.isOpen()){
			preferencesDialog.cancel();
		}
	}
}
