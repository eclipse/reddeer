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
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.m2e.core.ui.preferences.MavenSettingsPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenSettingsPreferencePageTest{
	
	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	
	private MavenSettingsPreferencePage page = new MavenSettingsPreferencePage(preferencesDialog);
	
	@After
	public void tearDown(){
		preferencesDialog.cancel();
	}
	
	@Test
	public void setSettingsXMLTest(){
		preferencesDialog.open();
		preferencesDialog.select(page);
		page.setUserSettingsLocation("test");
		assertEquals("test", page.getUserSettingsLocation());
	}

}
