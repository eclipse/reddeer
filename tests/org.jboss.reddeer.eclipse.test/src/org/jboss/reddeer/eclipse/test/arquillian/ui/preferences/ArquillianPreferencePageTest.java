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
package org.jboss.reddeer.eclipse.test.arquillian.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.arquillian.ui.preferences.ArquillianPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for class that represents Arquillian preference page
 * 
 * @author Vlado Pakan, Len DiMaggio
 *
 */
@RunWith(RedDeerSuite.class)
public class ArquillianPreferencePageTest {

	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	private ArquillianPreferencePage arquillianPreferencePage = new ArquillianPreferencePage();
	private final String VERSION_STRING = "1.1.10.Final";
	private final String ARG_STRING = "TEST ARGS";

	@Test
	public void checkAllPreferences() {
		preferencesDialog.open();
		preferencesDialog.select(arquillianPreferencePage);
		
		arquillianPreferencePage.setDefaultVMArg(true);
		arquillianPreferencePage.setArquillianVersion (VERSION_STRING);
		arquillianPreferencePage.setVMArgsText (ARG_STRING);
		arquillianPreferencePage.setDefaultVMArgsToJUnit(true);
		arquillianPreferencePage.setDefaultVMArgsToLaunch(true);
		arquillianPreferencePage.setAllowOSCommandWhenAnalyzing(true);
		arquillianPreferencePage.setAllowSystemPropWhenAnalyzing(true);
		
		assertTrue(arquillianPreferencePage.isDefaultVMArgChecked());
		assertTrue(arquillianPreferencePage.getArquillianVersion().equals(VERSION_STRING));
		assertTrue(arquillianPreferencePage.getVMArgsText().equals(ARG_STRING));
		assertTrue(arquillianPreferencePage.isAddDefaultVMArgsToJUnitChecked());
		assertTrue(arquillianPreferencePage.isAddDefaultVMArgsToLaunchChecked());
		assertTrue(arquillianPreferencePage.isAllowOSCommandWhenAnalyzingChecked());
		assertTrue(arquillianPreferencePage.isAllowSystemPropWhenAnalyzingChecked());
		
		preferencesDialog.cancel();
	}

	@Test
	public void uncheckAllPreferences() {
		preferencesDialog.open();
		preferencesDialog.select(arquillianPreferencePage);
		
		/* setDefaultVMArgsToJUnit and setDefaultVMArgsToLaunch are dependent on setDefaultVMArgChecked */
		arquillianPreferencePage.setDefaultVMArg(true);		
		arquillianPreferencePage.setDefaultVMArgsToJUnit(false);
		arquillianPreferencePage.setDefaultVMArgsToLaunch(false);
		assertFalse(arquillianPreferencePage.isAddDefaultVMArgsToJUnitChecked());
		assertFalse(arquillianPreferencePage.isAddDefaultVMArgsToLaunchChecked());
				
		arquillianPreferencePage.setDefaultVMArg(false);
		arquillianPreferencePage.setAllowOSCommandWhenAnalyzing(false);
		arquillianPreferencePage.setAllowSystemPropWhenAnalyzing(false);

		assertFalse(arquillianPreferencePage.isDefaultVMArgChecked());
		assertFalse(arquillianPreferencePage.isAllowOSCommandWhenAnalyzingChecked());
		assertFalse(arquillianPreferencePage.isAllowSystemPropWhenAnalyzingChecked());
		
		preferencesDialog.cancel();
	}	
		
	@After
	public void tearDown(){
		// try to close preference dialog in case it stayed open
		try{
			preferencesDialog.cancel();
		} catch (SWTLayerException swtle){
			// do nothing
		}
	}
}
