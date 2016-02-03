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

import org.jboss.reddeer.eclipse.arquillian.ui.preferences.ArquillianValidatorPreferencePage;
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
public class ArquillianValidatorPreferencePageTest {

	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	private ArquillianValidatorPreferencePage arquillianValidatorPreferencePage = new ArquillianValidatorPreferencePage();
	private final String ERROR = "Error";
	private final String WARNING = "Warning";
	private final String IGNORE = "Ignore";
	private final String SEVERITY_FILTER_STRING = "test filter string";

	@Test
	public void checkAllPreferences() {
		preferencesDialog.open();
		preferencesDialog.select(arquillianValidatorPreferencePage);
		
		arquillianValidatorPreferencePage.setEnableValidation(true);
		arquillianValidatorPreferencePage.setTestArquillianContainer(true);
		
		/* Note: Default setting is WARNING, change it here for the test */
		arquillianValidatorPreferencePage.setDeploymentMethod(ERROR);
		arquillianValidatorPreferencePage.setMissingTestMethod(ERROR);
		arquillianValidatorPreferencePage.setTypeNotIncluded(ERROR);
		arquillianValidatorPreferencePage.setImportNotIncluded(ERROR);
		arquillianValidatorPreferencePage.setDeployArchiveNotIncluded(ERROR);
		arquillianValidatorPreferencePage.setInvalidArchiveName(ERROR);
		arquillianValidatorPreferencePage.setDeployMethodPublicStatic(ERROR);
		arquillianValidatorPreferencePage.setInvalidArchiveFileLocation(ERROR);

		assertTrue(arquillianValidatorPreferencePage.isTestEnableValidationChecked());
		assertTrue(arquillianValidatorPreferencePage.isTestArquillianContainerChecked());
		
		assertTrue(arquillianValidatorPreferencePage.getDeploymentMethod().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getMissingTestMethod().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getTypeNotIncluded().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getImportNotIncluded().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getDeployArchiveNotIncluded().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getInvalidArchiveName().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getDeployMethodPublicStatic().equals(ERROR));
		assertTrue(arquillianValidatorPreferencePage.getInvalidArchiveFileLocation().equals(ERROR));
				
		arquillianValidatorPreferencePage.setSelectedSeverity(SEVERITY_FILTER_STRING);
		assertTrue(arquillianValidatorPreferencePage.getSelectedSeverity().equals(SEVERITY_FILTER_STRING));
		
		preferencesDialog.cancel();
	}

	@Test
	public void uncheckAllPreferences() {
		preferencesDialog.open();
		preferencesDialog.select(arquillianValidatorPreferencePage);
		
		arquillianValidatorPreferencePage.setEnableValidation(false);
		arquillianValidatorPreferencePage.setTestArquillianContainer(false);

		assertFalse(arquillianValidatorPreferencePage.isTestEnableValidationChecked());
		assertFalse(arquillianValidatorPreferencePage.isTestArquillianContainerChecked());
		
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
