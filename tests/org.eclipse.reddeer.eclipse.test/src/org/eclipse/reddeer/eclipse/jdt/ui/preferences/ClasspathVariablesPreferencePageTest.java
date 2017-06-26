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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.reddeer.eclipse.test.Activator;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Tests Classpath Variables preference page
 * @author Vlado Pakan
 *
 */
@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ClasspathVariablesPreferencePageTest {
	@Test
	public void getVariables() {
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		preferencesDialog.open();
		ClasspathVariablesPreferencePage classpathVariablesPreferencePage = new ClasspathVariablesPreferencePage(preferencesDialog); 
		preferencesDialog.select(classpathVariablesPreferencePage);
		List<String> variables = classpathVariablesPreferencePage.getVariables();
		preferencesDialog.cancel();
		assertTrue("Classpath variables caonnot be empty",!variables.isEmpty());
 	}
	@Test
	public void addRemoveVariable() {
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		preferencesDialog.open();
		ClasspathVariablesPreferencePage classpathVariablesPreferencePage = new ClasspathVariablesPreferencePage(preferencesDialog); 
		preferencesDialog.select(classpathVariablesPreferencePage);
		String addedVariableLabel = classpathVariablesPreferencePage.addVariable("VN", getJarVariableLocation(), true);
		List<String> variables = classpathVariablesPreferencePage.getVariables();
		preferencesDialog.ok();
		assertTrue("Variables has to contain item:'" + addedVariableLabel + "'",variables.contains(addedVariableLabel));
		// reopen and check
		preferencesDialog.open();
		preferencesDialog.select(classpathVariablesPreferencePage);
		classpathVariablesPreferencePage.removeVariable(addedVariableLabel);
		variables = classpathVariablesPreferencePage.getVariables();
		preferencesDialog.ok();
		assertFalse("Variables should not contain item:'" + addedVariableLabel + "'",variables.contains(addedVariableLabel));
 	}
	
	private String getJarVariableLocation(){
		try {
			File jarFileRoot = new File(FileLocator.resolve(FileLocator.find(
					Platform.getBundle(Activator.PLUGIN_ID), new Path("target"), null)).getFile());
			File[] jarFiles = jarFileRoot.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar");
				}
			});
			if (jarFiles == null ||jarFiles.length == 0){
				jarFiles = new File(jarFileRoot,"plugins").listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".jar");
					}
				});
			}
			return jarFiles[0].getAbsolutePath();
		} catch (IOException e) {
			return null;
		}
	}
}
