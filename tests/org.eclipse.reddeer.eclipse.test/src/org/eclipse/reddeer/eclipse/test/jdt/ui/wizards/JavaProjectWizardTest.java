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
package org.eclipse.reddeer.eclipse.test.jdt.ui.wizards;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for Java Project Wizard page objects
 * @author odockal
 *
 */
@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class JavaProjectWizardTest {

	public static final String JAVA_PROJECT = "SimpleJavaProject";
	public static final String JAVA_MODULE_NAME = "simplejavaproject";
	public static final String JAVA_MODULE_INFO = "module-info.java";
	public static String DEFAULT_JAVA;
	
	@BeforeClass
	public static void findOutJavaVersionUsed() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
	
		new NewJavaProjectWizardPageOne(dialog);
		new RadioButton(dialog, "Use an execution environment JRE:").toggle(true);
		DEFAULT_JAVA = new DefaultCombo(dialog, 0).getSelection();
		dialog.cancel();
	}
	
	
	@After
	public void cleanUp() {
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		
		explorer.deleteAllProjects(true);
	}
	
	@AfterClass
	public static void cleanUpClass() {
		// setting another jre in test will keep its value through other tests
		// causing build errors
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
	
		NewJavaProjectWizardPageOne pageOne = new NewJavaProjectWizardPageOne(dialog);
		pageOne.useExecutionEnvironmentJRE(DEFAULT_JAVA);
		dialog.cancel();		
	}
	
	@Test
	public void testJavaProjectWizard() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		
		NewJavaProjectWizardPageOne pageOne = new NewJavaProjectWizardPageOne(dialog);
		pageOne.setProjectName(JAVA_PROJECT);
		pageOne.useDefaultLocation(true);
		
		dialog.next();
		
		NewJavaProjectWizardPageTwo pageTwo = new NewJavaProjectWizardPageTwo(dialog);
		pageTwo.allowOutputFoldersForSourceFolders(true);
		
		dialog.finish();
		
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		
		assertTrue(explorer.containsProject(JAVA_PROJECT));
	}
	
	@Test
	public void testJavaProjectWizardCreateInfoModule() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		
		NewJavaProjectWizardPageOne pageOne = new NewJavaProjectWizardPageOne(dialog);
		pageOne.setProjectName(JAVA_PROJECT);
		pageOne.useExecutionEnvironmentJRE("JavaSE-9");
		
		dialog.next();
		
		NewJavaProjectWizardPageTwo pageTwo = new NewJavaProjectWizardPageTwo(dialog);
		pageTwo.createModuleInfoFile(true);
		
		dialog.finish(true, JAVA_MODULE_NAME);
		
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		
		assertTrue(explorer.containsProject(JAVA_PROJECT));
		assertTrue(explorer.getProject(JAVA_PROJECT).containsResource("src", JAVA_MODULE_INFO, JAVA_MODULE_NAME));
	}
	
	@Test
	public void testJavaProjectWizardCreateInfoModuleDefaultName() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		
		NewJavaProjectWizardPageOne pageOne = new NewJavaProjectWizardPageOne(dialog);
		pageOne.setProjectName(JAVA_PROJECT);
		pageOne.useExecutionEnvironmentJRE("JavaSE-10");
		
		dialog.next();
		
		NewJavaProjectWizardPageTwo pageTwo = new NewJavaProjectWizardPageTwo(dialog);
		pageTwo.createModuleInfoFile(true);
		
		dialog.finish();
		
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		
		assertTrue(explorer.containsProject(JAVA_PROJECT));
		assertTrue(!explorer.getProject(JAVA_PROJECT).containsResource("src", JAVA_MODULE_INFO, JAVA_PROJECT));
	}
	
	@Test
	public void testJavaProjectWizardDisabledModuleInfoCheckbox() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		
		NewJavaProjectWizardPageOne pageOne = new NewJavaProjectWizardPageOne(dialog);
		pageOne.setProjectName(JAVA_PROJECT);
		pageOne.useExecutionEnvironmentJRE("JavaSE-1.8");
		
		dialog.next();
		
		NewJavaProjectWizardPageTwo pageTwo = new NewJavaProjectWizardPageTwo(dialog);
		try {
			pageTwo.createModuleInfoFile(true);
		} catch (WaitTimeoutExpiredException exc) {
			// exception expected
		}
		
		dialog.finish();
	}
}
